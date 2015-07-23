/*
 * ***************************************************************************
 * Copyright 2015 Alexandros Schillings
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

package uk.co.alt236.easycursor.objectcursor;

import android.database.AbstractCursor;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import uk.co.alt236.easycursor.EasyCursor;
import uk.co.alt236.easycursor.EasyQueryModel;
import uk.co.alt236.easycursor.exceptions.ConversionErrorException;
import uk.co.alt236.easycursor.internal.conversion.ObjectConverter;
import uk.co.alt236.easycursor.internal.conversion.ObjectType;

public class EasyObjectCursor<T> extends AbstractCursor implements EasyCursor {
    public static final String DEFAULT_STRING = null;
    public static final boolean DEFAULT_BOOLEAN = false;
    public static final double DEFAULT_DOUBLE = 0.0d;
    public static final float DEFAULT_FLOAT = 0.0f;
    public static final int DEFAULT_INT = 0;
    public static final long DEFAULT_LONG = 0l;
    public static final short DEFAULT_SHORT = 0;
    private static final String _ID = "_id";

    private final ObjectConverter mObjectConverter;
    private final EasyQueryModel mQueryModel;
    private final List<T> mObjectList;
    private final String TAG = this.getClass().getName();
    private final String m_IdAlias;
    private final ObjectFieldAccessor<T> mFieldAccessor;
    private boolean mDebugEnabled;

    public EasyObjectCursor(final Class<T> clazz, final List<T> objectList, final String _idAlias) {
        this(clazz, objectList, _idAlias, null);
    }

    public EasyObjectCursor(final Class<T> clazz, final List<T> objectList, final String _idAlias, final EasyQueryModel model) {
        mObjectConverter = new ObjectConverter();
        mQueryModel = model;
        mObjectList = objectList;
        m_IdAlias = _idAlias;
        mFieldAccessor = new ObjectFieldAccessor<>(clazz);
    }

    public EasyObjectCursor(final Class<T> clazz,
                            final T[] objectArray,
                            final String _idAlias) {
        this(clazz, new ArrayList<>(Arrays.asList(objectArray)), _idAlias, null);
    }

    public EasyObjectCursor(final Class<T> clazz,
                            final T[] objectArray,
                            final String _idAlias,
                            final EasyQueryModel model) {
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
        final Object result = internalGet(ObjectType.BYTE_ARRAY, method);
        if (result == null) {
            return null;
        } else {
            return (byte[]) result;
        }
    }

    @Override
    public boolean getBoolean(final String fieldName) {
        final Method method = getGetterForFieldOrThrow(applyAlias(fieldName));
        final Object result = internalGet(ObjectType.BOOLEAN, method);
        if (result == null) {
            return DEFAULT_BOOLEAN;
        } else {
            return (boolean) result;
        }
    }

    @Override
    public int getColumnIndex(final String columnName) {
        final String column = applyAlias(columnName);
        return mFieldAccessor.getFieldIndexByName(column);
    }

    @Override
    public int getColumnIndexOrThrow(final String columnName) {
        final String column = applyAlias(columnName);
        final int index = mFieldAccessor.getFieldIndexByName(column);

        if (index == -1) {
            throw new IllegalArgumentException("There is no column named '" + column + "'");
        } else {
            return index;
        }
    }

    @Override
    public String getColumnName(final int columnIndex) {
        return mFieldAccessor.getFieldNameByIndex(columnIndex);
    }

    @Override
    public String[] getColumnNames() {
        return mFieldAccessor.getFieldNames();
    }

    @Override
    public int getCount() {
        return mObjectList.size();
    }

    @Override
    public double getDouble(final int column) {
        return getDoubleIntenal(mFieldAccessor.getMethod(column));
    }

    @Override
    public double getDouble(final String fieldName) {
        return getDoubleIntenal(getGetterForFieldOrThrow(applyAlias(fieldName)));
    }

    private double getDoubleIntenal(final Method method) {
        final Object result = internalGet(ObjectType.DOUBLE, method);
        if (result == null) {
            return DEFAULT_DOUBLE;
        } else {
            return (double) result;
        }
    }

    @Override
    public float getFloat(final int column) {
        return getFloatInternal(mFieldAccessor.getMethod(column));
    }

    @Override
    public float getFloat(final String fieldName) {
        return getFloatInternal(getGetterForFieldOrThrow(applyAlias(fieldName)));
    }

    private float getFloatInternal(final Method method) {
        final Object result = internalGet(ObjectType.FLOAT, method);
        if (result == null) {
            return DEFAULT_FLOAT;
        } else {
            return (float) result;
        }
    }

    private Method getGetterForFieldOrThrow(final String fieldName) {
        final Method method = mFieldAccessor.getGetterForField(applyAlias(fieldName));
        if (method == null) {
            throw new IllegalArgumentException("Could not find getter for field '" + applyAlias(fieldName) + "'");
        } else {
            return method;
        }
    }

    @Override
    public int getInt(final int column) {
        return getIntInternal(mFieldAccessor.getMethod(column));
    }

    @Override
    public int getInt(final String fieldName) {
        return getIntInternal(getGetterForFieldOrThrow(applyAlias(fieldName)));
    }

    private int getIntInternal(final Method method) {
        final Object result = internalGet(ObjectType.INTEGER, method);
        if (result == null) {
            return DEFAULT_INT;
        } else {
            return (int) result;
        }
    }

    public T getItem(final int position) {
        return mObjectList.get(position);
    }

    @Override
    public long getLong(final int column) {
        return getLongInternal(mFieldAccessor.getMethod(column));
    }

    @Override
    public long getLong(final String fieldName) {
        return getLongInternal(getGetterForFieldOrThrow(applyAlias(fieldName)));
    }

    private long getLongInternal(final Method method) {
        final Object result = internalGet(ObjectType.LONG, method);
        if (result == null) {
            return DEFAULT_LONG;
        } else {
            return (long) result;
        }
    }

    /* package */ List<Method> getMethods() {
        return mFieldAccessor.getMethodList();
    }

    public Object getObject(final int column) {
        return getObjectInternal(mFieldAccessor.getMethod(column));
    }

    public Object getObject(final String fieldName) {
        return getObjectInternal(getGetterForFieldOrThrow(applyAlias(fieldName)));
    }

    private Object getObjectInternal(final Method method) {
        return runGetter(method, getItem(getPosition()));
    }

    @Override
    public EasyQueryModel getQueryModel() {
        return mQueryModel;
    }

    @Override
    public short getShort(final int column) {
        return getShortInternal(mFieldAccessor.getMethod(column));
    }

    public short getShort(final String fieldName) {
        return getShortInternal(getGetterForFieldOrThrow(applyAlias(fieldName)));
    }

    private short getShortInternal(final Method method) {
        final Object result = internalGet(ObjectType.SHORT, method);
        if (result == null) {
            return DEFAULT_SHORT;
        } else {
            return (short) result;
        }
    }

    @Override
    public String getString(final int column) {
        return getStringInternal(mFieldAccessor.getMethod(column));
    }

    @Override
    public String getString(final String fieldName) {
        return getStringInternal(getGetterForFieldOrThrow(applyAlias(fieldName)));
    }

    private String getStringInternal(final Method method) {
        return (String) internalGet(ObjectType.STRING, method);
    }

    @SuppressWarnings("unchecked")
    private Object internalGet(final ObjectType type, final Method method) {
        try {
            return mObjectConverter.toType(type, runGetter(method, getItem(getPosition())));
        } catch (final ConversionErrorException e) {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    private <R> R internalOpt(final ObjectType type,
                              final String fieldName,
                              final R getterMissingFallback,
                              final R conversionErrorFallback) {

        final Method method = mFieldAccessor.getGetterForField(applyAlias(fieldName));

        if (method == null) {
            final String message = String.format(
                    "No getter for '%s'. Type Requested: %s",
                    fieldName,
                    type);
            Log.w(TAG, message + type);
            return getterMissingFallback;
        } else {
            try {
                return (R) mObjectConverter.toType(type, runGetter(method, getItem(getPosition())));
            } catch (final ConversionErrorException e) {
                if (mDebugEnabled) {
                    final String message = String.format(
                            "Failed to convert field '%s' at %d/%d. Type Requested: %s",
                            fieldName,
                            getPosition(),
                            getCount(),
                            type);
                    Log.w(TAG, message, e);
                    e.printStackTrace();
                }
                return conversionErrorFallback;
            }
        }
    }

    public boolean isDebugEnabled() {
        return mDebugEnabled;
    }

    public void setDebugEnabled(final boolean enabled) {
        mDebugEnabled = enabled;
    }

    @Override
    public boolean isNull(final int column) {
        return isNullInternal(mFieldAccessor.getMethod(column));
    }

    @Override
    public boolean isNull(final String fieldName) {
        return isNullInternal(getGetterForFieldOrThrow(applyAlias(fieldName)));
    }

    private boolean isNullInternal(final Method method) {
        return (runGetter(method, getItem(getPosition())) == null);
    }

    @Override
    public boolean optBoolean(final String fieldName) {
        return internalOpt(ObjectType.BOOLEAN, fieldName, DEFAULT_BOOLEAN, DEFAULT_BOOLEAN);
    }

    @Override
    public boolean optBoolean(final String fieldName, final boolean fallback) {
        return internalOpt(ObjectType.BOOLEAN, fieldName, fallback, DEFAULT_BOOLEAN);
    }

    @Override
    public Boolean optBooleanAsWrapperType(final String fieldName) {
        return internalOpt(ObjectType.BOOLEAN, fieldName, null, DEFAULT_BOOLEAN);
    }

    @Override
    public double optDouble(final String fieldName) {
        return internalOpt(ObjectType.DOUBLE, fieldName, DEFAULT_DOUBLE, DEFAULT_DOUBLE);
    }

    @Override
    public double optDouble(final String fieldName, final double fallback) {
        return internalOpt(ObjectType.DOUBLE, fieldName, fallback, DEFAULT_DOUBLE);
    }

    @Override
    public Double optDoubleAsWrapperType(final String fieldName) {
        return internalOpt(ObjectType.DOUBLE, fieldName, null, DEFAULT_DOUBLE);
    }

    @Override
    public float optFloat(final String fieldName) {
        return internalOpt(ObjectType.FLOAT, fieldName, DEFAULT_FLOAT, DEFAULT_FLOAT);
    }

    @Override
    public float optFloat(final String fieldName, final float fallback) {
        return internalOpt(ObjectType.FLOAT, fieldName, fallback, DEFAULT_FLOAT);
    }

    @Override
    public Float optFloatAsWrapperType(final String fieldName) {
        return internalOpt(ObjectType.FLOAT, fieldName, null, DEFAULT_FLOAT);
    }

    @Override
    public int optInt(final String fieldName) {
        return internalOpt(ObjectType.INTEGER, fieldName, DEFAULT_INT, DEFAULT_INT);
    }

    @Override
    public int optInt(final String fieldName, final int fallback) {
        return internalOpt(ObjectType.INTEGER, fieldName, fallback, DEFAULT_INT);
    }

    @Override
    public Integer optIntAsWrapperType(final String fieldName) {
        return internalOpt(ObjectType.INTEGER, fieldName, null, DEFAULT_INT);
    }

    @Override
    public long optLong(final String fieldName) {
        return internalOpt(ObjectType.LONG, fieldName, DEFAULT_LONG, DEFAULT_LONG);
    }

    @Override
    public long optLong(final String fieldName, final long fallback) {
        return internalOpt(ObjectType.LONG, fieldName, fallback, DEFAULT_LONG);
    }

    @Override
    public Long optLongAsWrapperType(final String fieldName) {
        return internalOpt(ObjectType.LONG, fieldName, null, DEFAULT_LONG);
    }

    public short optShort(final String fieldName) {
        return internalOpt(ObjectType.SHORT, fieldName, DEFAULT_SHORT, DEFAULT_SHORT);
    }

    public short optShort(final String fieldName, final short fallback) {
        return internalOpt(ObjectType.SHORT, fieldName, fallback, DEFAULT_SHORT);
    }

    public Short optShortAsWrapperType(final String fieldName) {
        return internalOpt(ObjectType.SHORT, fieldName, null, DEFAULT_SHORT);
    }

    @Override
    public String optString(final String fieldName) {
        return internalOpt(ObjectType.STRING, fieldName, DEFAULT_STRING, DEFAULT_STRING);
    }

    @Override
    public String optString(final String fieldName, final String fallback) {
        return internalOpt(ObjectType.STRING, fieldName, fallback, DEFAULT_STRING);
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
