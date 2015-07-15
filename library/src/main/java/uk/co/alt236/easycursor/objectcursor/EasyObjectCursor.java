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

    public EasyObjectCursor(final Class<T> clazz, final List<T> objectList, final String _idAlias) {
        this(clazz, objectList, _idAlias, null);
    }

    public EasyObjectCursor(final Class<T> clazz, final List<T> objectList, final String _idAlias, final EasyQueryModel model) {
        mQueryModel = model;
        mFieldToIndexMap = new HashMap<>();
        mFieldToMethodMap = Collections.synchronizedMap(new HashMap<String, Method>());
        mMethodList = new ArrayList<>();
        mFieldNameList = new ArrayList<>();
        mObjectList = objectList;
        m_IdAlias = _idAlias;

        populateMethodList(clazz);
    }

    public EasyObjectCursor(final Class<T> clazz, final T[] objectArray, final String _idAlias) {
        this(clazz, new ArrayList<>(Arrays.asList(objectArray)), _idAlias, null);
    }

    public EasyObjectCursor(final Class<T> clazz, final T[] objectArray, final String _idAlias, final EasyQueryModel model) {
        this(clazz, new ArrayList<>(Arrays.asList(objectArray)), _idAlias, model);
    }

    private String applyAlias(final String columnName) {
        if (_ID.equals(columnName)) {
            if (m_IdAlias != null) {
                return m_IdAlias;
            }
        }

        return columnName;
    }

    @Override
    public byte[] getBlob(final String fieldName) {
        final Method method = getGetterForFieldOrThrow(applyAlias(fieldName));
        return ObjectConverters.toByteArray(runGetter(method, getItem(getPosition())));
    }

    @Override
    public boolean getBoolean(final String fieldName) {
        final Method method = getGetterForFieldOrThrow(applyAlias(fieldName));
        return ObjectConverters.toBoolean(runGetter(method, getItem(getPosition())));
    }

    @Override
    public int getColumnIndex(final String columnName) {
        final String column = applyAlias(columnName);
        if (mFieldToIndexMap.containsKey(column)) {
            return mFieldToIndexMap.get(column).intValue();
        } else {
            return -1;
        }
    }

    @Override
    public int getColumnIndexOrThrow(final String columnName) {
        final String column = applyAlias(columnName);
        if (mFieldToIndexMap.containsKey(column)) {
            return mFieldToIndexMap.get(column).intValue();
        } else {
            throw new IllegalArgumentException("There is no column named '" + column + "'");
        }
    }

    @Override
    public String getColumnName(final int columnIndex) {
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
    public double getDouble(final int column) {
        return getDouble(mMethodList.get(column));
    }

    private double getDouble(final Method method) {
        return ObjectConverters.toDouble(runGetter(method, getItem(getPosition())));
    }

    @Override
    public double getDouble(final String fieldName) {
        return getDouble(getGetterForFieldOrThrow(applyAlias(fieldName)));
    }

    @Override
    public float getFloat(final int column) {
        return getFloat(mMethodList.get(column));
    }

    private float getFloat(final Method method) {
        return ObjectConverters.toFloat(runGetter(method, getItem(getPosition())));
    }

    @Override
    public float getFloat(final String fieldName) {
        return getFloat(getGetterForFieldOrThrow(applyAlias(fieldName)));
    }

    private synchronized Method getGetterForField(final String field) {
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

    private Method getGetterForFieldOrThrow(final String fieldName) {
        final Method method = getGetterForField(applyAlias(fieldName));
        if (method == null) {
            throw new IllegalArgumentException("Could not find getter for field '" + applyAlias(fieldName) + "'");
        } else {
            return method;
        }
    }

    @Override
    public int getInt(final int column) {
        return getInt(mMethodList.get(column));
    }

    private int getInt(final Method method) {
        return ObjectConverters.toInt(runGetter(method, getItem(getPosition())));
    }

    @Override
    public int getInt(final String fieldName) {
        return getInt(getGetterForFieldOrThrow(applyAlias(fieldName)));
    }

    public T getItem(final int position) {
        return mObjectList.get(position);
    }

    @Override
    public long getLong(final int column) {
        return getLong(mMethodList.get(column));
    }

    private long getLong(final Method method) {
        return ObjectConverters.toLong(runGetter(method, getItem(getPosition())));
    }

    @Override
    public long getLong(final String fieldName) {
        return getLong(getGetterForFieldOrThrow(applyAlias(fieldName)));
    }

    public List<Method> getMethods() {
        return mMethodList;
    }

    public Object getObject(final int column) {
        return getObject(mMethodList.get(column));
    }

    private Object getObject(final Method method) {
        return runGetter(method, getItem(getPosition()));
    }

    public Object getObject(final String fieldName) {
        return getObject(getGetterForFieldOrThrow(applyAlias(fieldName)));
    }

    @Override
    public EasyQueryModel getQueryModel() {
        return mQueryModel;
    }

    @Override
    public short getShort(final int column) {
        return getShort(mMethodList.get(column));
    }

    public short getShort(final Method method) {
        return ObjectConverters.toShort(runGetter(method, getItem(getPosition())));
    }

    public short getShort(final String fieldName) {
        return getShort(getGetterForFieldOrThrow(applyAlias(fieldName)));
    }

    @Override
    public String getString(final int column) {
        return getString(mMethodList.get(column));
    }

    private String getString(final Method method) {
        return ObjectConverters.toString(runGetter(method, getItem(getPosition())));
    }

    @Override
    public String getString(final String fieldName) {
        return getString(getGetterForFieldOrThrow(applyAlias(fieldName)));
    }

    public boolean isDebugEnabled() {
        return mDebugEnabled;
    }

    public void setDebugEnabled(final boolean enabled) {
        mDebugEnabled = enabled;
    }

    @Override
    public boolean isNull(final int column) {
        return isNull(mMethodList.get(column));
    }

    private boolean isNull(final Method method) {
        return (runGetter(method, getItem(getPosition())) == null);
    }

    @Override
    public boolean isNull(final String fieldName) {
        return isNull(getGetterForFieldOrThrow(applyAlias(fieldName)));
    }

    @Override
    public boolean optBoolean(final String fieldName) {
        return optBoolean(fieldName, DEFAULT_BOOLEAN);
    }

    @Override
    public boolean optBoolean(final String fieldName, final boolean fallback) {
        final Method method = getGetterForField(applyAlias(fieldName));

        if (method == null) {
            return fallback;
        } else {
            try {
                if (mDebugEnabled) {
                    Log.w(TAG, "optBoolean('" + fieldName + "') Getter was null.");
                }
                return ObjectConverters.toBoolean(runGetter(method, getItem(getPosition())));
            } catch (final Exception e) {
                if (mDebugEnabled) {
                    Log.w(TAG, "optBoolean('" + fieldName + "') Caught Exception  at " + getPosition() + "/" + getCount(), e);
                    e.printStackTrace();
                }
                return fallback;
            }
        }
    }

    @Override
    public Boolean optBooleanAsWrapperType(final String fieldName) {
        final Method method = getGetterForField(applyAlias(fieldName));

        if (method == null) {
            if (mDebugEnabled) {
                Log.w(TAG, "optBooleanAsWrapperType('" + fieldName + "') Getter was null.");
            }
            return null;
        } else {
            try {
                return ObjectConverters.toBoolean(runGetter(method, getItem(getPosition())));
            } catch (final Exception e) {
                if (mDebugEnabled) {
                    Log.w(TAG, "optBooleanAsWrapperType('" + fieldName + "') Caught Exception  at " + getPosition() + "/" + getCount(), e);
                    e.printStackTrace();
                }
                return null;
            }
        }
    }

    @Override
    public double optDouble(final String fieldName) {
        return optDouble(fieldName, DEFAULT_DOUBLE);
    }

    @Override
    public double optDouble(final String fieldName, final double fallback) {
        final Method method = getGetterForField(applyAlias(fieldName));

        if (method == null) {
            if (mDebugEnabled) {
                Log.w(TAG, "optDouble('" + fieldName + "') Getter was null.");
            }
            return fallback;
        } else {
            try {
                return ObjectConverters.toDouble(runGetter(method, getItem(getPosition())));
            } catch (final Exception e) {
                if (mDebugEnabled) {
                    Log.w(TAG, "optDouble('" + fieldName + "') Caught Exception  at " + getPosition() + "/" + getCount(), e);
                    e.printStackTrace();
                }
                return fallback;
            }
        }
    }

    @Override
    public Double optDoubleAsWrapperType(final String fieldName) {
        final Method method = getGetterForField(applyAlias(fieldName));

        if (method == null) {
            if (mDebugEnabled) {
                Log.w(TAG, "optDoubleAsWrapperType('" + fieldName + "') Getter was null.");
            }
            return null;
        } else {
            try {
                return ObjectConverters.toDouble(runGetter(method, getItem(getPosition())));
            } catch (final Exception e) {
                if (mDebugEnabled) {
                    Log.w(TAG, "optDoubleAsWrapperType('" + fieldName + "') Caught Exception  at " + getPosition() + "/" + getCount(), e);
                    e.printStackTrace();
                }
                return null;
            }
        }
    }

    @Override
    public float optFloat(final String fieldName) {
        return optFloat(fieldName, DEFAULT_FLOAT);
    }

    @Override
    public float optFloat(final String fieldName, final float fallback) {
        final Method method = getGetterForField(applyAlias(fieldName));

        if (method == null) {
            if (mDebugEnabled) {
                Log.w(TAG, "optFloat('" + fieldName + "') Getter was null.");
            }
            return fallback;
        } else {
            try {
                return ObjectConverters.toFloat(runGetter(method, getItem(getPosition())));
            } catch (final Exception e) {
                if (mDebugEnabled) {
                    Log.w(TAG, "optFloat('" + fieldName + "') Caught Exception  at " + getPosition() + "/" + getCount(), e);
                    e.printStackTrace();
                }
                return fallback;
            }
        }
    }

    @Override
    public Float optFloatAsWrapperType(final String fieldName) {
        final Method method = getGetterForField(applyAlias(fieldName));

        if (method == null) {
            if (mDebugEnabled) {
                Log.w(TAG, "optFloatAsWrapperType('" + fieldName + "') Getter was null.");
            }
            return null;
        } else {
            try {
                return ObjectConverters.toFloat(runGetter(method, getItem(getPosition())));
            } catch (final Exception e) {
                if (mDebugEnabled) {
                    Log.w(TAG, "optFloatAsWrapperType('" + fieldName + "') Caught Exception  at " + getPosition() + "/" + getCount(), e);
                    e.printStackTrace();
                }
                return null;
            }
        }
    }

    @Override
    public int optInt(final String fieldName) {
        return optInt(fieldName, DEFAULT_INT);
    }

    @Override
    public int optInt(final String fieldName, final int fallback) {
        final Method method = getGetterForField(applyAlias(fieldName));

        if (method == null) {
            if (mDebugEnabled) {
                Log.w(TAG, "optInt('" + fieldName + "') Getter was null.");
            }
            return fallback;
        } else {
            try {
                return ObjectConverters.toInt(runGetter(method, getItem(getPosition())));
            } catch (final Exception e) {
                if (mDebugEnabled) {
                    Log.w(TAG, "optInt('" + fieldName + "') Caught Exception  at " + getPosition() + "/" + getCount(), e);
                    e.printStackTrace();
                }
                return fallback;
            }
        }
    }

    @Override
    public Integer optIntAsWrapperType(final String fieldName) {
        final Method method = getGetterForField(applyAlias(fieldName));

        if (method == null) {
            if (mDebugEnabled) {
                Log.w(TAG, "optIntAsWrapperType('" + fieldName + "') Getter was null.");
            }
            return null;
        } else {
            try {
                return ObjectConverters.toInt(runGetter(method, getItem(getPosition())));
            } catch (final Exception e) {
                if (mDebugEnabled) {
                    Log.w(TAG, "optIntAsWrapperType('" + fieldName + "') Caught Exception  at " + getPosition() + "/" + getCount(), e);
                    e.printStackTrace();
                }
                return null;
            }
        }
    }

    @Override
    public long optLong(final String fieldName) {
        return optLong(fieldName, DEFAULT_LONG);
    }

    @Override
    public long optLong(final String fieldName, final long fallback) {
        final Method method = getGetterForField(applyAlias(fieldName));

        if (method == null) {
            if (mDebugEnabled) {
                Log.w(TAG, "optLong('" + fieldName + "') Getter was null.");
            }
            return fallback;
        } else {
            try {
                return ObjectConverters.toLong(runGetter(method, getItem(getPosition())));
            } catch (final Exception e) {
                if (mDebugEnabled) {
                    Log.w(TAG, "optLong('" + fieldName + "') Caught Exception  at " + getPosition() + "/" + getCount(), e);
                    e.printStackTrace();
                }
                return fallback;
            }
        }
    }

    @Override
    public Long optLongAsWrapperType(final String fieldName) {
        final Method method = getGetterForField(applyAlias(fieldName));

        if (method == null) {
            if (mDebugEnabled) {
                Log.w(TAG, "optLongAsWrapperType('" + fieldName + "') Getter was null.");
            }
            return null;
        } else {
            try {
                return ObjectConverters.toLong(runGetter(method, getItem(getPosition())));
            } catch (final Exception e) {
                if (mDebugEnabled) {
                    Log.w(TAG, "optLongAsWrapperType('" + fieldName + "') Caught Exception  at " + getPosition() + "/" + getCount(), e);
                    e.printStackTrace();
                }
                return null;
            }
        }
    }

    @Override
    public String optString(final String fieldName) {
        return optString(fieldName, DEFAULT_STRING);
    }

    @Override
    public String optString(final String fieldName, final String fallback) {
        final Method method = getGetterForField(applyAlias(fieldName));

        if (method == null) {
            if (mDebugEnabled) {
                Log.w(TAG, "optString('" + fieldName + "') Getter was null.");
            }
            return fallback;
        } else {
            try {
                return ObjectConverters.toString(runGetter(method, getItem(getPosition())));
            } catch (final Exception e) {
                if (mDebugEnabled) {
                    Log.w(TAG, "optString('" + fieldName + "') Caught Exception  at " + getPosition() + "/" + getCount(), e);
                    e.printStackTrace();
                }
                return fallback;
            }
        }
    }

    private void populateMethodList(final Class<T> clazz) {
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

    private Object runGetter(final Method method, final T object) {
        if (method != null) {
            try {
                return method.invoke(object);
            } catch (final IllegalAccessException e) {
                Log.w(TAG, "Could not determine method: " + method.getName());
            } catch (final InvocationTargetException e) {
                Log.w(TAG, "Could not determine method: " + method.getName());
            }
        }

        return null;
    }
}
