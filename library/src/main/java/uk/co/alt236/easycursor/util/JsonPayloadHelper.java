package uk.co.alt236.easycursor.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonPayloadHelper {
    private static final String NULL = "null";

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
            for (String str : value) {
                arr.put(str);
            }
        }

        object.put(key, arr);
    }


    public static boolean getBoolean(final JSONObject object, final String key) {
        return object.optBoolean(key, false);
    }

    public static int getInt(JSONObject object, String key) {
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

    public static String[] getStringArray(JSONObject payload, String key) {
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
