package uk.co.alt236.easycursor.sampleapp.util;

import uk.co.alt236.easycursor.sampleapp.database.QueryConstants;
import uk.co.alt236.easycursor.sampleapp.database.builders.LousyQueryBuilder;
import uk.co.alt236.easycursor.sqlcursor.EasySqlQueryModel;
import uk.co.alt236.easycursor.sqlcursor.querybuilders.EasyCompatSqlModelBuilder;

public class StaticModelBuilder {

	public static EasySqlQueryModel getCompatQueryModel(){
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

	public static EasySqlQueryModel getCustomBuilderModel(){
		final LousyQueryBuilder builder = new LousyQueryBuilder();
		final EasySqlQueryModel model = builder
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

		return model;
	}

	public static EasySqlQueryModel getDefaultSelectModel(){
		final EasySqlQueryModel model = new EasySqlQueryModel.SelectQueryBuilder()
		.setDistict(true)
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

		return model;
	}

	public static EasySqlQueryModel getRawQueryModel(){
		final EasySqlQueryModel model = new EasySqlQueryModel.RawQueryBuilder()
		.setRawSql(QueryConstants.RAW_QUERY)
		.setSelectionArgs(QueryConstants.RAW_SQL_PARAMS)
		.build();

		return model;
	}
}
