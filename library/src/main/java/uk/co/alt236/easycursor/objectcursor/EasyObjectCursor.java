package uk.co.alt236.easycursor.objectcursor;

import android.database.AbstractCursor;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import uk.co.alt236.easycursor.EasyCursor;
import uk.co.alt236.easycursor.EasyQueryModel;
import uk.co.alt236.easycursor.util.ObjectConverters;

public class EasyObjectCursor<T> extends AbstractCursor implements EasyCursor {
    public static final String DEFAULT_STRING = null;
    public static final boolean DEFAULT_BOOLEAN = false;
    public static final double DEFAULT_DOUBLE = 0.0d;
    public static final float DEFAULT_FLOAT = 0.0f;
    public static final int DEFAULT_INT = 0;
    public static final long DEFAULT_LONG = 0l;
    public static final short DEFAULT_SHORT = 0;
    private static final String _ID = "_id";
    private static final String IS = "is";
    private static final String GET = "get";
    private final EasyQueryModel mQueryModel;
    private final List<Method> mMethodList;
    private final List<String> mFieldNameList;
    private final List<T> mObjectList;
    private final Map<String, Integer> mFieldToIndexMap;
    private final Map<String, Method> mFieldToMethodMap;
    private final String TAG = this.getClass().getName();
    private final String m_IdAlias;

    private boolean mDebugEnabled;

    public EasyObjectCursor(Class<T> clazz, List<T> objectList, String _idAlias) {
        this(clazz, objectList, _idAlias, null);
    }

    public EasyObjectCursor(Class<T> clazz, List<T> objectList, String _idAlias, EasyQueryModel model) {
        mQueryModel = model;
        mFieldToIndexMap = new HashMap<String, Integer>();
        mFieldToMethodMap = Collections.synchronizedMap(new HashMap<String, Method>());
        mMethodList = new ArrayList<Method>();
        mFieldNameList = new ArrayList<String>();
        mObjectList = objectList;
        m_IdAlias = _idAlias;

        populateMethodList(clazz);
    }

    public EasyObjectCursor(Class<T> clazz, T[] objectArray, String _idAlias) {
        this(clazz, new ArrayList<T>(Arrays.asList(objectArray)), _idAlias, null);
    }

    public EasyObjectCursor(Class<T> clazz, T[] objectArray, String _idAlias, EasyQueryModel model) {
        this(clazz, new ArrayList<T>(Arrays.asList(objectArray)), _idAlias, model);
    }

    private String applyAlias(String columnName) {
        if (_ID.equals(columnName)) {
            if (m_IdAlias != null) {
                return m_IdAlias;
            }
        }

        return columnName;
    }

    @Override
    public byte[] getBlob(String fieldName) {
        final Method method = getGetterForFieldOrThrow(applyAlias(fieldName));
        return ObjectConverters.toByteArray(runGetter(method, getItem(getPosition())));
    }

    @Override
    public boolean getBoolean(String fieldName) {
        final Method method = getGetterForFieldOrThrow(applyAlias(fieldName));
        return ObjectConverters.toBoolean(runGetter(method, getItem(getPosition())));
    }

    @Override
    public int getColumnIndex(String columnName) {
        final String column = applyAlias(columnName);
        if (mFieldToIndexMap.containsKey(column)) {
            return mFieldToIndexMap.get(column).intValue();
        } else {
            return -1;
        }
    }

