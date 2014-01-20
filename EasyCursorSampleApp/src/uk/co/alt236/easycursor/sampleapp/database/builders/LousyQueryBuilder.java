package uk.co.alt236.easycursor.sampleapp.database.builders;

import uk.co.alt236.easycursor.sqlcursor.EasySqlQueryModel;
import uk.co.alt236.easycursor.sqlcursor.querybuilders.interfaces.SqlSelectBuilder;

public class LousyQueryBuilder implements SqlSelectBuilder{
	private String[] mSelect;
	private String mOrderBy;
	private String mWhere;
	private String[] mWhereArgs;
	private String mTables;

	public LousyQueryBuilder setSelect(String[] select) {
		mSelect = select;
		return this;
	}

	public LousyQueryBuilder setOrderBy(String orderBy) {
		mOrderBy = orderBy;
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

	public LousyQueryBuilder setTables(String tables) {
		mTables = tables;
		return this;
	}

	@Override
	public String getGroupBy() {
		return null;
	}

	@Override
	public String getHaving() {
		return null;
	}

	@Override
	public String getLimit() {
		return null;
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
		return false;
	}

	@Override
	public boolean isStrict() {
		return false;
	}

	@Override
	public EasySqlQueryModel build(){
		return new EasySqlQueryModel(this);
	}
}
