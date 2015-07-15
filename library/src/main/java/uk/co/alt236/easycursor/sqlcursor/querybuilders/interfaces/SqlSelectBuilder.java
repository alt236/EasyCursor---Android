package uk.co.alt236.easycursor.sqlcursor.querybuilders.interfaces;

import uk.co.alt236.easycursor.EasyQueryModel;

public interface SqlSelectBuilder {

    EasyQueryModel build();

    String getGroupBy();

    String getHaving();

    String getLimit();

    String getOrderBy();

    String[] getSelect();

    String getTables();

    String getWhere();

    String[] getWhereArgs();

    boolean isDistinct();

    boolean isStrict();
}