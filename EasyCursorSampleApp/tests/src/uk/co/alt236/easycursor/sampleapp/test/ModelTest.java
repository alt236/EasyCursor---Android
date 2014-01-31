package uk.co.alt236.easycursor.sampleapp.test;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import uk.co.alt236.easycursor.sampleapp.database.DbSingleton;
import uk.co.alt236.easycursor.sampleapp.database.ExampleDatabase;
import uk.co.alt236.easycursor.sqlcursor.EasySqlCursor;
import uk.co.alt236.easycursor.sqlcursor.EasySqlQueryModel;
import android.util.Log;

public class ModelTest extends android.test.AndroidTestCase {
	private final String TAG = getClass().getName();

	private List<EasySqlQueryModel> mSelectQueryModelList;
	private List<EasySqlQueryModel> mRawQueryModelList;
	private List<EasySqlQueryModel> mCombinedList;

	private ExampleDatabase mDb;


	@Override
	protected void setUp() throws Exception {
		super.setUp();
		mSelectQueryModelList = new ArrayList<EasySqlQueryModel>();
		mRawQueryModelList = new ArrayList<EasySqlQueryModel>();
		mCombinedList = new ArrayList<EasySqlQueryModel>();
		mDb = DbSingleton.getInstance(getContext());

		final List<EasySqlQueryModel> tempSelectList = new ArrayList<EasySqlQueryModel>();

		tempSelectList.add(StaticModelBuilder.getCompatQueryModel());
		tempSelectList.add(StaticModelBuilder.getCustomBuilderModel());
		tempSelectList.add(StaticModelBuilder.getDefaultSelectModel());

		mSelectQueryModelList.addAll(tempSelectList);
		for(final EasySqlQueryModel model : tempSelectList){
			final String json = model.toJson();
			mSelectQueryModelList.add(new EasySqlQueryModel(json));
		}

		final List<EasySqlQueryModel> tempRawList = new ArrayList<EasySqlQueryModel>();
		tempRawList.add(StaticModelBuilder.getRawQueryModel());

		mRawQueryModelList.addAll(tempRawList);
		for(final EasySqlQueryModel model : tempRawList){
			final String json = model.toJson();
			mRawQueryModelList.add(new EasySqlQueryModel(json));
		}

		mCombinedList.addAll(mRawQueryModelList);
		mCombinedList.addAll(mSelectQueryModelList);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		mSelectQueryModelList = null;
		mRawQueryModelList = null;
		mCombinedList = null;
	}

	public void testBooleanFieldParsing(){
		final String NON_EXISTANT_COL = "THIS_COLUMN_DOES_NOT_EXIST";
		final String EXISTANT_COL = "hascomposer";

		for(int i = 0; i < mCombinedList.size(); i++){
			Log.v(TAG, "testBooleanFieldParsing: " + i);
			final EasySqlCursor cursor = (EasySqlCursor) mCombinedList.get(i).execute(mDb.getReadableDatabase());

			Assert.assertTrue(cursor.getBoolean(EXISTANT_COL));
			Assert.assertTrue(cursor.optBooleanAsWrapperType(EXISTANT_COL));

			Assert.assertEquals(cursor.optBoolean(NON_EXISTANT_COL), EasySqlCursor.DEFAULT_BOOLEAN);
			Assert.assertTrue(cursor.optBoolean(NON_EXISTANT_COL, true));

			Assert.assertNull(cursor.optBooleanAsWrapperType(NON_EXISTANT_COL));

			cursor.close();
		}
	}

	public void testIntegerFieldParsing(){
		final String NON_EXISTANT_COL = "THIS_COLUMN_DOES_NOT_EXIST";
		final String EXISTANT_COL = "_id";
		final int EXPECTED_VALUE = 14;
		final int FALLBACK = -100;

		for(int i = 0; i < mCombinedList.size(); i++){
			Log.v(TAG, "testIntegerFieldParsing: " + i);
			final EasySqlCursor cursor = (EasySqlCursor) mCombinedList.get(i).execute(mDb.getReadableDatabase());

			Assert.assertEquals(cursor.getInt(EXISTANT_COL), EXPECTED_VALUE);
			Assert.assertEquals((int) cursor.optIntAsWrapperType(EXISTANT_COL), EXPECTED_VALUE);

			Assert.assertEquals(cursor.optInt(NON_EXISTANT_COL), EasySqlCursor.DEFAULT_INT);
			Assert.assertEquals(cursor.optInt(NON_EXISTANT_COL, FALLBACK), FALLBACK);

			Assert.assertNull(cursor.optIntAsWrapperType(NON_EXISTANT_COL));

			cursor.close();
		}
	}

	public void testLongFieldParsing(){
		final String NON_EXISTANT_COL = "THIS_COLUMN_DOES_NOT_EXIST";
		final String EXISTANT_COL = "meaninglessSum";
		final long EXPECTED_VALUE = 2400415l;
		final long FALLBACK = -100l;

		for(int i = 0; i < mCombinedList.size(); i++){
			Log.v(TAG, "testLongFieldParsing: " + i);
			final EasySqlCursor cursor = (EasySqlCursor) mCombinedList.get(i).execute(mDb.getReadableDatabase());

			Assert.assertEquals(cursor.getLong(EXISTANT_COL), EXPECTED_VALUE);
			Assert.assertEquals((long) cursor.optLongAsWrapperType(EXISTANT_COL), EXPECTED_VALUE);

			Assert.assertEquals(cursor.optLong(NON_EXISTANT_COL), EasySqlCursor.DEFAULT_LONG);
			Assert.assertEquals(cursor.optLong(NON_EXISTANT_COL, FALLBACK), FALLBACK);

			Assert.assertNull(cursor.optLongAsWrapperType(NON_EXISTANT_COL));

			cursor.close();
		}
	}

