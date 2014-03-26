package uk.co.alt236.easycursor.sampleapp.test;

import junit.framework.Assert;
import uk.co.alt236.easycursor.EasyCursor;
import uk.co.alt236.easycursor.objectcursor.ObjectCursor;
import uk.co.alt236.easycursor.sqlcursor.EasySqlCursor;

public abstract class CommonTestCases extends android.test.AndroidTestCase{

	protected void testBooleanFieldParsing(EasyCursor cursor){
		// Real data
		final String NON_EXISTANT_COL = "THIS_COLUMN_DOES_NOT_EXIST";
		final String EXISTANT_COL = "hascomposer";

		Assert.assertTrue(cursor.getBoolean(EXISTANT_COL));
		Assert.assertTrue(cursor.optBooleanAsWrapperType(EXISTANT_COL));
		Assert.assertEquals(cursor.optBoolean(NON_EXISTANT_COL), EasySqlCursor.DEFAULT_BOOLEAN);
		Assert.assertTrue(cursor.optBoolean(NON_EXISTANT_COL, true));
		Assert.assertNull(cursor.optBooleanAsWrapperType(NON_EXISTANT_COL));
	}

	protected void testDoubleFieldParsing(EasyCursor cursor){
		final String NON_EXISTANT_COL = "THIS_COLUMN_DOES_NOT_EXIST";
		final String EXISTANT_COL = "meaninglessDiv";
		final double EXPECTED_VALUE = 720845.345345345;
		final double FALLBACK = -9.99;
		final double DELTA = 0.0100;


		Assert.assertEquals(cursor.getDouble(EXISTANT_COL), EXPECTED_VALUE, DELTA);
		Assert.assertEquals((double) cursor.optDoubleAsWrapperType(EXISTANT_COL), EXPECTED_VALUE, DELTA);
		Assert.assertEquals(cursor.optDouble(NON_EXISTANT_COL), EasySqlCursor.DEFAULT_DOUBLE, DELTA);
		Assert.assertEquals(cursor.optDouble(NON_EXISTANT_COL, FALLBACK), FALLBACK, DELTA);
		Assert.assertNull(cursor.optDoubleAsWrapperType(NON_EXISTANT_COL));
	}

	protected void testFloatFieldParsing(EasyCursor cursor){
		final String NON_EXISTANT_COL = "THIS_COLUMN_DOES_NOT_EXIST";
		final String EXISTANT_COL = "meaninglessDiv";
		final float EXPECTED_VALUE = 720845.345345345f;
		final float FALLBACK = -9.99f;

		Assert.assertEquals(cursor.getFloat(EXISTANT_COL), EXPECTED_VALUE);
		Assert.assertEquals((float) cursor.optFloatAsWrapperType(EXISTANT_COL), EXPECTED_VALUE);
		Assert.assertEquals(cursor.optFloat(NON_EXISTANT_COL), EasySqlCursor.DEFAULT_FLOAT);
		Assert.assertEquals(cursor.optFloat(NON_EXISTANT_COL, FALLBACK), FALLBACK);
		Assert.assertNull(cursor.optFloatAsWrapperType(NON_EXISTANT_COL));
	}

	protected void testIntegerFieldParsing(EasyCursor cursor){
		final String NON_EXISTANT_COL = "THIS_COLUMN_DOES_NOT_EXIST";
		final String EXISTANT_COL = "_id";
		final int EXPECTED_VALUE = 14;
		final int FALLBACK = -100;

		Assert.assertEquals(cursor.getInt(EXISTANT_COL), EXPECTED_VALUE);
		Assert.assertEquals((int) cursor.optIntAsWrapperType(EXISTANT_COL), EXPECTED_VALUE);
		Assert.assertEquals(cursor.optInt(NON_EXISTANT_COL), EasySqlCursor.DEFAULT_INT);
		Assert.assertEquals(cursor.optInt(NON_EXISTANT_COL, FALLBACK), FALLBACK);
		Assert.assertNull(cursor.optIntAsWrapperType(NON_EXISTANT_COL));
	}

	protected void testLongFieldParsing(EasyCursor cursor){
		final String NON_EXISTANT_COL = "THIS_COLUMN_DOES_NOT_EXIST";
		final String EXISTANT_COL = "meaninglessSum";
		final long EXPECTED_VALUE = 2400415l;
		final long FALLBACK = -100l;

		Assert.assertEquals(cursor.getLong(EXISTANT_COL), EXPECTED_VALUE);
		Assert.assertEquals((long) cursor.optLongAsWrapperType(EXISTANT_COL), EXPECTED_VALUE);
		Assert.assertEquals(cursor.optLong(NON_EXISTANT_COL), EasySqlCursor.DEFAULT_LONG);
		Assert.assertEquals(cursor.optLong(NON_EXISTANT_COL, FALLBACK), FALLBACK);
		Assert.assertNull(cursor.optLongAsWrapperType(NON_EXISTANT_COL));
	}

	protected void testStringFieldParsing(EasyCursor cursor){
		// And now for some real data
		final String NON_EXISTANT_COL = "THIS_COLUMN_DOES_NOT_EXIST";
		final String EXISTANT_COL = "artist";
		final String EXPECTED_VALUE = "AC/DC";
		final String FALLBACK = "lalalala";

		Assert.assertEquals(cursor.getString(EXISTANT_COL), EXPECTED_VALUE);
		Assert.assertEquals(cursor.optString(NON_EXISTANT_COL), ObjectCursor.DEFAULT_STRING);
		Assert.assertEquals(cursor.optString (NON_EXISTANT_COL, FALLBACK), FALLBACK);
	}
}
