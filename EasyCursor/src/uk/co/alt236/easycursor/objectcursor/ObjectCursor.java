package uk.co.alt236.easycursor.objectcursor;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import uk.co.alt236.easycursor.EasyCursor;
import uk.co.alt236.easycursor.EasyQueryModel;
import android.database.AbstractCursor;
import android.util.Log;

public class ObjectCursor<T> extends AbstractCursor implements EasyCursor {
	private static final String UTF_8 = "UTF-8";
	private static final String IS = "is";
	private static final String GET = "get";

	public static final String DEFAULT_STRING = null;
	public static final int DEFAULT_INT = 0;
	public static final short DEFAULT_SHORT = 0;
	public static final long DEFAULT_LONG = 0l;
	public static final float DEFAULT_FLOAT = 0.0f;

	public static final double DEFAULT_DOUBLE = 0.0d;
	public static final boolean DEFAULT_BOOLEAN = false;

	private final EasyQueryModel mQueryModel;
	private final List<Method> mMethodList;
	private final List<String> mFieldNameList;
	private final List<T> mObjectList;
	private final Map<String, Integer> mFieldToIndexMap;
	private final Map<String, Method> mFieldToMethodMap;
	private final String TAG = this.getClass().getName();

	public ObjectCursor(Class<T> clazz, List<T> objectList) {
		this(clazz, objectList, null);
	}

	public ObjectCursor(Class<T> clazz, List<T> objectList, EasyQueryModel model) {
		mQueryModel = model;
		mFieldToIndexMap = new HashMap<String, Integer>();
		mFieldToMethodMap = Collections.synchronizedMap(new HashMap<String, Method>());
		mMethodList = new ArrayList<Method>();
		mFieldNameList = new ArrayList<String>();
		mObjectList = objectList;

		populateMethodList(clazz);
	}

	public ObjectCursor(Class<T> clazz, T[] objectArray) {
		this(clazz, new ArrayList<T>(Arrays.asList(objectArray)), null);
	}

	public ObjectCursor(Class<T> clazz, T[] objectArray, EasyQueryModel model) {
		this(clazz, new ArrayList<T>(Arrays.asList(objectArray)), model);
	}

	@Override
	public byte[] getBlob(String fieldName) {
		final Method method = getGetterForFieldOrThrow(fieldName);
		return toByteArray(runGetter(method, getItem(getPosition())));
	}

	@Override
	public boolean getBoolean(String fieldName) {
		final Method method = getGetterForFieldOrThrow(fieldName);
		return toBoolean(runGetter(method, getItem(getPosition())));
	}

	@Override
	public int getColumnIndex(String columnName) {
		if(mFieldToIndexMap.containsKey(columnName)){
			return mFieldToIndexMap.get(columnName).intValue();
		} else {
			return -1;
		}
	}

	@Override
	public int getColumnIndexOrThrow(String columnName) {
		if(mFieldToIndexMap.containsKey(columnName)){
			return mFieldToIndexMap.get(columnName).intValue();
		} else {
			throw new IllegalArgumentException("There is no column named '" + columnName + "'");
		}
	}

	@Override
	public String getColumnName(int columnIndex) {
		return mFieldNameList.get(columnIndex);
	}

	@Override
	public String[] getColumnNames() {
		return mFieldNameList.toArray(new String[mFieldNameList.size()]);
	}

	@Override
	public int getCount() {
		return mObjectList.size();
	}

	@Override
	public double getDouble(int column) {
		validateFieldNumberMapping(column);
		return getDouble(mMethodList.get(column));
	}

	private double getDouble(Method method){
		return toDouble(runGetter(method, getItem(getPosition())));
	}

	@Override
	public double getDouble(String fieldName) {
		return getDouble(getGetterForFieldOrThrow(fieldName));
	}

	@Override
	public float getFloat(int column) {
		validateFieldNumberMapping(column);
		return getFloat(mMethodList.get(column));
	}

