/*
 * ***************************************************************************
 * Copyright 2024 Alexandros Schillings
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ***************************************************************************
 *
 */

package dev.alt236.easycursor.sampleapp.database;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.preference.PreferenceManager;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import dev.alt236.easycursor.EasyCursor;
import dev.alt236.easycursor.objectcursor.EasyObjectCursor;
import dev.alt236.easycursor.sampleapp.container.TrackInfo;
import dev.alt236.easycursor.sampleapp.database.builders.LousyQueryBuilder;
import dev.alt236.easycursor.sampleapp.util.Constants;
import dev.alt236.easycursor.sqlcursor.EasySqlCursor;
import dev.alt236.easycursor.sqlcursor.querybuilders.CompatSqlModelBuilder;
import dev.alt236.easycursor.sqlcursor.querymodels.SqlJsonModelConverter;
import dev.alt236.easycursor.sqlcursor.querymodels.SqlQueryModel;

public class ExampleDatabase extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "Chinook_Sqlite.db";
    private static final int DATABASE_VERSION = 1;

    public ExampleDatabase(final Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public EasyCursor doAndroidDefaultQuery() {
        // This will convert a default Android cursor into an EasyCursor
        // but it will not contain the query model

        final SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(QueryConstants.DEFAULT_TABLES);
        qb.setDistinct(true);
        final Cursor cursor = qb.query(
                getReadableDatabase(),
                QueryConstants.DEFAULT_SELECT,
                QueryConstants.DEFAULT_WHERE,
                QueryConstants.DEFAULT_SELECT_WHERE_PARAMS,
                null,
                null,
                QueryConstants.DEFAULT_ORDER_BY);
        cursor.moveToFirst();
        return new EasySqlCursor(cursor);
    }

    public EasyCursor doEasyCursorCompatQuery() {
        final CompatSqlModelBuilder builder = new CompatSqlModelBuilder();
        builder.setTables(QueryConstants.DEFAULT_TABLES);
        builder.setDistinct(true);
        builder.setQueryParams(
                QueryConstants.DEFAULT_SELECT,
                QueryConstants.DEFAULT_WHERE,
                QueryConstants.DEFAULT_SELECT_WHERE_PARAMS,
                null,
                null,
                QueryConstants.DEFAULT_ORDER_BY);

        final SqlQueryModel model = builder.build();
        model.setModelComment("Default compat query");
        return model.execute(getReadableDatabase());
    }

    public EasyCursor doEasyCustomBuilderQuery() {
        final LousyQueryBuilder builder = new LousyQueryBuilder();
        final SqlQueryModel model = builder.setSelect(QueryConstants.DEFAULT_SELECT)
                .setFrom(QueryConstants.DEFAULT_TABLES)
                .setWhere(QueryConstants.DEFAULT_WHERE)
                .setWhereArgs(QueryConstants.DEFAULT_SELECT_WHERE_PARAMS)
                .setOrderBy(QueryConstants.DEFAULT_ORDER_BY)
                .setGroupBy(QueryConstants.DEFAULT_SELECT_GROUP_BY)
                .setHaving(QueryConstants.DEFAULT_SELECT_HAVING)
                .build();
        model.setModelComment("Custom Builder query");
        return model.execute(getReadableDatabase());
    }

    public EasyCursor doEasyRawQuery() {
        final SqlQueryModel model = new SqlQueryModel.RawQueryBuilder()
                .setRawSql(QueryConstants.RAW_QUERY)
                .setSelectionArgs(QueryConstants.RAW_SQL_PARAMS)
                .setModelComment("Raw query")
                .build();

        return model.execute(getReadableDatabase());
    }

    public EasyCursor doEasySelectQuery() {
        final SqlQueryModel model = new SqlQueryModel.SelectQueryBuilder()
                .setDistinct(true)
                .setProjectionIn(QueryConstants.DEFAULT_SELECT)
                .setTables(QueryConstants.DEFAULT_TABLES)
                .setSelection(QueryConstants.DEFAULT_WHERE)
                .setSelectionArgs(QueryConstants.DEFAULT_SELECT_WHERE_PARAMS)
                .setSortOrder(QueryConstants.DEFAULT_ORDER_BY)
                .setModelComment("Default easy query")
                .build();

        return model.execute(getReadableDatabase());
    }

    public EasyCursor doObjectCursorQuery() {
        final EasyCursor dataIn = doEasyRawQuery();
        final List<TrackInfo> list = new ArrayList<>();

        while (!dataIn.isAfterLast()) {
            list.add(new TrackInfo(dataIn));
            dataIn.moveToNext();
        }

        dataIn.close();

        // the TrackInfo object already contains an _id getter, so we pass null as the alias
        final EasyCursor methodResult = new EasyObjectCursor<>(TrackInfo.class, list, null);
        methodResult.moveToFirst();
        return methodResult;
    }

    public EasyCursor doSavedQuery(final Context context) {
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        final String json = settings.getString(Constants.PREFS_SAVED_QUERY, null);
        EasyCursor result;

        if (json == null) {
            result = null;
        } else {
            try {
                final SqlQueryModel model = SqlJsonModelConverter.convert(json);
                result = model.execute(getReadableDatabase());
            } catch (final JSONException e) {
                e.printStackTrace();
                result = null;
            } catch (final IllegalStateException e) {
                e.printStackTrace();
                result = null;
            }
        }

        return result;
    }
}