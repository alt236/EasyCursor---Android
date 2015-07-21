package uk.co.alt236.easycursor.sqlcursor.querybuilders.interfaces;

import uk.co.alt236.easycursor.EasyQueryModel;

public interface SqlRawQueryBuilder {

    EasyQueryModel build();

    String getRawSql();

    String[] getSelectionArgs();
}
