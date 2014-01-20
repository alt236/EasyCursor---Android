package uk.co.alt236.easycursor.sqlcursor.querybuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import uk.co.alt236.easycursor.sqlcursor.EasySqlQueryModel;
import uk.co.alt236.easycursor.sqlcursor.querybuilders.interfaces.SqlSelectBuilder;

public class EasySelectQueryBuilder implements SqlSelectBuilder {
	private static final String LEFT_OUTER_JOIN = "LEFT OUTER JOIN";
	private static final String COLLATE = "COLLATE";

	private static final char SPACE = ' ';
	private static final char COMMA = ',';
	private static final String KEYWORD_AS = " AS ";
	private static final String KEYWORD_ON = " ON ";
	private static final String WHERE_AND = " AND ";
	private static final String WHERE_OR = " OR ";

	private final StringBuilder mWhere;
	private final StringBuilder mGroupBy;
	private final StringBuilder mTables;
	private final StringBuilder mJoins;
	private final StringBuilder mOrderBy;
	private final Collection<String> mWhereArgs;
	private final Collection<String> mSelect;


	private String mLimit;
	private boolean mStrict;
	private boolean mDistinct;

	public EasySelectQueryBuilder(){
		mWhere = new StringBuilder();
		mTables = new StringBuilder();
		mGroupBy = new StringBuilder
				();
		mOrderBy = new StringBuilder();
		mJoins = new StringBuilder();
		mWhereArgs = new ArrayList<String>();
		mSelect = new ArrayList<String>();
	}

	public EasySelectQueryBuilder addColumn(final String column){
		if(column == null){return this;}
		mSelect.add(column);
		return this;
	}

	public EasySelectQueryBuilder addColumn(final String colName, final String alias){
		if(colName == null){return this;}
		if(alias == null){return this;}
		mSelect.add(colName + KEYWORD_AS + alias);
		return this;
	}

	public EasySelectQueryBuilder addColumns(final Collection<String> columns){
		if(columns == null){return this;}
		mSelect.addAll(columns);
		return this;
	}

	public EasySelectQueryBuilder addColumns(final String[] columns){
		if(columns == null){return this;}
		mSelect.addAll(Arrays.asList(columns));
		return this;
	}

	public EasySelectQueryBuilder addGroupBy(final String column){
		if(column == null){return this;}
		addSpaceIfNeeded(mGroupBy, true);
		mGroupBy.append(column);
		return this;
	}

	public EasySelectQueryBuilder addLeftJoin(final String table, final String on){
		addLeftJoin(table, null, on);
		return this;
	}

	public EasySelectQueryBuilder addLeftJoin(final String table, final String alias, final String on){
		if(table == null){return this;}
		if(on == null){return this;}

		addSpaceIfNeeded(mJoins, false);

		mJoins.append(LEFT_OUTER_JOIN);
		mJoins.append(SPACE);
		mJoins.append(table.trim());

		if(alias != null){
			mJoins.append(KEYWORD_AS);
			mJoins.append(alias);
		}

		mJoins.append(SPACE);
		mJoins.append(KEYWORD_ON);
		mJoins.append(SPACE);
		putBrackets(mJoins, on.trim());
		return this;
	}

	public EasySelectQueryBuilder addOrderBy(final String column){
		addOrderBy(column, null, null);
		return this;
	}

	public EasySelectQueryBuilder addOrderBy(final String column, final String collation, final Order order){
		if(column == null){return this;}

		addSpaceIfNeeded(mOrderBy, true);

		mOrderBy.append(column);

		if(collation != null){
			mOrderBy.append(SPACE);
			mOrderBy.append(COLLATE);
			mOrderBy.append(SPACE);
			mOrderBy.append(collation);
		}

		if(order != null){
			mOrderBy.append(SPACE);
			mOrderBy.append(order.toString());
		}

		return this;
	}

	public EasySelectQueryBuilder addTable(final String table){
		addTable(table, null);
		return this;
	}

	public EasySelectQueryBuilder addTable(final String table, final String alias){
		if(table == null){return this;}
		addSpaceIfNeeded(mTables, true);
		mTables.append(table.trim());

		if(alias != null){
			mTables.append(SPACE);
			mTables.append(alias);
		}

		return this;
	}

	public EasySelectQueryBuilder addWhere(final String where, final String... args){
		return addWhere(null, where, args);
	}

