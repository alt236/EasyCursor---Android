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
        return (byte[]) internalGet(ObjectType.BYTE_ARRAY, method);
    }

    @Override
    public boolean getBoolean(final String fieldName) {
        final Method method = getGetterForFieldOrThrow(applyAlias(fieldName));
        return (boolean) internalGet(ObjectType.BOOLEAN, method);
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
        return (double) internalGet(ObjectType.DOUBLE, method);
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
        return (float) internalGet(ObjectType.FLOAT, method);
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
        return (int) internalGet(ObjectType.INTEGER, method);
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
        return (long) internalGet(ObjectType.LONG, method);
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
        return (short) internalGet(ObjectType.SHORT, method);
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
        return mObjectConverter.toType(type, runGetter(method, getItem(getPosition())));
    }

    @SuppressWarnings("unchecked")
    private <R> R internalOpt(final ObjectType type, final String fieldName, final R fallback) {
        final Method method = mFieldAccessor.getGetterForField(applyAlias(fieldName));

        if (method == null) {
            final String message = String.format(
                    "No getter for '%s'. Type Requested: %s",
                    fieldName,
                    type);
            Log.w(TAG, message + type);
            return fallback;
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
                return fallback;
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
        return internalOpt(ObjectType.BOOLEAN, fieldName, DEFAULT_BOOLEAN);
    }

    @Override
    public boolean optBoolean(final String fieldName, final boolean fallback) {
        return internalOpt(ObjectType.BOOLEAN, fieldName, fallback);
    }

    @Override
    public Boolean optBooleanAsWrapperType(final String fieldName) {
        return internalOpt(ObjectType.BOOLEAN, fieldName, null);
    }

    @Override
    public double optDouble(final String fieldName) {
        return internalOpt(ObjectType.DOUBLE, fieldName, DEFAULT_DOUBLE);
    }

    @Override
    public double optDouble(final String fieldName, final double fallback) {
        return internalOpt(ObjectType.DOUBLE, fieldName, fallback);
    }

    @Override
    public Double optDoubleAsWrapperType(final String fieldName) {
        return internalOpt(ObjectType.DOUBLE, fieldName, null);
    }

    @Override
    public float optFloat(final String fieldName) {
        return internalOpt(ObjectType.FLOAT, fieldName, DEFAULT_FLOAT);
    }

    @Override
    public float optFloat(final String fieldName, final float fallback) {
        return internalOpt(ObjectType.FLOAT, fieldName, fallback);
    }

    @Override
    public Float optFloatAsWrapperType(final String fieldName) {
        return internalOpt(ObjectType.FLOAT, fieldName, null);
    }

    @Override
    public int optInt(final String fieldName) {
        return internalOpt(ObjectType.INTEGER, fieldName, DEFAULT_INT);
    }

    @Override
    public int optInt(final String fieldName, final int fallback) {
        return internalOpt(ObjectType.INTEGER, fieldName, fallback);
    }

    @Override
    public Integer optIntAsWrapperType(final String fieldName) {
        return internalOpt(ObjectType.INTEGER, fieldName, null);
    }

    @Override
    public long optLong(final String fieldName) {
        return internalOpt(ObjectType.LONG, fieldName, DEFAULT_LONG);
    }

    @Override
    public long optLong(final String fieldName, final long fallback) {
        return internalOpt(ObjectType.LONG, fieldName, fallback);
    }

    @Override
    public Long optLongAsWrapperType(final String fieldName) {
        return internalOpt(ObjectType.LONG, fieldName, null);
    }

    public short optShort(final String fieldName) {
        return internalOpt(ObjectType.SHORT, fieldName, DEFAULT_SHORT);
    }

    public short optShort(final String fieldName, final short fallback) {
        return internalOpt(ObjectType.SHORT, fieldName, fallback);
    }

    public Short optShortAsWrapperType(final String fieldName) {
        return internalOpt(ObjectType.SHORT, fieldName, null);
    }

    @Override
    public String optString(final String fieldName) {
        return internalOpt(ObjectType.STRING, fieldName, DEFAULT_STRING);
    }

    @Override
    public String optString(final String fieldName, final String fallback) {
        return internalOpt(ObjectType.STRING, fieldName, fallback);
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
