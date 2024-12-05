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

package dev.alt236.easycursor.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class JsonPayloadHelper {
    private static final String NULL = "null";

    private JsonPayloadHelper() {
    }

    public static void add(final JSONObject object, final String key, final Boolean value) throws JSONException {
        object.put(key, value);
    }

    public static void add(final JSONObject object, final String key, final Double value) throws JSONException {
        object.put(key, value);
    }

    public static void add(final JSONObject object, final String key, final Integer value) throws JSONException {
        object.put(key, value);
    }

    public static void add(final JSONObject object, final String key, final Long value) throws JSONException {
        object.put(key, value);
    }

    public static void add(final JSONObject object, final String key, final Object value) throws JSONException {
        object.put(key, value);
    }

    public static void add(final JSONObject object, final String key, final String value) throws JSONException {
        object.put(key, value);
    }

    public static void add(final JSONObject object, final String key, final String[] value) throws JSONException {
        final JSONArray arr = new JSONArray();

        if (value != null && value.length > 0) {
            for (final String str : value) {
                arr.put(str);
            }
        }

        object.put(key, arr);
    }


    public static boolean getBoolean(final JSONObject object, final String key) {
        return object.optBoolean(key, false);
    }

    public static int getInt(final JSONObject object, final String key) {
        return object.optInt(key, 0);
    }

    public static String getString(final JSONObject object, final String key) {
        final String res = object.optString(key, null);
        if (key != null && key.equalsIgnoreCase(NULL)) {
            return null;
        } else {
            return res;
        }
    }

    public static String[] getStringArray(final JSONObject payload, final String key) {
        final JSONArray arr = payload.optJSONArray(key);
        final String[] res;

        if (arr != null && arr.length() > 0) {
            final int count = arr.length();
            res = new String[count];

            for (int i = 0; i < count; i++) {
                res[i] = arr.optString(i);
            }
        } else {
            res = null;
        }

        return res;
    }
}
