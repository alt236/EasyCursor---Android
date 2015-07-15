package uk.co.alt236.easycursor.sampleapp.database.builders;

import uk.co.alt236.easycursor.sqlcursor.EasySqlQueryModel;
import uk.co.alt236.easycursor.sqlcursor.querybuilders.interfaces.SqlSelectBuilder;

public class LousyQueryBuilder implements SqlSelectBuilder{
	private String mGroupBy;
	private String mHaving;
	private String mLimit;
	private String mOrderBy;
	private String mTables;
	private String mWhere;
	private String[] mSelect;
	private String[] mWhereArgs;
	private boolean mDistinct;
	private boolean mStrict;

	@Override
	public EasySqlQueryModel build(){
		return new EasySqlQueryModel(this);
	}

	@Override
	public String getGroupBy() {
		return mGroupBy;
	}

	@Override
	public String getHaving() {
		return mHaving;
	}

	@Override
	public String getLimit() {
		return mLimit;
	}

	@Override
	public String getOrderBy() {
		return mOrderBy;
	}

	@Override
	public String[] getSelect() {
		return mSelect;
	}

	@Override
	public String getTables() {
		return mTables;
	}

	@Override
	public String getWhere() {
		return mWhere;
	}

	@Override
	public String[] getWhereArgs() {
		return mWhereArgs;
	}

	@Override
	public boolean isDistinct() {
		return mDistinct;
	}

	@Override
	public boolean isStrict() {
		return mStrict;
	}

	public LousyQueryBuilder setDistinct(boolean value) {
		mDistinct = value;
		return this;
	}

	public LousyQueryBuilder setGroupBy(String groupBy) {
		mGroupBy = groupBy;
		return this;
	}

	public LousyQueryBuilder setHaving(String having) {
		mHaving = having;
		return this;
	}

	public LousyQueryBuilder setLimit(String limit) {
		mLimit = limit;
		return this;
	}

	public LousyQueryBuilder setOrderBy(String orderBy) {
		mOrderBy = orderBy;
		return this;
	}

	public LousyQueryBuilder setSelect(String[] select) {
		mSelect = select;
		return this;
	}

	public LousyQueryBuilder setStrict(boolean strict){
		mStrict = strict;
		return this;
	}

	public LousyQueryBuilder setFrom(String tables) {
		mTables = tables;
		return this;
	}

	public LousyQueryBuilder setWhere(String where) {
		mWhere = where;
		return this;
	}

	public LousyQueryBuilder setWhereArgs(String[] whereArgs) {
		mWhereArgs = whereArgs;
		return this;
	}
}
