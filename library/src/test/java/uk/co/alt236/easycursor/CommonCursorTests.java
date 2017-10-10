/*
 * ***************************************************************************
 * Copyright 2017 Alexandros Schillings
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

package uk.co.alt236.easycursor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import uk.co.alt236.easycursor.objectcursor.EasyObjectCursor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE, sdk = {16, 17, 18, 19, 21, 22, 23, 24, 25})
public abstract class CommonCursorTests implements EasyCursorTests {

    private static final String FIELD_DOES_NOT_EXIST = "FIELD_DOES_NOT_EXIST";
    private EasyCursor mSut;

    public void setCursor(final EasyCursor easyCursor) {
        mSut = easyCursor;
    }

    @Override
    @Test
    public void parsesBooleanFieldsThatExistAndHaveValues() {
        final String fieldName = "bool";

        mSut.moveToPosition(0);
        assertEquals(false, mSut.getBoolean(fieldName));
        assertEquals(EasyObjectCursor.DEFAULT_BOOLEAN, mSut.optBoolean(fieldName));
        assertEquals(Boolean.FALSE, mSut.optBooleanAsWrapperType(fieldName));

        mSut.moveToPosition(1);
        assertEquals(true, mSut.getBoolean(fieldName));
        assertEquals(true, mSut.optBoolean(fieldName));
        assertEquals(Boolean.TRUE, mSut.optBooleanAsWrapperType(fieldName));
    }

    @Test
    @Override
    public void parsesBooleanFieldsThatExistAndHaveNullValues() {
        final String fieldName = "bool";

        mSut.moveToPosition(2); // Field exists but it is null
        assertEquals(EasyObjectCursor.DEFAULT_BOOLEAN, mSut.getBoolean(fieldName));
        assertEquals(EasyObjectCursor.DEFAULT_BOOLEAN, mSut.optBoolean(fieldName));
        assertEquals(EasyObjectCursor.DEFAULT_BOOLEAN, mSut.optBoolean(fieldName, true));
        assertEquals(EasyObjectCursor.DEFAULT_BOOLEAN, mSut.optBooleanAsWrapperType(fieldName));
    }

    @Test
    @Override
    public void parsesBooleanFieldsThatDoNotExist() {
        final String DOES_NOT_EXIST = "FIELD_DOES_NOT_EXIST";

        mSut.moveToPosition(0); // Field does NOT exist
        assertEquals(EasyObjectCursor.DEFAULT_BOOLEAN, mSut.optBoolean(DOES_NOT_EXIST));
        assertEquals(true, mSut.optBoolean(DOES_NOT_EXIST, true));
        assertEquals(null, mSut.optBooleanAsWrapperType(DOES_NOT_EXIST));
    }

    @Test(expected = IllegalArgumentException.class)
    @Override
    public void throwsExceptionOnBooleanFieldsWhenGettingANullValue() {
        mSut.moveToPosition(0); // Field does NOT exist
        mSut.getBoolean(FIELD_DOES_NOT_EXIST);
    }

    @Override
    @Test
    public void testDoubleFieldParsing() {
        final String fieldName = "double";

        mSut.moveToPosition(0);
        assertEquals(Double.MIN_VALUE, mSut.getDouble(fieldName), 0);
        assertEquals(Double.MIN_VALUE, mSut.optDouble(fieldName), 0);
        assertEquals(Double.MIN_VALUE, mSut.optDoubleAsWrapperType(fieldName), 0);

        mSut.moveToPosition(1);
        assertEquals(Double.MAX_VALUE, mSut.getDouble(fieldName), 0);
        assertEquals(Double.MAX_VALUE, mSut.optDouble(fieldName), 0);
        assertEquals(Double.MAX_VALUE, mSut.optDoubleAsWrapperType(fieldName), 0);

        mSut.moveToPosition(2);
        assertEquals(EasyObjectCursor.DEFAULT_DOUBLE, mSut.getDouble(fieldName), 0);
        assertEquals(EasyObjectCursor.DEFAULT_DOUBLE, mSut.optDouble(fieldName), 0);
        assertEquals(EasyObjectCursor.DEFAULT_DOUBLE, mSut.optDouble(fieldName, 0.2D), 0);
        assertEquals(EasyObjectCursor.DEFAULT_DOUBLE, mSut.optDoubleAsWrapperType(fieldName), 0);

        // Non existant
        mSut.moveToPosition(0);
        assertEquals(EasyObjectCursor.DEFAULT_DOUBLE, mSut.optDouble(FIELD_DOES_NOT_EXIST), 0);
        assertEquals(0.2D, mSut.optDouble(FIELD_DOES_NOT_EXIST, 0.2D), 0);
        assertEquals(null, mSut.optDoubleAsWrapperType(FIELD_DOES_NOT_EXIST));
        try {
            mSut.getDouble(FIELD_DOES_NOT_EXIST);
            fail("this should have blown");
        } catch (final IllegalArgumentException e) {
            // expected
        }

        mSut.close();
    }

    @Override
    @Test
    public void testFloatFieldParsing() {
        final String fieldName = "float";

        mSut.moveToPosition(0);
        assertEquals(Float.MIN_VALUE, mSut.getFloat(fieldName), 0);
        assertEquals(Float.MIN_VALUE, mSut.optFloat(fieldName), 0);
        assertEquals(Float.MIN_VALUE, mSut.optFloatAsWrapperType(fieldName), 0);

        mSut.moveToPosition(1);
        assertEquals(Float.MAX_VALUE, mSut.getFloat(fieldName), 0);
        assertEquals(Float.MAX_VALUE, mSut.optFloat(fieldName), 0);
        assertEquals(Float.MAX_VALUE, mSut.optFloatAsWrapperType(fieldName), 0);

        mSut.moveToPosition(2); // Field Exists and value is null
        assertEquals(EasyObjectCursor.DEFAULT_FLOAT, mSut.getFloat(fieldName), 0);
        assertEquals(EasyObjectCursor.DEFAULT_FLOAT, mSut.optFloat(fieldName), 0);
        assertEquals(0.0F, mSut.optFloat(fieldName, 0.2F), 0);
        assertEquals(0.0F, mSut.optFloatAsWrapperType(fieldName), 0);

        // Non existant
        mSut.moveToPosition(0); // Field does NOT exist
        assertEquals(EasyObjectCursor.DEFAULT_FLOAT, mSut.optFloat(FIELD_DOES_NOT_EXIST), 0);
        assertEquals(0.3F, mSut.optFloat(FIELD_DOES_NOT_EXIST, 0.3F), 0);
        assertEquals(null, mSut.optFloatAsWrapperType(FIELD_DOES_NOT_EXIST));
        try {
            mSut.getFloat(FIELD_DOES_NOT_EXIST);
            fail("this should have blown");
        } catch (final IllegalArgumentException e) {
            // expected
        }

        mSut.close();
    }

    @Override
    @Test
    public void testIndexFetching() {
        assertTrue(mSut.getColumnIndex("int") != -1);
        assertTrue(mSut.getColumnIndex("bool") != -1);
        assertTrue(mSut.getColumnIndex("float") != -1);
        assertTrue(mSut.getColumnIndex("double") != -1);
        assertTrue(mSut.getColumnIndex("_id") == -1);
        assertTrue(mSut.getColumnIndex("not_exists") == -1);

        assertTrue(mSut.getColumnIndexOrThrow("int") != -1);
        assertTrue(mSut.getColumnIndexOrThrow("bool") != -1);
        assertTrue(mSut.getColumnIndexOrThrow("float") != -1);
        assertTrue(mSut.getColumnIndexOrThrow("double") != -1);

        try {
            assertTrue(mSut.getColumnIndexOrThrow("_id") == -1);
            fail("this should have blown");
        } catch (final IllegalArgumentException e) {
            //
        }

        try {
            assertTrue(mSut.getColumnIndexOrThrow("not_exists") == -1);
            fail("this should have blown");
        } catch (final IllegalArgumentException e) {
            //
        }
    }

    @Override
    @Test
    public void testIntegerFieldParsing() {
        final String fieldName = "int";

        mSut.moveToPosition(0);
        assertEquals(Integer.MIN_VALUE, mSut.getInt(fieldName));
        assertEquals(Integer.MIN_VALUE, mSut.optInt(fieldName));
        assertEquals((Integer) Integer.MIN_VALUE, mSut.optIntAsWrapperType(fieldName));

        mSut.moveToPosition(1);
        assertEquals(Integer.MAX_VALUE, mSut.getInt(fieldName));
        assertEquals(Integer.MAX_VALUE, mSut.optInt(fieldName));
        assertEquals((Integer) Integer.MAX_VALUE, mSut.optIntAsWrapperType(fieldName));

        mSut.moveToPosition(2);
        assertEquals(EasyObjectCursor.DEFAULT_INT, mSut.getInt(fieldName));
        assertEquals(EasyObjectCursor.DEFAULT_INT, mSut.optInt(fieldName));
        assertEquals(EasyObjectCursor.DEFAULT_INT, mSut.optInt(fieldName, 33));
        assertEquals((Integer) EasyObjectCursor.DEFAULT_INT, mSut.optIntAsWrapperType(fieldName));

        // Non existant
        mSut.moveToPosition(0);
        assertEquals(EasyObjectCursor.DEFAULT_INT, mSut.optInt(FIELD_DOES_NOT_EXIST));
        assertEquals(44, mSut.optInt(FIELD_DOES_NOT_EXIST, 44));
        assertEquals(null, mSut.optIntAsWrapperType(FIELD_DOES_NOT_EXIST));
        try {
            mSut.getInt(FIELD_DOES_NOT_EXIST);
            fail("this should have blown");
        } catch (final IllegalArgumentException e) {
            // expected
        }

        mSut.close();
    }

    @Override
    @Test
    public void testLongFieldParsing() {
        final String fieldName = "long";

        mSut.moveToPosition(0);
        assertEquals(Long.MIN_VALUE, mSut.getLong(fieldName));
        assertEquals(Long.MIN_VALUE, mSut.optLong(fieldName));
        assertEquals((Long) Long.MIN_VALUE, mSut.optLongAsWrapperType(fieldName));

        mSut.moveToPosition(1);
        assertEquals(Long.MAX_VALUE, mSut.getLong(fieldName));
        assertEquals(Long.MAX_VALUE, mSut.optLong(fieldName));
        assertEquals((Long) Long.MAX_VALUE, mSut.optLongAsWrapperType(fieldName));

        mSut.moveToPosition(2);
        assertEquals(EasyObjectCursor.DEFAULT_LONG, mSut.getLong(fieldName));
        assertEquals(EasyObjectCursor.DEFAULT_LONG, mSut.optLong(fieldName));
        assertEquals(EasyObjectCursor.DEFAULT_LONG, mSut.optLong(fieldName, 33));
        assertEquals((Long) EasyObjectCursor.DEFAULT_LONG, mSut.optLongAsWrapperType(fieldName));

        // Non existant
        mSut.moveToPosition(0);
        assertEquals(EasyObjectCursor.DEFAULT_LONG, mSut.optLong(FIELD_DOES_NOT_EXIST));
        assertEquals(44, mSut.optLong(FIELD_DOES_NOT_EXIST, 44));
        assertEquals(null, mSut.optLongAsWrapperType(FIELD_DOES_NOT_EXIST));

        try {
            mSut.getLong(FIELD_DOES_NOT_EXIST);
            fail("this should have blown");
        } catch (final IllegalArgumentException e) {
            // expected
        }

        mSut.close();
    }

    @Override
    @Test
    public void testShortFieldParsing() {
        final String fieldName = "short";

        mSut.moveToPosition(0);
        assertEquals(Short.MIN_VALUE, mSut.getShort(fieldName));
        assertEquals(Short.MIN_VALUE, mSut.optShort(fieldName));
        assertEquals((Short) Short.MIN_VALUE, mSut.optShortAsWrapperType(fieldName));

        mSut.moveToPosition(1);
        assertEquals(Short.MAX_VALUE, mSut.getShort(fieldName));
        assertEquals(Short.MAX_VALUE, mSut.optShort(fieldName));
        assertEquals((Short) Short.MAX_VALUE, mSut.optShortAsWrapperType(fieldName));

        mSut.moveToPosition(2);
        assertEquals(EasyObjectCursor.DEFAULT_SHORT, mSut.getShort(fieldName));
        assertEquals(EasyObjectCursor.DEFAULT_SHORT, mSut.optShort(fieldName));
        assertEquals(EasyObjectCursor.DEFAULT_SHORT, mSut.optShort(fieldName, (short) 2));
        assertEquals((Short) EasyObjectCursor.DEFAULT_SHORT, mSut.optShortAsWrapperType(fieldName));

        // Non existant
        mSut.moveToPosition(0);
        assertEquals(EasyObjectCursor.DEFAULT_SHORT, mSut.optShort(FIELD_DOES_NOT_EXIST));
        assertEquals(3, mSut.optShort(FIELD_DOES_NOT_EXIST, (short) 3));
        assertEquals(null, mSut.optShortAsWrapperType(FIELD_DOES_NOT_EXIST));
        try {
            mSut.getShort(FIELD_DOES_NOT_EXIST);
            fail("this should have blown");
        } catch (final IllegalArgumentException e) {
            // expected
        }

        mSut.close();
    }

    @Override
    @Test
    public void testStringFieldParsing() {
        final String fieldName = "string";

        mSut.moveToPosition(0);
        assertEquals("foo", mSut.getString(fieldName));
        assertEquals("foo", mSut.optString(fieldName));

        mSut.moveToPosition(1);
        assertEquals("bar", mSut.getString(fieldName));
        assertEquals("bar", mSut.optString(fieldName));

        mSut.moveToPosition(2);
        assertEquals(EasyObjectCursor.DEFAULT_STRING, mSut.getString(fieldName));
        assertEquals(EasyObjectCursor.DEFAULT_STRING, mSut.optString(fieldName));
        assertEquals(EasyObjectCursor.DEFAULT_STRING, mSut.optString(fieldName, "baz"));

        // Non existant
        mSut.moveToPosition(0);
        assertEquals(EasyObjectCursor.DEFAULT_STRING, mSut.optString(FIELD_DOES_NOT_EXIST));
        assertEquals("qux", mSut.optString(FIELD_DOES_NOT_EXIST, "qux"));
        try {
            mSut.getString(FIELD_DOES_NOT_EXIST);
            fail("this should have blown");
        } catch (final IllegalArgumentException e) {
            // expected
        }

        mSut.close();
    }

    @Override
    @Test
    public void testFieldIndexes() {
        mSut.moveToFirst();
        final String[] cols = mSut.getColumnNames();

        int index = 0;
        for (final String col : cols) {
            final int cursorFieldIndex = mSut.getColumnIndex(col);
            assertEquals(index, cursorFieldIndex);
            final String cursorFieldName = mSut.getColumnName(cursorFieldIndex);
            assertEquals(col, cursorFieldName);
            index++;
        }

        mSut.close();
    }

    @Override
    @Test
    public void testNullCheck() {
        final String[] cols = mSut.getColumnNames();

        mSut.moveToFirst();

        mSut.moveToPosition(0);
        for (final String col : cols) {
            if ("null".equals(col)) {
                assertTrue("Should have been null: " + col, mSut.isNull(col));
            } else {
                assertFalse("Should NOT have been null: " + col, mSut.isNull(col));
            }
        }

        mSut.moveToPosition(1);
        for (final String col : cols) {
            if ("null".equals(col)) {
                assertTrue("Should have been null: " + col, mSut.isNull(col));
            } else {
                assertFalse("Should NOT have been null: " + col, mSut.isNull(col));
            }
        }

        mSut.moveToPosition(2);
        for (final String col : cols) {
            if (!"class".equals(col)) { // this is the base Object class field
                assertTrue("Should have been null: " + col, mSut.isNull(col));
            }
        }

        mSut.close();
    }

}
