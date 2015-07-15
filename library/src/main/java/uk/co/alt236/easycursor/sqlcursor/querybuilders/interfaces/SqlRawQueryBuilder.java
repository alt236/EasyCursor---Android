package uk.co.alt236.easycursor.sqlcursor.querybuilders.interfaces;

import uk.co.alt236.easycursor.EasyQueryModel;

public interface SqlRawQueryBuilder {

    public abstract EasyQueryModel build();

    public abstract String getRawSql();

    public abstract String[] getWhereArgs();
}
