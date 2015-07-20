package uk.co.alt236.easycursor.sqlcursor;

import android.test.AndroidTestCase;

import uk.co.alt236.easycursor.EasyCursor;

/**
 *
 */
public class EasySqlCursorTest extends AndroidTestCase {
    private DatabaseHandler mHandler;

    private EasyCursor query() {
        return new EasySqlCursor(mHandler.getReadableDatabase().rawQuery("SELECT * FROM DATA", null));
    }

    @Override
    public void setUp() {
        if (mHandler == null) {
            mHandler = new DatabaseHandler(getContext());
            mHandler.getWritableDatabase();
        }
    }

    public void testBooleanFieldParsing() {
        final EasyCursor cursor = query();

        final String fieldName = "bool";
        final String does_not_exist = "does_not_exist";

        cursor.moveToPosition(0); // Field exists and has value
        assertEquals(false, cursor.getBoolean(fieldName));
        assertEquals(EasySqlCursor.DEFAULT_BOOLEAN, cursor.optBoolean(fieldName));
        assertEquals(Boolean.FALSE, cursor.optBooleanAsWrapperType(fieldName));

        cursor.moveToPosition(1); // Field exists and has value
        assertEquals(true, cursor.getBoolean(fieldName));
        assertEquals(true, cursor.optBoolean(fieldName));
        assertEquals(Boolean.TRUE, cursor.optBooleanAsWrapperType(fieldName));

        cursor.moveToPosition(2); // Field exists but it is null
        assertEquals(EasySqlCursor.DEFAULT_BOOLEAN, cursor.getBoolean(fieldName));
        assertEquals(EasySqlCursor.DEFAULT_BOOLEAN, cursor.optBoolean(fieldName));
        assertEquals(EasySqlCursor.DEFAULT_BOOLEAN, cursor.optBoolean(fieldName, true));
        assertEquals((Boolean) EasySqlCursor.DEFAULT_BOOLEAN, cursor.optBooleanAsWrapperType(fieldName));

        // Non existant
        cursor.moveToPosition(0); // Field does NOT exist
        assertEquals(EasySqlCursor.DEFAULT_BOOLEAN, cursor.optBoolean(does_not_exist));
        assertEquals(true, cursor.optBoolean(does_not_exist, true));
        assertEquals(null, cursor.optBooleanAsWrapperType(does_not_exist));
        try {
            cursor.getBoolean(does_not_exist);
            fail("this should have blown");
        } catch (final IllegalArgumentException e) {
            // expected
        }

        cursor.close();
    }

    public void testDoubleFieldParsing() {
        final EasyCursor cursor = query();

        final String fieldName = "double";
        final String does_not_exist = "does_not_exist";

        cursor.moveToPosition(0);
        assertEquals(Double.MIN_VALUE, cursor.getDouble(fieldName));
        assertEquals(Double.MIN_VALUE, cursor.optDouble(fieldName));
        assertEquals(Double.MIN_VALUE, cursor.optDoubleAsWrapperType(fieldName));

        cursor.moveToPosition(1);
        assertEquals(Double.MAX_VALUE, cursor.getDouble(fieldName));
        assertEquals(Double.MAX_VALUE, cursor.optDouble(fieldName));
        assertEquals(Double.MAX_VALUE, cursor.optDoubleAsWrapperType(fieldName));

        cursor.moveToPosition(2);
        assertEquals(EasySqlCursor.DEFAULT_DOUBLE, cursor.getDouble(fieldName));
        assertEquals(EasySqlCursor.DEFAULT_DOUBLE, cursor.optDouble(fieldName));
        assertEquals(EasySqlCursor.DEFAULT_DOUBLE, cursor.optDouble(fieldName, 0.2D));
        assertEquals(EasySqlCursor.DEFAULT_DOUBLE, cursor.optDoubleAsWrapperType(fieldName));

        // Non existant
        cursor.moveToPosition(0);
        assertEquals(EasySqlCursor.DEFAULT_DOUBLE, cursor.optDouble(does_not_exist));
        assertEquals(0.2D, cursor.optDouble(does_not_exist, 0.2D));
        assertEquals(null, cursor.optDoubleAsWrapperType(does_not_exist));
        try {
            cursor.getDouble(does_not_exist);
            fail("this should have blown");
        } catch (final IllegalArgumentException e) {
            // expected
        }

        cursor.close();
    }

    public void testFieldIndexes() {
        final EasyCursor cursor = query();

        cursor.moveToFirst();
        final String[] cols = cursor.getColumnNames();

        int index = 0;
        for (final String col : cols) {
            final int cursorFieldIndex = cursor.getColumnIndex(col);
            assertEquals(index, cursorFieldIndex);
            final String cursorFieldName = cursor.getColumnName(cursorFieldIndex);
            assertEquals(col, cursorFieldName);
            index++;
        }

        cursor.close();
    }

