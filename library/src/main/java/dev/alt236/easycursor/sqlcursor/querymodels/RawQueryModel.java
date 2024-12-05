/*
 * ***************************************************************************
 * Copyright 2015 Alexandros Schillings
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

package dev.alt236.easycursor.sqlcursor.querymodels;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import dev.alt236.easycursor.sqlcursor.querybuilders.interfaces.QueryModelInfo;
import dev.alt236.easycursor.sqlcursor.querybuilders.interfaces.SqlRawQueryBuilder;
import dev.alt236.easycursor.util.JsonPayloadHelper;

/**
 *
 */
public class RawQueryModel extends SqlQueryModel {
    private static final int QUERY_TYPE = RawQueryModel.QUERY_TYPE_RAW;
    private static final String FIELD_RAW_SQL = "rawSql";
    private static final String FIELD_SELECTION_ARGS = "selectionArgs";

    //
    // Raw Query
    //
    private final String mRawSql;
    private final String[] mSelectionArgs;

    public RawQueryModel(final SqlRawQueryBuilder builder) {
        super(builder instanceof QueryModelInfo ? (QueryModelInfo) builder : null,
                QUERY_TYPE);
        mRawSql = builder.getRawSql();
        mSelectionArgs = builder.getSelectionArgs();
    }

    public RawQueryModel(final JsonWrapper wrapper) {
        super(wrapper, QUERY_TYPE);
        mRawSql = wrapper.getString(FIELD_RAW_SQL);
        mSelectionArgs = wrapper.getStringArray(FIELD_SELECTION_ARGS);
    }

    @Override
    protected Cursor executeQueryInternal(final SQLiteDatabase db) {
        return db.rawQuery(mRawSql, mSelectionArgs);
    }

    public String getRawSql() {
        return mRawSql;
    }

    public String[] getSelectionArgs() {
        return mSelectionArgs;
    }

    @Override
    public String toJson() throws JSONException {
        final JSONObject payload = new JSONObject();
        addCommonFields(payload);
        JsonPayloadHelper.add(payload, FIELD_RAW_SQL, getRawSql());
        JsonPayloadHelper.add(payload, FIELD_SELECTION_ARGS, getSelectionArgs());

        return payload.toString();
    }

    @Override
    public String toString() {
        return "RawQueryModel{" +
                "mRawSql='" + mRawSql + '\'' +
                ", mSelectionArgs=" + Arrays.toString(mSelectionArgs) +
                '}';
    }
}
