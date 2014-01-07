package uk.co.alt236.easycursor.querybuilders.interfaces;

public interface SelectBuilder {

	public abstract String getGroupBy();

	public abstract String getHaving();

	public abstract String getLimit();

	public abstract String getOrderBy();

	public abstract String[] getSelect();

	public abstract String getTables();

	public abstract String getWhere();

	public abstract String[] getWhereArgs();

	public abstract boolean isDistinct();

	public abstract boolean isStrict();

}