	public EasySelectQueryBuilder addWhere(final WhereLink link, final String where, final String... args){
		if(where == null){return this;}

		if(mWhere.length() > 0 && link != null){
			mWhere.append(link.toString());
		}

		putBrackets(mWhere, where.trim());

		if(args != null){
			for(final String arg : args){
				mWhereArgs.add(arg);
			}
		}
		return this;
	}

	public EasySqlQueryModel build(){
		return new EasySqlQueryModel(this);
	}

	public void clear(){
		mWhere.setLength(0);
		mTables.setLength(0);
		mOrderBy.setLength(0);
		mGroupBy.setLength(0);
		mJoins.setLength(0);
		mWhereArgs.clear();
		mSelect.clear();
	}

	/* (non-Javadoc)
	 * @see uk.co.alt236.easycursor.SelectQueryBuilder#getGroupBy()
	 */
	@Override
	public String getGroupBy(){
		if(mGroupBy.length() == 0){return null;}
		return mGroupBy.toString().trim();
	}

	@Override
	public String getHaving() {
		return null;
	}

	/* (non-Javadoc)
	 * @see uk.co.alt236.easycursor.SelectQueryBuilder#getLimit()
	 */
	@Override
	public String getLimit(){
		return mLimit;
	}

	/* (non-Javadoc)
	 * @see uk.co.alt236.easycursor.SelectQueryBuilder#getOrderBy()
	 */
	@Override
	public String getOrderBy(){
		if(mOrderBy.length() == 0){return null;}
		return mOrderBy.toString().trim();
	}

	/* (non-Javadoc)
	 * @see uk.co.alt236.easycursor.SelectQueryBuilder#getSelect()
	 */
	@Override
	public String[] getSelect(){
		if(mSelect.size() == 0){return null;}
		return mSelect.toArray(new String[0]);
	}

	/* (non-Javadoc)
	 * @see uk.co.alt236.easycursor.SelectQueryBuilder#getTables()
	 */
	@Override
	public String getTables(){
		if(mTables.length() == 0){return null;}
		final StringBuilder sb = new StringBuilder();

		if(mTables.length() > 0 && mJoins.length() > 0){
			sb.append(mTables);
			sb.append(SPACE);
			sb.append(mJoins);
		} else {
			sb.append(mTables);
		}

		return sb.toString();
	}

	/* (non-Javadoc)
	 * @see uk.co.alt236.easycursor.SelectQueryBuilder#getWhere()
	 */
	@Override
	public String getWhere(){
		if(mWhere.length() == 0){return null;}
		return mWhere.toString().trim();
	}

	/* (non-Javadoc)
	 * @see uk.co.alt236.easycursor.SelectQueryBuilder#getWhereArgs()
	 */
	@Override
	public String[] getWhereArgs(){
		if(mWhereArgs.size() == 0){return null;}
		return mWhereArgs.toArray(new String[0]);
	}

	/* (non-Javadoc)
	 * @see uk.co.alt236.easycursor.SelectQueryBuilder#isDistinct()
	 */
	@Override
	public boolean isDistinct(){
		return mDistinct;
	}

	/* (non-Javadoc)
	 * @see uk.co.alt236.easycursor.SelectQueryBuilder#isStrict()
	 */
	@Override
	public boolean isStrict(){
		return mStrict;
	}

	public EasySelectQueryBuilder setDistinct(final boolean value){
		mDistinct = value;
		return this;
	}

	public EasySelectQueryBuilder setLimit(int limit){
		mLimit = String.valueOf(limit);
		return this;
	}

	public EasySelectQueryBuilder setLimit(String limit){
		mLimit = limit;
		return this;
	}

	public EasySelectQueryBuilder setStrict(final boolean value){
		mStrict = value;
		return this;
	}

	@Override
	public String toString() {
		return "EasySelectQueryBuilder ["
				+   "mSelect=" + mSelect
				+ ", mTables="+ mTables
				+ ", mWhere=" + mWhere
				+ ", mWhereArgs=" + mWhereArgs
				+ ", mOrderBy=" + mOrderBy
				+ "]";
	}

	private static void addSpaceIfNeeded(final StringBuilder sb, final boolean addCommaToo){
		if(sb.length() > 0){
			if(addCommaToo){
				sb.append(COMMA);
			}

			sb.append(SPACE);
		}
	}

	private static void putBrackets(final StringBuilder sb, final String text){
		sb.append('(');
		sb.append(text);
		sb.append(')');
	}

	public enum WhereLink{
		AND(WHERE_AND),
		OR(WHERE_OR);

		final private String link;

		private WhereLink(String link){
			this.link = link;
		}

	    @Override
	    public String toString(){
	        return link;
	    }
	}
}
