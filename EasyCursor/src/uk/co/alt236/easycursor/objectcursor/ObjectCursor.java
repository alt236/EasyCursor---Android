package uk.co.alt236.easycursor.objectcursor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import uk.co.alt236.easycursor.EasyCursor;
import uk.co.alt236.easycursor.EasyQueryModel;
import android.database.AbstractCursor;
import android.util.Log;

public class ObjectCursor<T> extends AbstractCursor implements EasyCursor {
	public static final String DEFAULT_STRING = null;
	public static final int DEFAULT_INT = 0;
	public static final long DEFAULT_LONG = 0l;
	public static final float DEFAULT_FLOAT = 0.0f;

	public static final double DEFAULT_DOUBLE = 0.0d;
	public static final boolean DEFAULT_BOOLEAN = false;

	private static final String IS = "is";
	private static final String GET = "get";

	private final String TAG = this.getClass().getName();

	final Map<Integer, String> mNumberToFieldMap;
	final List<T> mObjectList;
	final Set<Method> mMethodSet;
	final Map<String, Method> mFieldToMethodMap;

	public ObjectCursor(Class<T> clazz, List<T> objectList, Map<Integer, String> numberToFieldMap) {
		mNumberToFieldMap = numberToFieldMap;
		mObjectList = objectList;
		mMethodSet = populateMethodSet(clazz);
		mFieldToMethodMap = Collections.synchronizedMap(new HashMap<String, Method>());
	}

	public ObjectCursor(Class<T> clazz, T[] objectArray, Map<Integer, String> numberToFieldMap) {
		this(clazz, new ArrayList<T>(Arrays.asList(objectArray)), numberToFieldMap);
	}

	@Override
	public byte[] getBlob(String fieldName) {
		final Method method = getGetterForFieldOrThrow(fieldName);
		return ((byte[]) runGetter(method, getItem(getPosition())));
	}

	@Override
	public boolean getBoolean(String fieldName) {
		final Method method = getGetterForFieldOrThrow(fieldName);
		return ((Boolean) runGetter(method, getItem(getPosition()))).booleanValue();
	}

	@Override
	public String[] getColumnNames() {
		return new String[0];
	}

	@Override
	public int getCount() {
		return mObjectList.size();
	}

	@Override
	public double getDouble(int column) {
		validateFieldNumberMapping(column);
		return getDouble(mNumberToFieldMap.get(column));
	}

	@Override
	public double getDouble(String fieldName) {
		final Method method = getGetterForFieldOrThrow(fieldName);
		return ((Double) runGetter(method, getItem(getPosition()))).doubleValue();
	}

	@Override
	public float getFloat(int column) {
		validateFieldNumberMapping(column);
		return getFloat(mNumberToFieldMap.get(column));
	}

	@Override
	public float getFloat(String fieldName) {
		final Method method = getGetterForFieldOrThrow(fieldName);
		return ((Float) runGetter(method, getItem(getPosition()))).floatValue();
	}

