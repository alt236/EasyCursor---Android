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

package uk.co.alt236.easycursor.sqlcursor.querymodels;

import org.json.JSONObject;

import uk.co.alt236.easycursor.sqlcursor.querybuilders.interfaces.QueryModelInfo;
import uk.co.alt236.easycursor.util.JsonPayloadHelper;

/**
 *
 */
public class JsonWrapper implements QueryModelInfo {

    private final JSONObject mJsonObject;

    public JsonWrapper(final JSONObject jsonObject) {
        mJsonObject = jsonObject;
    }

    public boolean getBoolean(final String field) {
        return JsonPayloadHelper.getBoolean(mJsonObject, field);
    }

    public int getInt(final String field) {
        return JsonPayloadHelper.getInt(mJsonObject, field);
    }

    @Override
    public String getModelComment() {
        return mJsonObject.optString(SqlQueryModel.FIELD_MODEL_COMMENT);
    }

    @Override
    public String getModelTag() {
        return mJsonObject.optString(SqlQueryModel.FIELD_MODEL_TAG);
    }

    @Override
    public int getModelVersion() {
        return mJsonObject.optInt(SqlQueryModel.FIELD_MODEL_VERSION, 0);
    }

    public String getString(final String field) {
        return JsonPayloadHelper.getString(mJsonObject, field);
    }

    public String[] getStringArray(final String field) {
        return JsonPayloadHelper.getStringArray(mJsonObject, field);
    }
}
