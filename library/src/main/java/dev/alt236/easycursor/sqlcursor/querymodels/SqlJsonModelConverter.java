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

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 */
public final class SqlJsonModelConverter {

    private SqlJsonModelConverter() {
        // no instances
    }

    public static SqlQueryModel convert(final String json) throws JSONException {
        final JSONObject payload = new JSONObject(json);

        final int modelType = payload.optInt(SqlQueryModel.FIELD_QUERY_TYPE, SqlQueryModel.QUERY_TYPE_UNINITIALISED);
        switch (modelType) {
            case SqlQueryModel.QUERY_TYPE_MANAGED:
                return new SelectQueryModel(new JsonWrapper(payload));
            case SqlQueryModel.QUERY_TYPE_RAW:
                return new RawQueryModel(new JsonWrapper(payload));
            default:
                throw new IllegalStateException("JSON cannot be converted to a valid " + SqlQueryModel.class.getSimpleName());
        }
    }
}