	private synchronized Method getGetterForField(String field){
		if(mFieldToMethodMap.containsKey(field)){
			return mFieldToMethodMap.get(field);
		} else {
			final String booleanField = IS + field.toLowerCase(Locale.US);
			final String otherField = GET + field.toLowerCase(Locale.US);

			Method methodResult = null;
			for (final Method method : mMethodSet) {
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
		return getInt(mNumberToFieldMap.get(column));
	}

	@Override
	public int getInt(String fieldName) {
		final Method method = getGetterForFieldOrThrow(fieldName);
		return ((Integer) runGetter(method, getItem(getPosition()))).intValue();
	}

	public T getItem(int position){
		return mObjectList.get(position);
	}

	@Override
	public long getLong(int column) {
		validateFieldNumberMapping(column);
		return getLong(mNumberToFieldMap.get(column));
	}

	@Override
	public long getLong(String fieldName) {
		final Method method = getGetterForFieldOrThrow(fieldName);
		return ((Long) runGetter(method, getItem(getPosition()))).longValue();
	}

	public Set<Method> getMethods(){
		return mMethodSet;
	}

	@Override
	public EasyQueryModel getQueryModel() {
		return null;
	}

	@Override
	public short getShort(int column) {
		validateFieldNumberMapping(column);
		return getShort(mNumberToFieldMap.get(column));
	}

	public short getShort(String fieldName) {
		final Method method = getGetterForFieldOrThrow(fieldName);
		return ((Short) runGetter(method, getItem(getPosition()))).shortValue();
	}

	@Override
	public String getString(int column) {
		validateFieldNumberMapping(column);
		return getString(mNumberToFieldMap.get(column));
	}

	@Override
	public String getString(String fieldName) {
		final Method method = getGetterForFieldOrThrow(fieldName);
		return String.valueOf(runGetter(method, getItem(getPosition())));
	}

	private boolean invokeForBoolean(Method method, boolean fallback) {
		final Object res = runGetter(method, getItem(getPosition()));
		if(res == null){
			return fallback;
		} else {
			return ((Boolean) res).booleanValue();
		}
	}

	private double invokeForDouble(Method method, double fallback) {
		final Object res = runGetter(method, getItem(getPosition()));
		if(res == null){
			return fallback;
		} else {
			return ((Double) res).doubleValue();
		}
	}

	private float invokeForFloat(Method method, float fallback) {
		final Object res = runGetter(method, getItem(getPosition()));
		if(res == null){
			return fallback;
		} else {
			return ((Float) res).floatValue();
		}
	}

	private int invokeForInt(Method method, int fallback) {
		final Object res = runGetter(method, getItem(getPosition()));
		if(res == null){
			return fallback;
		} else {
			return ((Integer) res).intValue();
		}
	}

	private long invokeForLong(Method method, long fallback) {
		final Object res = runGetter(method, getItem(getPosition()));
		if(res == null){
			return fallback;
		} else {
			return ((Long) res).longValue();
		}
	}

	private String invokeForString(Method method, String fallback) {
		final Object res = runGetter(method, getItem(getPosition()));
		if(res == null){
			return fallback;
		} else {
			return String.valueOf(res);
		}
	}

	@Override
	public boolean isNull(int column) {
		validateFieldNumberMapping(column);
		return isNull(mNumberToFieldMap.get(column));
	}

	@Override
	public boolean isNull(String fieldName) {
		final Method method = getGetterForFieldOrThrow(fieldName);
		return (runGetter(method, getItem(getPosition())) == null);
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
			return invokeForBoolean(method, fallback);
		}
	}

	@Override
	public Boolean optBooleanAsWrapperType(String fieldName) {
		final Method method = getGetterForField(fieldName);

		if(method == null){
			return null;
		} else {
			return (Boolean) runGetter(method, getItem(getPosition()));
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
			return invokeForDouble(method, fallback);
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
			return invokeForFloat(method, fallback);
		}
	}

	@Override
	public Float optFloatAsWrapperType(String fieldName) {
		final Method method = getGetterForField(fieldName);

		if(method == null){
			return null;
		} else {
			return (Float) runGetter(method, getItem(getPosition()));
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
			return invokeForInt(method, fallback);
		}
	}

	@Override
	public Integer optIntAsWrapperType(String fieldName) {
		final Method method = getGetterForField(fieldName);

		if(method == null){
			return null;
		} else {
			return (Integer) runGetter(method, getItem(getPosition()));
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
			return invokeForLong(method, fallback);
		}
	}

	@Override
	public Long optLongAsWrapperType(String fieldName) {
		final Method method = getGetterForField(fieldName);

		if(method == null){
			return null;
		} else {
			return (Long) runGetter(method, getItem(getPosition()));
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
			return invokeForString(method, fallback);
		}
	}

	private Set<Method> populateMethodSet(Class<T> clazz){
		final Set<Method> methodResult = new HashSet<Method>();

		for(final Method method : clazz.getMethods()){
			Method candidate = null;

			if(method.getName().startsWith(GET)){
				if(!method.getReturnType().equals(Void.TYPE)){
					candidate = method;
				}
			} else if (method.getName().startsWith(IS)){
				if(!method.getReturnType().equals(Void.TYPE)){
					candidate = method;
				}
			}

			if(candidate != null){
				// we can only use getter with no params
				if(candidate.getParameterTypes().length == 0){
					methodResult.add(candidate);
				}
			}
		}

		return methodResult;
	}

	public Object runGetter(Method method, T object) {
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
		if (mNumberToFieldMap == null) {
			throw new IllegalArgumentException("There is no valid field to id mapping and was asked to get a field by id");
		}

		if (!mNumberToFieldMap.containsKey(fieldNumber)) {
			throw new IllegalArgumentException("There is no mapping for field number: " + fieldNumber);
		}
	}
}