	private float getFloat(Method method){
		return toFloat(runGetter(method, getItem(getPosition())));
	}

	@Override
	public float getFloat(String fieldName) {
		return getFloat(getGetterForFieldOrThrow(fieldName));
	}

	private synchronized Method getGetterForField(String field){
		if(mFieldToMethodMap.containsKey(field)){
			return mFieldToMethodMap.get(field);
		} else {
			final String booleanField = IS + field.toLowerCase(Locale.US);
			final String otherField = GET + field.toLowerCase(Locale.US);

			Method methodResult = null;
			for (final Method method : mMethodList) {
				if(method.getName().toLowerCase(Locale.US).equals(booleanField)){
					methodResult = method;
					break;
				} else if (method.getName().toLowerCase(Locale.US).equals(otherField)){
					methodResult = method;
					break;
				}
			}

			mFieldToMethodMap.put(field, methodResult);
			return methodResult;
		}
	}

	private Method getGetterForFieldOrThrow(String fieldName){
		final Method method = getGetterForField(fieldName);
		if(method == null){
			throw new IllegalArgumentException("Could not find getter for field '" + fieldName + "'");
		} else {
			return method;
		}
	}

	@Override
	public int getInt(int column) {
		validateFieldNumberMapping(column);
		return getInt(mMethodList.get(column));
	}

	private int getInt(Method method){
		return  toInt(runGetter(method, getItem(getPosition())));
	}

	@Override
	public int getInt(String fieldName) {
		return getInt(getGetterForFieldOrThrow(fieldName));
	}

	public T getItem(int position){
		return mObjectList.get(position);
	}

	@Override
	public long getLong(int column) {
		validateFieldNumberMapping(column);
		return getLong(mMethodList.get(column));
	}

	private long getLong(Method method){
		return toLong(runGetter(method, getItem(getPosition())));
	}

	@Override
	public long getLong(String fieldName) {
		return getLong(getGetterForFieldOrThrow(fieldName));
	}

	public List<Method> getMethods(){
		return mMethodList;
	}

	public Object getObject(int column) {
		validateFieldNumberMapping(column);
		return getObject(mMethodList.get(column));
	}

	private Object getObject(Method method){
		return runGetter(method, getItem(getPosition()));
	}

	public Object getObject(String fieldName){
		return getObject(getGetterForFieldOrThrow(fieldName));
	}

	@Override
	public EasyQueryModel getQueryModel() {
		return mQueryModel;
	}

	@Override
	public short getShort(int column) {
		validateFieldNumberMapping(column);
		return getShort(mMethodList.get(column));
	}

	public short getShort(Method method) {
		return  toShort(runGetter(method, getItem(getPosition())));
	}

	public short getShort(String fieldName) {
		return getShort(getGetterForFieldOrThrow(fieldName));
	}

	@Override
	public String getString(int column) {
		validateFieldNumberMapping(column);
		return getString(mMethodList.get(column));
	}

	private String getString(Method method) {
		return toString(runGetter(method, getItem(getPosition())));
	}

	@Override
	public String getString(String fieldName) {
		return getString(getGetterForFieldOrThrow(fieldName));
	}

	@Override
	public boolean isNull(int column) {
		validateFieldNumberMapping(column);
		return isNull(mMethodList.get(column));
	}

	private boolean isNull(Method method) {
		return (runGetter(method, getItem(getPosition())) == null);
	}

	@Override
	public boolean isNull(String fieldName) {
		return isNull(getGetterForFieldOrThrow(fieldName));
	}

	@Override
	public boolean optBoolean(String fieldName) {
		return optBoolean(fieldName, DEFAULT_BOOLEAN);
	}

	@Override
	public boolean optBoolean(String fieldName, boolean fallback) {
		final Method method = getGetterForField(fieldName);

		if(method == null){
			return fallback;
		} else {
			return toBoolean(runGetter(method, getItem(getPosition())));
		}
	}

