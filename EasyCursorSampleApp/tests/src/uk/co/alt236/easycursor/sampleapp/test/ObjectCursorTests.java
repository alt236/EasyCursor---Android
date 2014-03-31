package uk.co.alt236.easycursor.sampleapp.test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;
import uk.co.alt236.easycursor.EasyCursor;
import uk.co.alt236.easycursor.objectcursor.EasyObjectCursor;
import uk.co.alt236.easycursor.sampleapp.container.TrackInfo;
import uk.co.alt236.easycursor.sampleapp.containers.TestObject;
import uk.co.alt236.easycursor.sampleapp.database.DbSingleton;
import uk.co.alt236.easycursor.sampleapp.database.ExampleDatabase;
import android.util.Log;

public class ObjectCursorTests extends CommonTestCases {
	private ExampleDatabase mDb;
	private List<TestObject> mTestObjects;
	private EasyObjectCursor<TrackInfo> mObjectCursor;
	private final String TAG = getClass().getName();
	private final static long SEED = 1000;

	@SuppressWarnings("unchecked")
	@Override
	protected void setUp() throws Exception {
		mDb = DbSingleton.getInstance(getContext());
		mObjectCursor = (EasyObjectCursor<TrackInfo>) mDb.doObjectCursorQuery();

		mTestObjects = new ArrayList<TestObject>();

		for(int i = 0; i< 10; i++){
			mTestObjects.add(new TestObject(SEED + i));
		}

		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		mObjectCursor.close();
		super.tearDown();
	}

	public void testBooleanFieldParsing(){
		final EasyObjectCursor<TestObject> cursor = new EasyObjectCursor<TestObject>(TestObject.class, mTestObjects);

		cursor.moveToFirst();
		String fieldName = "bool";
		while(!cursor.isAfterLast()){
			final boolean data = cursor.getBoolean(fieldName);

			logFieldData(TAG, fieldName, cursor.getPosition(), String.valueOf(data));

			Assert.assertNotNull(data);
			cursor.moveToNext();
		}

		cursor.close();
	}


	public void testByteArrayFieldParsing(){
		final EasyCursor cursor = new EasyObjectCursor<TestObject>(TestObject.class, mTestObjects);
		final String[] cols = cursor.getColumnNames();
		final Set<String> convertables = new HashSet<String>();
		convertables.add("byte");
		convertables.add("double");
		convertables.add("float");
		convertables.add("int");
		convertables.add("long");
		convertables.add("null");
		convertables.add("short");
		convertables.add("string");

		cursor.moveToFirst();

		while(!cursor.isAfterLast()){
			for(final String col : cols){
				Log.v(TAG, "Trying field: " + col);
				if(convertables.contains(col)){
					final byte[] data = cursor.getBlob(col);
					logFieldData(TAG, col, cursor.getPosition(), Arrays.toString(data));
				} else {
					try {
						cursor.getBlob(col);
						fail( "This should have thrown an excepption! Item: " + col + "/" + cursor.getPosition() );
					} catch (ClassCastException expectedException) {}
				}
			}
			cursor.moveToNext();
		}

		cursor.close();
	}

	public void testDoubleFieldParsing(){
		testDoubleFieldParsing(mObjectCursor);
	}

	public void testFieldIndexes(){
		final EasyCursor cursor = new EasyObjectCursor<TestObject>(TestObject.class, mTestObjects);
		cursor.moveToFirst();
		final String[] cols = cursor.getColumnNames();

		int index = 0;
		for(final String col : cols){
			final int cursorFieldIndex = cursor.getColumnIndex(col);
			Assert.assertEquals(index, cursorFieldIndex);
			final String cursorFieldName = cursor.getColumnName(cursorFieldIndex);
			Assert.assertEquals(col, cursorFieldName);
			index++;
		}

		cursor.close();
	}

	public void testFloatFieldParsing(){
		testFloatFieldParsing(mObjectCursor);
	}

	public void testIntegerFieldParsing(){
		testIntegerFieldParsing(mObjectCursor);
	}


	public void testLongFieldParsing(){
		testLongFieldParsing(mObjectCursor);
	}

	public void testMethodSet(){
		final EasyObjectCursor<TestObject> cursor = new EasyObjectCursor<TestObject>(TestObject.class, mTestObjects);
		final List<Method> methods = cursor.getMethods();

		for(final Method method : methods){
			Log.v(TAG, "Has method: " + method.getName());
			Assert.assertEquals(0, method.getParameterTypes().length);
		}

		final String[] cols = cursor.getColumnNames();
		for(final String col : cols){
			Log.v(TAG, "Column name: " + col);
		}

		cursor.close();
	}


	public void testNullCheck(){
		final EasyObjectCursor<TestObject> cursor = new EasyObjectCursor<TestObject>(TestObject.class, mTestObjects);
		final String[] cols = cursor.getColumnNames();

		cursor.moveToFirst();

		while(!cursor.isAfterLast()){
			for(final String col : cols){
				Log.v(TAG, "Trying field: " + col);
				if("null".equals(col)){
					Assert.assertTrue(cursor.isNull(col));
				} else {
					Assert.assertFalse(cursor.isNull(col));
				}
			}
			cursor.moveToNext();
		}

		cursor.close();
	}

	public void testStringFieldParsing(){
		final EasyCursor cursor = new EasyObjectCursor<TestObject>(TestObject.class, mTestObjects);
		final String[] cols = cursor.getColumnNames();

		cursor.moveToFirst();
		while(!cursor.isAfterLast()){

			for(final String col : cols){
				Log.v(TAG, "Trying field: " + col);
				final String data = cursor.getString(col);

				logFieldData(TAG, col, cursor.getPosition(), data);

				Assert.assertNotNull(data);
			}
			cursor.moveToNext();
		}

		cursor.close();

		testStringFieldParsing(mObjectCursor);
	}

	private static void logFieldData(String tag, String fieldName, int position, String data){
		Log.v(tag, "Data for field: '" + fieldName + "', position: " + position + " is '" + data + "'");
	}

}