    @Override
    public int getColumnIndexOrThrow(String columnName) {
        final String column = applyAlias(columnName);
        if (mFieldToIndexMap.containsKey(column)) {
            return mFieldToIndexMap.get(column).intValue();
        } else {
            throw new IllegalArgumentException("There is no column named '" + column + "'");
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
        return getDouble(mMethodList.get(column));
    }

    private double getDouble(Method method) {
        return ObjectConverters.toDouble(runGetter(method, getItem(getPosition())));
    }

    @Override
    public double getDouble(String fieldName) {
        return getDouble(getGetterForFieldOrThrow(applyAlias(fieldName)));
    }

    @Override
    public float getFloat(int column) {
        return getFloat(mMethodList.get(column));
    }

    private float getFloat(Method method) {
        return ObjectConverters.toFloat(runGetter(method, getItem(getPosition())));
    }

    @Override
    public float getFloat(String fieldName) {
        return getFloat(getGetterForFieldOrThrow(applyAlias(fieldName)));
    }

    private synchronized Method getGetterForField(String field) {
        if (mFieldToMethodMap.containsKey(field)) {
            return mFieldToMethodMap.get(field);
        } else {
            final String booleanField = IS + field.toLowerCase(Locale.US);
            final String otherField = GET + field.toLowerCase(Locale.US);

            Method methodResult = null;
            for (final Method method : mMethodList) {
                if (method.getName().toLowerCase(Locale.US).equals(booleanField)) {
                    methodResult = method;
                    break;
                } else if (method.getName().toLowerCase(Locale.US).equals(otherField)) {
                    methodResult = method;
                    break;
                }
            }

            mFieldToMethodMap.put(field, methodResult);
            return methodResult;
        }
    }

    private Method getGetterForFieldOrThrow(String fieldName) {
        final Method method = getGetterForField(applyAlias(fieldName));
        if (method == null) {
            throw new IllegalArgumentException("Could not find getter for field '" + applyAlias(fieldName) + "'");
        } else {
            return method;
        }
    }

    @Override
    public int getInt(int column) {
        return getInt(mMethodList.get(column));
    }

    private int getInt(Method method) {
        return ObjectConverters.toInt(runGetter(method, getItem(getPosition())));
    }

    @Override
    public int getInt(String fieldName) {
        return getInt(getGetterForFieldOrThrow(applyAlias(fieldName)));
    }

    public T getItem(int position) {
        return mObjectList.get(position);
    }

    @Override
    public long getLong(int column) {
        return getLong(mMethodList.get(column));
    }

    private long getLong(Method method) {
        return ObjectConverters.toLong(runGetter(method, getItem(getPosition())));
    }

    @Override
    public long getLong(String fieldName) {
        return getLong(getGetterForFieldOrThrow(applyAlias(fieldName)));
    }

    public List<Method> getMethods() {
        return mMethodList;
    }

    public Object getObject(int column) {
        return getObject(mMethodList.get(column));
    }

    private Object getObject(Method method) {
        return runGetter(method, getItem(getPosition()));
    }

    public Object getObject(String fieldName) {
        return getObject(getGetterForFieldOrThrow(applyAlias(fieldName)));
    }

    @Override
    public EasyQueryModel getQueryModel() {
        return mQueryModel;
    }

    @Override
    public short getShort(int column) {
        return getShort(mMethodList.get(column));
    }

    public short getShort(Method method) {
        return ObjectConverters.toShort(runGetter(method, getItem(getPosition())));
    }

    public short getShort(String fieldName) {
        return getShort(getGetterForFieldOrThrow(applyAlias(fieldName)));
    }

    @Override
    public String getString(int column) {
        return getString(mMethodList.get(column));
    }

    private String getString(Method method) {
        return ObjectConverters.toString(runGetter(method, getItem(getPosition())));
    }

    @Override
    public String getString(String fieldName) {
        return getString(getGetterForFieldOrThrow(applyAlias(fieldName)));
    }

    public boolean isDebugEnabled() {
        return mDebugEnabled;
    }

    public void setDebugEnabled(boolean enabled) {
        mDebugEnabled = enabled;
    }

    @Override
    public boolean isNull(int column) {
        return isNull(mMethodList.get(column));
    }

    private boolean isNull(Method method) {
        return (runGetter(method, getItem(getPosition())) == null);
    }

    @Override
    public boolean isNull(String fieldName) {
        return isNull(getGetterForFieldOrThrow(applyAlias(fieldName)));
    }

    @Override
    public boolean optBoolean(String fieldName) {
        return optBoolean(fieldName, DEFAULT_BOOLEAN);
    }

    @Override
    public boolean optBoolean(String fieldName, boolean fallback) {
        final Method method = getGetterForField(applyAlias(fieldName));

        if (method == null) {
            return fallback;
        } else {
            try {
                if (mDebugEnabled) {
                    Log.w(TAG, "optBoolean('" + fieldName + "') Getter was null.");
                }
                return ObjectConverters.toBoolean(runGetter(method, getItem(getPosition())));
            } catch (Exception e) {
                if (mDebugEnabled) {
                    Log.w(TAG, "optBoolean('" + fieldName + "') Caught Exception  at " + getPosition() + "/" + getCount(), e);
                    e.printStackTrace();
                }
                return fallback;
            }
        }
    }

    @Override
    public Boolean optBooleanAsWrapperType(String fieldName) {
        final Method method = getGetterForField(applyAlias(fieldName));

        if (method == null) {
            if (mDebugEnabled) {
                Log.w(TAG, "optBooleanAsWrapperType('" + fieldName + "') Getter was null.");
            }
            return null;
        } else {
            try {
                return ObjectConverters.toBoolean(runGetter(method, getItem(getPosition())));
            } catch (Exception e) {
                if (mDebugEnabled) {
                    Log.w(TAG, "optBooleanAsWrapperType('" + fieldName + "') Caught Exception  at " + getPosition() + "/" + getCount(), e);
                    e.printStackTrace();
                }
                return null;
            }
        }
    }

    @Override
    public double optDouble(String fieldName) {
        return optDouble(fieldName, DEFAULT_DOUBLE);
    }

    @Override
    public double optDouble(String fieldName, double fallback) {
        final Method method = getGetterForField(applyAlias(fieldName));

        if (method == null) {
            if (mDebugEnabled) {
                Log.w(TAG, "optDouble('" + fieldName + "') Getter was null.");
            }
            return fallback;
        } else {
            try {
                return ObjectConverters.toDouble(runGetter(method, getItem(getPosition())));
            } catch (Exception e) {
                if (mDebugEnabled) {
                    Log.w(TAG, "optDouble('" + fieldName + "') Caught Exception  at " + getPosition() + "/" + getCount(), e);
                    e.printStackTrace();
                }
                return fallback;
            }
        }
    }

    @Override
    public Double optDoubleAsWrapperType(String fieldName) {
        final Method method = getGetterForField(applyAlias(fieldName));

        if (method == null) {
            if (mDebugEnabled) {
                Log.w(TAG, "optDoubleAsWrapperType('" + fieldName + "') Getter was null.");
            }
            return null;
        } else {
            try {
                return ObjectConverters.toDouble(runGetter(method, getItem(getPosition())));
            } catch (Exception e) {
                if (mDebugEnabled) {
                    Log.w(TAG, "optDoubleAsWrapperType('" + fieldName + "') Caught Exception  at " + getPosition() + "/" + getCount(), e);
                    e.printStackTrace();
                }
                return null;
            }
        }
    }

    @Override
    public float optFloat(String fieldName) {
        return optFloat(fieldName, DEFAULT_FLOAT);
    }

    @Override
    public float optFloat(String fieldName, float fallback) {
        final Method method = getGetterForField(applyAlias(fieldName));

        if (method == null) {
            if (mDebugEnabled) {
                Log.w(TAG, "optFloat('" + fieldName + "') Getter was null.");
            }
            return fallback;
        } else {
            try {
                return ObjectConverters.toFloat(runGetter(method, getItem(getPosition())));
            } catch (Exception e) {
                if (mDebugEnabled) {
                    Log.w(TAG, "optFloat('" + fieldName + "') Caught Exception  at " + getPosition() + "/" + getCount(), e);
                    e.printStackTrace();
                }
                return fallback;
            }
        }
    }

    @Override
    public Float optFloatAsWrapperType(String fieldName) {
        final Method method = getGetterForField(applyAlias(fieldName));

        if (method == null) {
            if (mDebugEnabled) {
                Log.w(TAG, "optFloatAsWrapperType('" + fieldName + "') Getter was null.");
            }
            return null;
        } else {
            try {
                return ObjectConverters.toFloat(runGetter(method, getItem(getPosition())));
            } catch (Exception e) {
                if (mDebugEnabled) {
                    Log.w(TAG, "optFloatAsWrapperType('" + fieldName + "') Caught Exception  at " + getPosition() + "/" + getCount(), e);
                    e.printStackTrace();
                }
                return null;
            }
        }
    }

    @Override
    public int optInt(String fieldName) {
        return optInt(fieldName, DEFAULT_INT);
    }

    @Override
    public int optInt(String fieldName, int fallback) {
        final Method method = getGetterForField(applyAlias(fieldName));

        if (method == null) {
            if (mDebugEnabled) {
                Log.w(TAG, "optInt('" + fieldName + "') Getter was null.");
            }
            return fallback;
        } else {
            try {
                return ObjectConverters.toInt(runGetter(method, getItem(getPosition())));
            } catch (Exception e) {
                if (mDebugEnabled) {
                    Log.w(TAG, "optInt('" + fieldName + "') Caught Exception  at " + getPosition() + "/" + getCount(), e);
                    e.printStackTrace();
                }
                return fallback;
            }
        }
    }

    @Override
    public Integer optIntAsWrapperType(String fieldName) {
        final Method method = getGetterForField(applyAlias(fieldName));

        if (method == null) {
            if (mDebugEnabled) {
                Log.w(TAG, "optIntAsWrapperType('" + fieldName + "') Getter was null.");
            }
            return null;
        } else {
            try {
                return ObjectConverters.toInt(runGetter(method, getItem(getPosition())));
            } catch (Exception e) {
                if (mDebugEnabled) {
                    Log.w(TAG, "optIntAsWrapperType('" + fieldName + "') Caught Exception  at " + getPosition() + "/" + getCount(), e);
                    e.printStackTrace();
                }
                return null;
            }
        }
    }

    @Override
    public long optLong(String fieldName) {
        return optLong(fieldName, DEFAULT_LONG);
    }

    @Override
    public long optLong(String fieldName, long fallback) {
        final Method method = getGetterForField(applyAlias(fieldName));

        if (method == null) {
            if (mDebugEnabled) {
                Log.w(TAG, "optLong('" + fieldName + "') Getter was null.");
            }
            return fallback;
        } else {
            try {
                return ObjectConverters.toLong(runGetter(method, getItem(getPosition())));
            } catch (Exception e) {
                if (mDebugEnabled) {
                    Log.w(TAG, "optLong('" + fieldName + "') Caught Exception  at " + getPosition() + "/" + getCount(), e);
                    e.printStackTrace();
                }
                return fallback;
            }
        }
    }

    @Override
    public Long optLongAsWrapperType(String fieldName) {
        final Method method = getGetterForField(applyAlias(fieldName));

        if (method == null) {
            if (mDebugEnabled) {
                Log.w(TAG, "optLongAsWrapperType('" + fieldName + "') Getter was null.");
            }
            return null;
        } else {
            try {
                return ObjectConverters.toLong(runGetter(method, getItem(getPosition())));
            } catch (Exception e) {
                if (mDebugEnabled) {
                    Log.w(TAG, "optLongAsWrapperType('" + fieldName + "') Caught Exception  at " + getPosition() + "/" + getCount(), e);
                    e.printStackTrace();
                }
                return null;
            }
        }
    }

    @Override
    public String optString(String fieldName) {
        return optString(fieldName, DEFAULT_STRING);
    }

    @Override
    public String optString(String fieldName, String fallback) {
        final Method method = getGetterForField(applyAlias(fieldName));

        if (method == null) {
            if (mDebugEnabled) {
                Log.w(TAG, "optString('" + fieldName + "') Getter was null.");
            }
            return fallback;
        } else {
            try {
                return ObjectConverters.toString(runGetter(method, getItem(getPosition())));
            } catch (Exception e) {
                if (mDebugEnabled) {
                    Log.w(TAG, "optString('" + fieldName + "') Caught Exception  at " + getPosition() + "/" + getCount(), e);
                    e.printStackTrace();
                }
                return fallback;
            }
        }
    }

    private void populateMethodList(Class<T> clazz) {
        Method candidate;
        String canditateCleanName;

        int count = 0;
        for (final Method method : clazz.getMethods()) {
            candidate = null;
            canditateCleanName = null;

            if (Modifier.isPublic(method.getModifiers())) {

                // Just in case there is a get()/is() function
                if (method.getName().length() > 3) {
                    if (method.getParameterTypes().length == 0) {
                        if (!method.getReturnType().equals(Void.TYPE)) {

                            if (method.getName().startsWith(GET)) {
                                candidate = method;
                                canditateCleanName =
                                        method.getName().substring(GET.length()).toLowerCase(Locale.US);

                            } else if (method.getName().startsWith(IS)) {
                                candidate = method;
                                canditateCleanName =
                                        method.getName().substring(IS.length()).toLowerCase(Locale.US);
                            }

                            if (candidate != null) {
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
        if (method != null) {
            try {
                return method.invoke(object);
            } catch (IllegalAccessException e) {
                Log.w(TAG, "Could not determine method: " + method.getName());
            } catch (InvocationTargetException e) {
                Log.w(TAG, "Could not determine method: " + method.getName());
            }
        }

        return null;
    }
}