	@Override
	public Boolean optBooleanAsWrapperType(String fieldName) {
		final Method method = getGetterForField(fieldName);

		if(method == null){
			return null;
		} else {
			return toBoolean(runGetter(method, getItem(getPosition())));
		}
	}

	@Override
	public double optDouble(String fieldName) {
		return optDouble(fieldName, DEFAULT_DOUBLE);
	}

	@Override
	public double optDouble(String fieldName, double fallback) {
		final Method method = getGetterForField(fieldName);

		if(method == null){
			return fallback;
		} else {
			return toDouble(runGetter(method, getItem(getPosition())));
		}
	}

	@Override
	public Double optDoubleAsWrapperType(String fieldName) {
		final Method method = getGetterForField(fieldName);

		if(method == null){
			return null;
		} else {
			return (Double) runGetter(method, getItem(getPosition()));
		}
	}

	@Override
	public float optFloat(String fieldName) {
		return optFloat(fieldName, DEFAULT_FLOAT);
	}


	@Override
	public float optFloat(String fieldName, float fallback) {
		final Method method = getGetterForField(fieldName);

		if(method == null){
			return fallback;
		} else {
			return toFloat(runGetter(method, getItem(getPosition())));
		}
	}

	@Override
	public Float optFloatAsWrapperType(String fieldName) {
		final Method method = getGetterForField(fieldName);

		if(method == null){
			return null;
		} else {
			return toFloat(runGetter(method, getItem(getPosition())));
		}
	}

	@Override
	public int optInt(String fieldName) {
		return optInt(fieldName, DEFAULT_INT);
	}

	@Override
	public int optInt(String fieldName, int fallback) {
		final Method method = getGetterForField(fieldName);

		if(method == null){
			return fallback;
		} else {
			return toInt(runGetter(method, getItem(getPosition())));
		}
	}

	@Override
	public Integer optIntAsWrapperType(String fieldName) {
		final Method method = getGetterForField(fieldName);

		if(method == null){
			return null;
		} else {
			return toInt(runGetter(method, getItem(getPosition())));
		}
	}

	@Override
	public long optLong(String fieldName) {
		return optLong(fieldName, DEFAULT_LONG);
	}

	@Override
	public long optLong(String fieldName, long fallback) {
		final Method method = getGetterForField(fieldName);

		if(method == null){
			return fallback;
		} else {
			return toLong(runGetter(method, getItem(getPosition())));
		}
	}

	@Override
	public Long optLongAsWrapperType(String fieldName) {
		final Method method = getGetterForField(fieldName);

		if(method == null){
			return null;
		} else {
			return toLong(runGetter(method, getItem(getPosition())));
		}
	}

	@Override
	public String optString(String fieldName) {
		return optString(fieldName, DEFAULT_STRING);
	}

	@Override
	public String optString(String fieldName, String fallback) {
		final Method method = getGetterForField(fieldName);

		if(method == null){
			return fallback;
		} else {
			return toString(runGetter(method, getItem(getPosition())));
		}
	}

	private void populateMethodList(Class<T> clazz){
		Method candidate;
		String canditateCleanName;

		int count = 0;
		for(final Method method : clazz.getMethods()){
			candidate = null;
			canditateCleanName = null;

			if(Modifier.isPublic(method.getModifiers())){

				// Just in case there is a get()/is() function
				if(method.getName().length() > 3){
					if(method.getParameterTypes().length == 0){
						if(!method.getReturnType().equals(Void.TYPE)){

							if(method.getName().startsWith(GET)){
								candidate = method;
								canditateCleanName =
										method.getName().substring(GET.length()).toLowerCase(Locale.US);

							} else if (method.getName().startsWith(IS)){
								candidate = method;
								canditateCleanName =
										method.getName().substring(IS.length()).toLowerCase(Locale.US);
							}

							if(candidate != null){
								mMethodList.add(candidate);
								mFieldToIndexMap.put(canditateCleanName, count);
								mFieldNameList.add(canditateCleanName);
								count++;
							}
						}
					}
				}
			}
		}
	}

