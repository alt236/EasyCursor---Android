package uk.co.alt236.easycursor.sampleapp.database;

import org.json.JSONException;

import uk.co.alt236.easycursor.EasyCursor;
import uk.co.alt236.easycursor.sampleapp.database.builders.LousyQueryBuilder;
import uk.co.alt236.easycursor.sampleapp.util.Constants;
import uk.co.alt236.easycursor.sqlcursor.EasySqlCursor;
import uk.co.alt236.easycursor.sqlcursor.EasySqlQueryModel;
import uk.co.alt236.easycursor.sqlcursor.querybuilders.EasyCompatSqlModelBuilder;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.preference.PreferenceManager;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class ExampleDatabase extends SQLiteAssetHelper {
	private static final String DATABASE_NAME = "Chinook_Sqlite.db";
	private static final int DATABASE_VERSION = 1;

	public ExampleDatabase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public EasyCursor doAndroidDefaultQuery(){
		// This will convert a default Android cursor into an EasyCursor
		// but it will not contain the query model

		final SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(QueryConstants.DEFAULT_TABLES);
		qb.setDistinct(true);
		final Cursor cursor = qb.query(
				getReadableDatabase(),
				QueryConstants.DEFAULT_SELECT,
				QueryConstants.DEFAULT_WHERE,
				QueryConstants.RAW_SQL_PARAMS,
				null,
				null,
				QueryConstants.DEFAULT_ORDER_BY);
		cursor.moveToFirst();
		return new EasySqlCursor(cursor);
	}

	public EasyCursor doEasyCursorCompatQuery(){
		final EasyCompatSqlModelBuilder builder = new EasyCompatSqlModelBuilder();
		builder.setTables(QueryConstants.DEFAULT_TABLES);
		builder.setDistinct(true);
		builder.setQueryParams(
				QueryConstants.DEFAULT_SELECT,
				QueryConstants.DEFAULT_WHERE,
				QueryConstants.RAW_SQL_PARAMS,
				QueryConstants.DEFAULT_ORDER_BY);

		final EasySqlQueryModel model = builder.build();
		model.setModelComment("Default compat query");
		return model.execute(getReadableDatabase());
	}

	public EasyCursor doEasyCustomBuilderQuery(){
		final LousyQueryBuilder builder = new LousyQueryBuilder();
		final EasySqlQueryModel model = builder.setSelect(QueryConstants.DEFAULT_SELECT)
				.setWhere(QueryConstants.DEFAULT_WHERE)
				.setWhereArgs(QueryConstants.RAW_SQL_PARAMS)
				.setOrderBy(QueryConstants.DEFAULT_ORDER_BY)
				.build();
		model.setModelComment("Custom Builder query");
		return model.execute(getReadableDatabase());
	}

	public EasyCursor doEasyRawQuery() {
		final EasySqlQueryModel model = new EasySqlQueryModel.RawQueryBuilder()
		.setRawSql(QueryConstants.RAW_QUERY)
		.setSelectionArgs(QueryConstants.RAW_SQL_PARAMS)
		.setModelComment("Raw query")
		.build();

		return model.execute(getReadableDatabase());
	}

	public EasyCursor doEasySelectQuery(){
		final EasySqlQueryModel model = new EasySqlQueryModel.SelectQueryBuilder()
		.setTables(QueryConstants.DEFAULT_TABLES)
		.setDistict(true)
		.setProjectionIn(QueryConstants.DEFAULT_SELECT)
		.setSelection(QueryConstants.DEFAULT_WHERE)
		.setSelectionArgs(QueryConstants.RAW_SQL_PARAMS)
		.setSortOrder(QueryConstants.DEFAULT_ORDER_BY)
		.setModelComment("Default easy query")
		.build();

		return model.execute(getReadableDatabase());
	}

	public EasyCursor doSavedQuery(Context context) {
		final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
		final String json = settings.getString(Constants.PREFS_SAVED_QUERY, null);
		EasyCursor result;

		if (json == null) {
			result = null;
		} else {
			try {
				final EasySqlQueryModel model = new EasySqlQueryModel(json);
				result = model.execute(getReadableDatabase());
			} catch (JSONException e) {
				e.printStackTrace();
				result = null;
			}
		}

		return result;
	}
}