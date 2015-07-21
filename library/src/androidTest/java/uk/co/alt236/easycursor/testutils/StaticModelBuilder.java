package uk.co.alt236.easycursor.testutils;

import uk.co.alt236.easycursor.sqlcursor.querybuilders.EasyCompatSqlModelBuilder;
import uk.co.alt236.easycursor.sqlcursor.querymodels.SqlQueryModel;

public final class StaticModelBuilder {

    private StaticModelBuilder() {
    }

    public static SqlQueryModel getCompatQueryModel() {
        final EasyCompatSqlModelBuilder builder = new EasyCompatSqlModelBuilder();
        builder.setTables(QueryConstants.DEFAULT_TABLES);
        builder.setDistinct(true);
        builder.setStrict(true);
        builder.setQueryParams(
                QueryConstants.DEFAULT_SELECT,
                QueryConstants.DEFAULT_WHERE,
                QueryConstants.DEFAULT_SELECT_WHERE_PARAMS,
                QueryConstants.DEFAULT_SELECT_GROUP_BY,
                QueryConstants.DEFAULT_SELECT_HAVING,
                QueryConstants.DEFAULT_ORDER_BY,
                QueryConstants.DEFAULT_SELECT_LIMIT);

        return builder.build();
    }

    public static SqlQueryModel getCustomBuilderModel() {
        final LousyQueryBuilder builder = new LousyQueryBuilder();

        return builder
                .setDistinct(true)
                .setStrict(true)
                .setSelect(QueryConstants.DEFAULT_SELECT)
                .setFrom(QueryConstants.DEFAULT_TABLES)
                .setWhere(QueryConstants.DEFAULT_WHERE)
                .setWhereArgs(QueryConstants.DEFAULT_SELECT_WHERE_PARAMS)
                .setGroupBy(QueryConstants.DEFAULT_SELECT_GROUP_BY)
                .setHaving(QueryConstants.DEFAULT_SELECT_HAVING)
                .setOrderBy(QueryConstants.DEFAULT_ORDER_BY)
                .setLimit(QueryConstants.DEFAULT_SELECT_LIMIT)
                .build();
    }

    public static SqlQueryModel getDefaultSelectModel() {

        return new SqlQueryModel.SelectQueryBuilder()
                .setDistinct(true)
                .setStrict(true)
                .setSelect(QueryConstants.DEFAULT_SELECT)
                .setTables(QueryConstants.DEFAULT_TABLES)
                .setWhere(QueryConstants.DEFAULT_WHERE)
                .setWhereArgs(QueryConstants.DEFAULT_SELECT_WHERE_PARAMS)
                .setGroupBy(QueryConstants.DEFAULT_SELECT_GROUP_BY)
                .setHaving(QueryConstants.DEFAULT_SELECT_HAVING)
                .setSortOrder(QueryConstants.DEFAULT_ORDER_BY)
                .setLimit(QueryConstants.DEFAULT_SELECT_LIMIT)
                .build();
    }

    public static SqlQueryModel getRawQueryModel() {

        return new SqlQueryModel.RawQueryBuilder()
                .setRawSql(QueryConstants.RAW_QUERY)
                .setSelectionArgs(QueryConstants.RAW_SQL_PARAMS)
                .build();
    }
}
