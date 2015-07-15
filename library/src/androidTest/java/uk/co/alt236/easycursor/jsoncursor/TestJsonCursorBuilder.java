package uk.co.alt236.easycursor.jsoncursor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import uk.co.alt236.easycursor.EasyCursor;

/**
 *
 */
public final class TestJsonCursorBuilder {

    private TestJsonCursorBuilder() {
    }

    private static JSONArray getArray() {
        final JSONArray array = new JSONArray();

        try {
            final JSONObject object1 = new JSONObject();
            object1.put("bool", false);
            object1.put("string", "foo");
            object1.put("byte", toByteArray("foo"));
            object1.put("double", Double.MIN_VALUE);
            object1.put("float", Float.MIN_VALUE);
            object1.put("int", Integer.MIN_VALUE);
            object1.put("long", Long.MIN_VALUE);
            object1.put("short", Short.MIN_VALUE);

            final JSONObject object2 = new JSONObject();
            object2.put("bool", true);
            object2.put("string", "bar");
            object2.put("byte", toByteArray("bar"));
            object2.put("double", Double.MAX_VALUE);
            object2.put("float", Float.MAX_VALUE);
            object2.put("int", Integer.MAX_VALUE);
            object2.put("long", Long.MAX_VALUE);
            object2.put("short", Short.MAX_VALUE);

            final JSONObject object3 = new JSONObject();
            object3.put("bool", null);
            object3.put("string", null);
            object3.put("byte", null);
            object3.put("double", null);
            object3.put("float", null);
            object3.put("int", null);
            object3.put("long", null);
            object3.put("short", null);

            array.put(object1);
            array.put(object2);
            array.put(object3);
        } catch (final JSONException e) {
            // SHOULD NEVER HAPPEN
            throw new IllegalStateException(e);
        }

        return array;
    }

    public static EasyCursor getCursor(final String alias) {
        return new EasyJsonCursor(getArray(), alias);
    }

    public static EasyCursor getCursor() {
        return getCursor(null);
    }

    private static byte[] toByteArray(final String string) {
        try {
            return string.getBytes("UTF-8");
        } catch (final Exception e) {
            // will never be thrown
            throw new IllegalStateException(e);
        }
    }
}
