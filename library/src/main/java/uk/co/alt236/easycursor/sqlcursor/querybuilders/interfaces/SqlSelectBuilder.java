package uk.co.alt236.easycursor.sqlcursor.querybuilders.interfaces;

import uk.co.alt236.easycursor.EasyQueryModel;

public interface SqlSelectBuilder {

    public abstract EasyQueryModel build();

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