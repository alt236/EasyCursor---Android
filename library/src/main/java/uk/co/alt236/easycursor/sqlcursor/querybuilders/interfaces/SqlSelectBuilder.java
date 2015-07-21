package uk.co.alt236.easycursor.sqlcursor.querybuilders.interfaces;

import uk.co.alt236.easycursor.EasyQueryModel;

public interface SqlSelectBuilder {

    EasyQueryModel build();

    String getGroupBy();

    String getHaving();

    String getLimit();

    String[] getProjectionIn();

    String getSelection();

    String[] getSelectionArgs();

    String getSortOrder();

    String getTables();

    boolean isDistinct();

    boolean isStrict();
}