	public void testDoubleFieldParsing(){
		final String NON_EXISTANT_COL = "THIS_COLUMN_DOES_NOT_EXIST";
		final String EXISTANT_COL = "meaninglessDiv";
		final double EXPECTED_VALUE = 720845.345345345;
		final double FALLBACK = -9.99;
		final double DELTA = 0.0100;

		for(int i = 0; i < mCombinedList.size(); i++){
			Log.v(TAG, "testDoubleFieldParsing: " + i);
			final EasySqlCursor cursor = (EasySqlCursor) mCombinedList.get(i).execute(mDb.getReadableDatabase());

			Assert.assertEquals(cursor.getDouble(EXISTANT_COL), EXPECTED_VALUE, DELTA);
			Assert.assertEquals((double) cursor.optDoubleAsWrapperType(EXISTANT_COL), EXPECTED_VALUE, DELTA);

			Assert.assertEquals(cursor.optDouble(NON_EXISTANT_COL), EasySqlCursor.DEFAULT_DOUBLE, DELTA);
			Assert.assertEquals(cursor.optDouble(NON_EXISTANT_COL, FALLBACK), FALLBACK, DELTA);

			Assert.assertNull(cursor.optDoubleAsWrapperType(NON_EXISTANT_COL));

			cursor.close();
		}
	}

	public void testFloatFieldParsing(){
		final String NON_EXISTANT_COL = "THIS_COLUMN_DOES_NOT_EXIST";
		final String EXISTANT_COL = "meaninglessDiv";
		final float EXPECTED_VALUE = 720845.345345345f;
		final float FALLBACK = -9.99f;

		for(int i = 0; i < mCombinedList.size(); i++){
			Log.v(TAG, "testLongFieldParsing: " + i);
			final EasySqlCursor cursor = (EasySqlCursor) mCombinedList.get(i).execute(mDb.getReadableDatabase());

			Assert.assertEquals(cursor.getFloat(EXISTANT_COL), EXPECTED_VALUE);
			Assert.assertEquals((float) cursor.optFloatAsWrapperType(EXISTANT_COL), EXPECTED_VALUE);

			Assert.assertEquals(cursor.optFloat(NON_EXISTANT_COL), EasySqlCursor.DEFAULT_FLOAT);
			Assert.assertEquals(cursor.optFloat(NON_EXISTANT_COL, FALLBACK), FALLBACK);

			Assert.assertNull(cursor.optFloatAsWrapperType(NON_EXISTANT_COL));

			cursor.close();
		}
	}

	public void testSelectModelEquality(){
		final EasySqlQueryModel first = mSelectQueryModelList.get(0);
		for(int i = 1; i < mSelectQueryModelList.size(); i++){
			Log.v(TAG, "testSelectModelEquality: " + i);
			Assert.assertEquals("Comparing first model with item # " + i, first, mSelectQueryModelList.get(i));
		}
	}

	public void testRawModelEquality(){
		final EasySqlQueryModel first = mRawQueryModelList.get(0);
		for(int i = 1; i < mRawQueryModelList.size(); i++){
			Log.v(TAG, "testSelectModelEquality: " + i);
			Assert.assertEquals("Comparing first model with item # " + i, first, mRawQueryModelList.get(i));
		}
	}

	public void testModelResults(){
		EasySqlCursor cursor = (EasySqlCursor) mCombinedList.get(0).execute(mDb.getReadableDatabase());
		final int res = cursor.getCount();
		cursor.close();

		for(int i = 1; i < mCombinedList.size(); i++){
			cursor = (EasySqlCursor) mCombinedList.get(i).execute(mDb.getReadableDatabase());
			final int tmp = cursor.getCount();
			cursor.close();
			Log.v(TAG, "testModelResults: " + i + ": " + tmp);
			Assert.assertEquals("Comparing first model results with item # " + i, res, tmp);
		}
	}

	public void testStringFieldParsing(){
		final String NON_EXISTANT_COL = "THIS_COLUMN_DOES_NOT_EXIST";
		final String EXISTANT_COL = "artist";
		final String EXPECTED_VALUE = "AC/DC";
		final String FALLBACK = "lalalala";

		for(int i = 0; i < mCombinedList.size(); i++){
			Log.v(TAG, "testStringFieldParsing: " + i);
			final EasySqlCursor cursor = (EasySqlCursor) mCombinedList.get(i).execute(mDb.getReadableDatabase());

			Assert.assertEquals(cursor.getString(EXISTANT_COL), EXPECTED_VALUE);

			Assert.assertEquals(cursor.optString(NON_EXISTANT_COL), EasySqlCursor.DEFAULT_STRING);
			Assert.assertEquals(cursor.optString (NON_EXISTANT_COL, FALLBACK), FALLBACK);

			cursor.close();
		}
	}
}
