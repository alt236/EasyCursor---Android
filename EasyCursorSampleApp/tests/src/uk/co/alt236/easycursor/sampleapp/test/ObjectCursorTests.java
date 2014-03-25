package uk.co.alt236.easycursor.sampleapp.test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;

import junit.framework.Assert;
import uk.co.alt236.easycursor.objectcursor.ObjectCursor;
import uk.co.alt236.easycursor.sampleapp.database.QueryConstants;
import android.util.Log;

public class ObjectCursorTests extends android.test.AndroidTestCase {
	private final String TAG = getClass().getName();

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}



	public void testByteArrayDataRetrieval(){
		final ObjectCursor<String> cursor = new ObjectCursor<String>(String.class, QueryConstants.DEFAULT_SELECT, null);


		cursor.moveToFirst();
		String fieldName = "bytes";
		while(!cursor.isAfterLast()){
			final byte[] data = cursor.getBlob(fieldName);
			Log.v(TAG, "Data for field: " + fieldName + ", position: " + cursor.getPosition() + " is " + Arrays.toString(data));

			Assert.assertNotNull(data);
			cursor.moveToNext();
		}

		cursor.moveToFirst();
		fieldName = "empty";
		while(!cursor.isAfterLast()){
			final byte[] data = cursor.getBlob(fieldName);
			Log.v(TAG, "Data for field: " + fieldName + ", position: " + cursor.getPosition() + " is " + Arrays.toString(data));

			Assert.assertNotNull(data);
			cursor.moveToNext();
		}

		cursor.close();
	}

	public void testMethodSet(){
		final ObjectCursor<String> cursor = new ObjectCursor<String>(String.class, QueryConstants.DEFAULT_SELECT, null);
		final Set<Method> methods = cursor.getMethods();

		for(final Method method : methods){
			Log.v(TAG, "Has method: " + method);

			// The method should have no parameters
			Assert.assertEquals(0, method.getParameterTypes().length);
		}

		cursor.close();
	}

}