    public void testFloatFieldParsing() {
        final EasyCursor cursor = query();

        final String fieldName = "float";
        final String does_not_exist = "does_not_exist";

        cursor.moveToPosition(0); // Field Exists and has value
        assertEquals(Float.MIN_VALUE, cursor.getFloat(fieldName));
        assertEquals(Float.MIN_VALUE, cursor.optFloat(fieldName));
        assertEquals(Float.MIN_VALUE, cursor.optFloatAsWrapperType(fieldName));

        cursor.moveToPosition(1); // Field Exists and has value
        assertEquals(Float.MAX_VALUE, cursor.getFloat(fieldName));
        assertEquals(Float.MAX_VALUE, cursor.optFloat(fieldName));
        assertEquals(Float.MAX_VALUE, cursor.optFloatAsWrapperType(fieldName));

        cursor.moveToPosition(2); // Field Exists and value is null
        assertEquals(EasySqlCursor.DEFAULT_FLOAT, cursor.getFloat(fieldName));
        assertEquals(EasySqlCursor.DEFAULT_FLOAT, cursor.optFloat(fieldName));
        assertEquals(0.0F, cursor.optFloat(fieldName, 0.2F));
        assertEquals(0.0F, cursor.optFloatAsWrapperType(fieldName));

        // Non existant
        cursor.moveToPosition(0); // Field does NOT exist
        assertEquals(EasySqlCursor.DEFAULT_FLOAT, cursor.optFloat(does_not_exist));
        assertEquals(0.3F, cursor.optFloat(does_not_exist, 0.3F));
        assertEquals(null, cursor.optFloatAsWrapperType(does_not_exist));
        try {
            cursor.getFloat(does_not_exist);
            fail("this should have blown");
        } catch (final IllegalArgumentException e) {
            // expected
        }

        cursor.close();
    }

    public void testIndexFetching() {
        final EasyCursor cursor = query();

        assertTrue(cursor.getColumnIndex("int") != -1);
        assertTrue(cursor.getColumnIndex("bool") != -1);
        assertTrue(cursor.getColumnIndex("float") != -1);
        assertTrue(cursor.getColumnIndex("double") != -1);
        assertTrue(cursor.getColumnIndex("_id") == -1);
        assertTrue(cursor.getColumnIndex("not_exists") == -1);

        assertTrue(cursor.getColumnIndexOrThrow("int") != -1);
        assertTrue(cursor.getColumnIndexOrThrow("bool") != -1);
        assertTrue(cursor.getColumnIndexOrThrow("float") != -1);
        assertTrue(cursor.getColumnIndexOrThrow("double") != -1);

        try {
            assertTrue(cursor.getColumnIndexOrThrow("_id") == -1);
            fail("this should have blown");
        } catch (final IllegalArgumentException e) {
            //
        }

        try {
            assertTrue(cursor.getColumnIndexOrThrow("not_exists") == -1);
            fail("this should have blown");
        } catch (final IllegalArgumentException e) {
            //
        }
    }

    public void testIntegerFieldParsing() {
        final EasyCursor cursor = query();

        final String fieldName = "int";
        final String does_not_exist = "does_not_exist";

        cursor.moveToPosition(0);
        assertEquals(Integer.MIN_VALUE, cursor.getInt(fieldName));
        assertEquals(Integer.MIN_VALUE, cursor.optInt(fieldName));
        assertEquals((Integer) Integer.MIN_VALUE, cursor.optIntAsWrapperType(fieldName));

        cursor.moveToPosition(1);
        assertEquals(Integer.MAX_VALUE, cursor.getInt(fieldName));
        assertEquals(Integer.MAX_VALUE, cursor.optInt(fieldName));
        assertEquals((Integer) Integer.MAX_VALUE, cursor.optIntAsWrapperType(fieldName));

        cursor.moveToPosition(2);
        assertEquals(EasySqlCursor.DEFAULT_INT, cursor.getInt(fieldName));
        assertEquals(EasySqlCursor.DEFAULT_INT, cursor.optInt(fieldName));
        assertEquals(EasySqlCursor.DEFAULT_INT, cursor.optInt(fieldName, 33));
        assertEquals((Integer) EasySqlCursor.DEFAULT_INT, cursor.optIntAsWrapperType(fieldName));

        // Non existant
        cursor.moveToPosition(0);
        assertEquals(EasySqlCursor.DEFAULT_INT, cursor.optInt(does_not_exist));
        assertEquals(44, cursor.optInt(does_not_exist, 44));
        assertEquals(null, cursor.optIntAsWrapperType(does_not_exist));
        try {
            cursor.getInt(does_not_exist);
            fail("this should have blown");
        } catch (final IllegalArgumentException e) {
            // expected
        }

        cursor.close();
    }