	private Object runGetter(Method method, T object) {
		if(method != null){
			try{
				return method.invoke(object);
			} catch (IllegalAccessException e) {
				Log.w(TAG, "Could not determine method: " + method.getName());
			} catch (InvocationTargetException e) {
				Log.w(TAG, "Could not determine method: " + method.getName());
			}
		}

		return null;
	}

	private void validateFieldNumberMapping(final int fieldNumber) {
		if (mMethodList == null) {
			throw new IllegalArgumentException("There is no valid field to id mapping and was asked to get a field by id");
		}
	}

	private static byte[] toByteArray(Object obj){
		if(obj == null){return null;}

		if(obj instanceof byte[]){
			return (byte[]) obj;
		} else if(obj instanceof String){
			return ((String) obj).getBytes();
		} else if (obj instanceof Integer){
			return ByteBuffer.allocate(4).putInt((Integer) obj).array();
		} else if (obj instanceof Long){
			return ByteBuffer.allocate(8).putLong((Long) obj).array();
		} else if (obj instanceof Float){
			return ByteBuffer.allocate(4).putFloat((Float) obj).array();
		} else if (obj instanceof Double){
			return ByteBuffer.allocate(8).putDouble((Double) obj).array();
		} else if (obj instanceof Short){
			return ByteBuffer.allocate(2).putShort((Short) obj).array();
		} else {
			throw new ClassCastException("Unable to convert " + obj.getClass().getName() + " to byte[]");
		}
	}

	private static double toDouble(Object obj){
		if(obj instanceof Number){
			return ((Number) obj).doubleValue();
		} else if(obj instanceof String){
			return Double.valueOf(String.valueOf(obj)).doubleValue();
		} else {
			throw new ClassCastException("Unable to convert " + obj.getClass().getName() + " to double");
		}
	}

	private static float toFloat(Object obj){
		if(obj instanceof Number){
			return ((Number) obj).floatValue();
		} else if(obj instanceof String){
			return Float.valueOf(String.valueOf(obj)).floatValue();
		} else {
			throw new ClassCastException("Unable to convert " + obj.getClass().getName() + " to float");
		}
	}

	private static int toInt(Object obj){
		if(obj instanceof Number){
			return ((Number) obj).intValue();
		} else if(obj instanceof String){
			return Integer.valueOf(String.valueOf(obj)).intValue();
		} else {
			throw new ClassCastException("Unable to convert " + obj.getClass().getName() + " to int");
		}
	}

	private static long toLong(Object obj){
		if(obj instanceof Number){
			return ((Number) obj).longValue();
		} else if(obj instanceof String){
			return Short.valueOf(String.valueOf(obj)).longValue();
		} else {
			throw new ClassCastException("Unable to convert " + obj.getClass().getName() + " to long");
		}
	}


	private static boolean toBoolean(Object obj){
		if(obj instanceof Boolean){
			return ((Boolean) obj).booleanValue();
		} else if(obj instanceof String){
			return Boolean.valueOf(String.valueOf(obj)).booleanValue();
		} else {
			throw new ClassCastException("Unable to convert " + obj.getClass().getName() + " to boolean");
		}
	}

	private static short toShort(Object obj){
		if(obj instanceof Number){
			return ((Number) obj).shortValue();
		} else if(obj instanceof String){
			return Short.valueOf(String.valueOf(obj)).shortValue();
		} else {
			throw new ClassCastException("Unable to convert " + obj.getClass().getName() + " to short");
		}
	}

	private static String toString(Object obj){
		if(obj instanceof byte[]){
			try {
				return new String((byte[]) obj, UTF_8);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return new String((byte[]) obj);
			}
		} else {
			return String.valueOf(obj);
		}
	}
}
