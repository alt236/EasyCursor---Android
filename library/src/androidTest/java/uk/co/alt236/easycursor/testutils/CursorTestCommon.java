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

package uk.co.alt236.easycursor.testutils;

import junit.framework.TestCase;

import uk.co.alt236.easycursor.EasyCursor;
import uk.co.alt236.easycursor.sqlcursor.EasySqlCursor;

public abstract class CursorTestCommon extends TestCase {

    protected void testBooleanFieldParsing(final EasyCursor cursor) {
        // Real data
        final String NON_EXISTANT_COL = "THIS_COLUMN_DOES_NOT_EXIST";
        final String EXISTANT_COL = "hascomposer";

        assertTrue(cursor.getBoolean(EXISTANT_COL));
        assertTrue(cursor.optBooleanAsWrapperType(EXISTANT_COL));
        assertEquals(cursor.optBoolean(NON_EXISTANT_COL), EasySqlCursor.DEFAULT_BOOLEAN);
        assertTrue(cursor.optBoolean(NON_EXISTANT_COL, true));
        assertNull(cursor.optBooleanAsWrapperType(NON_EXISTANT_COL));
    }

    protected void testDoubleFieldParsing(final EasyCursor cursor) {
        final String NON_EXISTANT_COL = "THIS_COLUMN_DOES_NOT_EXIST";
        final String EXISTANT_COL = "meaninglessDiv";
        final double EXPECTED_VALUE = 720845.345345345;
        final double FALLBACK = -9.99;
        final double DELTA = 0.0100;


        assertEquals(cursor.getDouble(EXISTANT_COL), EXPECTED_VALUE, DELTA);
        assertEquals((double) cursor.optDoubleAsWrapperType(EXISTANT_COL), EXPECTED_VALUE, DELTA);
        assertEquals(cursor.optDouble(NON_EXISTANT_COL), EasySqlCursor.DEFAULT_DOUBLE, DELTA);
        assertEquals(cursor.optDouble(NON_EXISTANT_COL, FALLBACK), FALLBACK, DELTA);
        assertNull(cursor.optDoubleAsWrapperType(NON_EXISTANT_COL));
    }

    protected void testFloatFieldParsing(final EasyCursor cursor) {
        final String NON_EXISTANT_COL = "THIS_COLUMN_DOES_NOT_EXIST";
        final String EXISTANT_COL = "meaninglessDiv";
        final float EXPECTED_VALUE = 720845.345345345f;
        final float FALLBACK = -9.99f;

        assertEquals(cursor.getFloat(EXISTANT_COL), EXPECTED_VALUE);
        assertEquals((float) cursor.optFloatAsWrapperType(EXISTANT_COL), EXPECTED_VALUE);
        assertEquals(cursor.optFloat(NON_EXISTANT_COL), EasySqlCursor.DEFAULT_FLOAT);
        assertEquals(cursor.optFloat(NON_EXISTANT_COL, FALLBACK), FALLBACK);
        assertNull(cursor.optFloatAsWrapperType(NON_EXISTANT_COL));
    }

    protected void testIntegerFieldParsing(final EasyCursor cursor) {
        final String NON_EXISTANT_COL = "THIS_COLUMN_DOES_NOT_EXIST";
        final String EXISTANT_COL = "_id";
        final int EXPECTED_VALUE = 14;
        final int FALLBACK = -100;

        assertEquals(cursor.getInt(EXISTANT_COL), EXPECTED_VALUE);
        assertEquals((int) cursor.optIntAsWrapperType(EXISTANT_COL), EXPECTED_VALUE);
        assertEquals(cursor.optInt(NON_EXISTANT_COL), EasySqlCursor.DEFAULT_INT);
        assertEquals(cursor.optInt(NON_EXISTANT_COL, FALLBACK), FALLBACK);
        assertNull(cursor.optIntAsWrapperType(NON_EXISTANT_COL));
    }

    protected void testLongFieldParsing(final EasyCursor cursor) {
        final String NON_EXISTANT_COL = "THIS_COLUMN_DOES_NOT_EXIST";
        final String EXISTANT_COL = "meaninglessSum";
        final long EXPECTED_VALUE = 2400415l;
        final long FALLBACK = -100l;

        assertEquals(cursor.getLong(EXISTANT_COL), EXPECTED_VALUE);
        assertEquals((long) cursor.optLongAsWrapperType(EXISTANT_COL), EXPECTED_VALUE);
        assertEquals(cursor.optLong(NON_EXISTANT_COL), EasySqlCursor.DEFAULT_LONG);
        assertEquals(cursor.optLong(NON_EXISTANT_COL, FALLBACK), FALLBACK);
        assertNull(cursor.optLongAsWrapperType(NON_EXISTANT_COL));
    }

    protected void testStringFieldParsing(final EasyCursor cursor) {
        // And now for some real data
        final String NON_EXISTANT_COL = "THIS_COLUMN_DOES_NOT_EXIST";
        final String EXISTANT_COL = "artist";
        final String EXPECTED_VALUE = "AC/DC";
        final String FALLBACK = "lalalala";

        assertEquals(cursor.getString(EXISTANT_COL), EXPECTED_VALUE);
        assertEquals(cursor.optString(NON_EXISTANT_COL), EasySqlCursor.DEFAULT_STRING);
        assertEquals(cursor.optString(NON_EXISTANT_COL, FALLBACK), FALLBACK);
    }
}