    public void testLongFieldParsing() {
        final EasyCursor cursor = query();

        final String fieldName = "long";
        final String does_not_exist = "does_not_exist";

        cursor.moveToPosition(0);
        assertEquals(Long.MIN_VALUE, cursor.getLong(fieldName));
        assertEquals(Long.MIN_VALUE, cursor.optLong(fieldName));
        assertEquals((Long) Long.MIN_VALUE, cursor.optLongAsWrapperType(fieldName));

        cursor.moveToPosition(1);
        assertEquals(Long.MAX_VALUE, cursor.getLong(fieldName));
        assertEquals(Long.MAX_VALUE, cursor.optLong(fieldName));
        assertEquals((Long) Long.MAX_VALUE, cursor.optLongAsWrapperType(fieldName));

        cursor.moveToPosition(2);
        assertEquals(EasySqlCursor.DEFAULT_LONG, cursor.getLong(fieldName));
        assertEquals(EasySqlCursor.DEFAULT_LONG, cursor.optLong(fieldName));
        assertEquals(EasySqlCursor.DEFAULT_LONG, cursor.optLong(fieldName, 33));
        assertEquals((Long) EasySqlCursor.DEFAULT_LONG, cursor.optLongAsWrapperType(fieldName));

        // Non existant
        cursor.moveToPosition(0);
        assertEquals(EasySqlCursor.DEFAULT_LONG, cursor.optLong(does_not_exist));
        assertEquals(44, cursor.optLong(does_not_exist, 44));
        assertEquals(null, cursor.optLongAsWrapperType(does_not_exist));

        try {
            cursor.getLong(does_not_exist);
            fail("this should have blown");
        } catch (final IllegalArgumentException e) {
            // expected
        }

        cursor.close();
    }

    public void testNullCheck() {
        final EasyCursor cursor = query();
        final String[] cols = cursor.getColumnNames();

        cursor.moveToFirst();

        cursor.moveToPosition(0);
        for (final String col : cols) {
            if ("null".equals(col)) {
                assertTrue("Should have been null: " + col, cursor.isNull(col));
            } else {
                assertFalse("Should NOT have been null: " + col, cursor.isNull(col));
            }
        }

        cursor.moveToPosition(1);
        for (final String col : cols) {
            if ("null".equals(col)) {
                assertTrue("Should have been null: " + col, cursor.isNull(col));
            } else {
                assertFalse("Should NOT have been null: " + col, cursor.isNull(col));
            }
        }

        cursor.moveToPosition(2);
        for (final String col : cols) {
            if (!"class".equals(col)) { // this is the base Object class field
                assertTrue("Should have been null: " + col, cursor.isNull(col));
            }
        }

        cursor.close();
    }

    public void testShortFieldParsing() {
        final EasyCursor cursor = query();

        final String fieldName = "short";
        final String does_not_exist = "does_not_exist";

        cursor.moveToPosition(0);
        assertEquals(Short.MIN_VALUE, cursor.getShort(fieldName));
        assertEquals(Short.MIN_VALUE, cursor.optShort(fieldName));
        assertEquals((Short) Short.MIN_VALUE, cursor.optShortAsWrapperType(fieldName));

        cursor.moveToPosition(1);
        assertEquals(Short.MAX_VALUE, cursor.getShort(fieldName));
        assertEquals(Short.MAX_VALUE, cursor.optShort(fieldName));
        assertEquals((Short) Short.MAX_VALUE, cursor.optShortAsWrapperType(fieldName));

        cursor.moveToPosition(2);
        assertEquals(EasySqlCursor.DEFAULT_SHORT, cursor.getShort(fieldName));
        assertEquals(EasySqlCursor.DEFAULT_SHORT, cursor.optShort(fieldName));
        assertEquals(EasySqlCursor.DEFAULT_SHORT, cursor.optShort(fieldName, (short) 2));
        assertEquals((Short) EasySqlCursor.DEFAULT_SHORT, cursor.optShortAsWrapperType(fieldName));

        // Non existant
        cursor.moveToPosition(0);
        assertEquals(EasySqlCursor.DEFAULT_SHORT, cursor.optShort(does_not_exist));
        assertEquals(3, cursor.optShort(does_not_exist, (short) 3));
        assertEquals(null, cursor.optShortAsWrapperType(does_not_exist));
        try {
            cursor.getShort(does_not_exist);
            fail("this should have blown");
        } catch (final IllegalArgumentException e) {
            // expected
        }

        cursor.close();
    }

    public void testStringFieldParsing() {
        final EasyCursor cursor = query();

        final String fieldName = "string";
        final String does_not_exist = "does_not_exist";

        cursor.moveToPosition(0);
        assertEquals("foo", cursor.getString(fieldName));
        assertEquals("foo", cursor.optString(fieldName));

        cursor.moveToPosition(1);
        assertEquals("bar", cursor.getString(fieldName));
        assertEquals("bar", cursor.optString(fieldName));

        cursor.moveToPosition(2);
        assertEquals(EasySqlCursor.DEFAULT_STRING, cursor.getString(fieldName));
        assertEquals(EasySqlCursor.DEFAULT_STRING, cursor.optString(fieldName));
        assertEquals(EasySqlCursor.DEFAULT_STRING, cursor.optString(fieldName, "baz"));

        // Non existant
        cursor.moveToPosition(0);
        assertEquals(EasySqlCursor.DEFAULT_STRING, cursor.optString(does_not_exist));
        assertEquals("qux", cursor.optString(does_not_exist, "qux"));
        try {
            cursor.getString(does_not_exist);
            fail("this should have blown");
        } catch (final IllegalArgumentException e) {
            // expected
        }

        cursor.close();
    }
}