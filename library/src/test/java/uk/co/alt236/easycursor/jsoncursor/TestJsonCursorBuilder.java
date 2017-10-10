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

package uk.co.alt236.easycursor.jsoncursor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import uk.co.alt236.easycursor.EasyCursor;

/**
 *
 */
/*package*/ final class TestJsonCursorBuilder {

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
            object3.put("bool", JSONObject.NULL);
            object3.put("string", JSONObject.NULL);
            object3.put("byte", JSONObject.NULL);
            object3.put("double", JSONObject.NULL);
            object3.put("float", JSONObject.NULL);
            object3.put("int", JSONObject.NULL);
            object3.put("long", JSONObject.NULL);
            object3.put("short", JSONObject.NULL);

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
