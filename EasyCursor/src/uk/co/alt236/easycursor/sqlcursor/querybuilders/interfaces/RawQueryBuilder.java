package uk.co.alt236.easycursor.sqlcursor.querybuilders.interfaces;

public interface RawQueryBuilder {

	public abstract String getRawSql();

	public abstract String[] getWhereArgs();

}
