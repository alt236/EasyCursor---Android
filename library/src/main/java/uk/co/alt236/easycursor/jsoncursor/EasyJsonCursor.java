package uk.co.alt236.easycursor.jsoncursor;

import android.database.AbstractCursor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import uk.co.alt236.easycursor.EasyCursor;
import uk.co.alt236.easycursor.EasyQueryModel;
import uk.co.alt236.easycursor.exceptions.ConversionErrorException;
import uk.co.alt236.easycursor.internal.FieldAccessor;
import uk.co.alt236.easycursor.internal.conversion.ObjectConverter;
import uk.co.alt236.easycursor.internal.conversion.ObjectType;

public class EasyJsonCursor extends AbstractCursor implements EasyCursor {
    public static final String DEFAULT_STRING = null;
    public static final boolean DEFAULT_BOOLEAN = false;
    public static final double DEFAULT_DOUBLE = Double.NaN;
    public static final float DEFAULT_FLOAT = Float.NaN;
    public static final int DEFAULT_INT = 0;
    public static final long DEFAULT_LONG = 0l;
    public static final short DEFAULT_SHORT = 0;
    private static final String _ID = "_id";
    private final ObjectConverter mObjectConverter;
    private final EasyQueryModel mQueryModel;
    private final JSONArray mJsonArray;
    private final String m_IdAlias;
    private final FieldAccessor mFieldAccessor;

    public EasyJsonCursor(final JSONArray array, final String _idAlias) {
        this(array, _idAlias, null);
    }

    public EasyJsonCursor(final JSONArray array, final String _idAlias, final EasyQueryModel model) {
        mObjectConverter = new ObjectConverter();
        mFieldAccessor = new JsonFieldAccessor(array);

        m_IdAlias = _idAlias;
        mQueryModel = model;
        mJsonArray = array;
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
    public byte[] getBlob(final String name) {
        throw new UnsupportedOperationException("getBlob is not supported");
    }

    @Override
    public boolean getBoolean(final String name) {
        try {
            return getCurrentJsonObject().getBoolean(applyAlias(name));
        } catch (final JSONException e) {
            throw new IllegalArgumentException("Field '" + applyAlias(name) + "' does not exist");
        }
    }

    @Override
    public int getColumnIndex(final String name) {
        final String column = applyAlias(name);
        return mFieldAccessor.getFieldIndexByName(column);
    }

    @Override
    public int getColumnIndexOrThrow(final String name) {
        final String column = applyAlias(name);
        final int index = mFieldAccessor.getFieldIndexByName(column);

        if(index == -1){
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
        return mJsonArray.length();
    }

    public JSONObject getCurrentJsonObject() {
        return mJsonArray.optJSONObject(getPosition());
    }

    @Override
    public double getDouble(final int column) {
        return getDouble(mFieldAccessor.getFieldNameByIndex(column));
    }

    @Override
    public double getDouble(final String name) {
        try {
            return getCurrentJsonObject().getDouble(applyAlias(name));
        } catch (final JSONException e) {
            throw new IllegalArgumentException("Field '" + applyAlias(name) + "' does not exist");
        }
    }

    @Override
    public float getFloat(final int column) {
        return getFloat(mFieldAccessor.getFieldNameByIndex(column));
    }

    @Override
    public float getFloat(final String name) {
        try {
            return (float) getCurrentJsonObject().getDouble(applyAlias(name));
        } catch (final JSONException e) {
            throw new IllegalArgumentException("Field '" + applyAlias(name) + "' does not exist");
        }
    }

    @Override
    public int getInt(final int column) {
        return getInt(mFieldAccessor.getFieldNameByIndex(column));
    }

    @Override
    public int getInt(final String name) {
        try {
            return getCurrentJsonObject().getInt(applyAlias(name));
        } catch (final JSONException e) {
            throw new IllegalArgumentException("Field '" + applyAlias(name) + "' does not exist");
        }
    }

    public JSONArray getJSONArray(final int column) {
        return getJSONArray(mFieldAccessor.getFieldNameByIndex(column));
    }

    public JSONArray getJSONArray(final String name) {
        try {
            return getCurrentJsonObject().getJSONArray(applyAlias(name));
        } catch (final JSONException e) {
            throw new IllegalArgumentException("Field '" + applyAlias(name) + "' does not exist");
        }
    }

    public JSONObject getJSONObject(final int column) {
        return getJSONObject(mFieldAccessor.getFieldNameByIndex(column));
    }

    public JSONObject getJSONObject(final String name) {
        try {
            return getCurrentJsonObject().getJSONObject(applyAlias(name));
        } catch (final JSONException e) {
            throw new IllegalArgumentException("Field '" + applyAlias(name) + "' does not exist");
        }
    }

    @Override
    public long getLong(final int column) {
        return getLong(mFieldAccessor.getFieldNameByIndex(column));
    }

    @Override
    public long getLong(final String name) {
        try {
            return getCurrentJsonObject().getLong(applyAlias(name));
        } catch (final JSONException e) {
            throw new IllegalArgumentException("Field '" + applyAlias(name) + "' does not exist");
        }
    }

    @Override
    public EasyQueryModel getQueryModel() {
        return mQueryModel;
    }

    @Override
    public short getShort(final int column) {
        return getShort(mFieldAccessor.getFieldNameByIndex(column));
    }

    public short getShort(final String name) {
        return (short) getInt(applyAlias(name));
    }

    @Override
    public String getString(final int column) {
        return getString(mFieldAccessor.getFieldNameByIndex(column));
    }

    @Override
    public String getString(final String name) {
        try {
            return getCurrentJsonObject().getString(applyAlias(name));
        } catch (final JSONException e) {
            throw new IllegalArgumentException("Field '" + applyAlias(name) + "' does not exist");
        }
    }

    public String get_IdAlias() {
        return m_IdAlias;
    }

    @SuppressWarnings("unchecked")
    private <R> R internalGet(final ObjectType type, final String fieldName, final R fallback) {
        final String field = applyAlias(fieldName);

        if (!getCurrentJsonObject().has(field)) {
            return fallback;
        } else {
            try {
                return (R) mObjectConverter.toType(type, getCurrentJsonObject().opt(field));
            } catch (final ConversionErrorException e) {
                return fallback;
            }
        }
    }

    @Override
    public boolean isNull(final int column) {
        return isNull(mFieldAccessor.getFieldNameByIndex(column));
    }

    @Override
    public boolean isNull(final String name) {
        return getCurrentJsonObject().isNull(applyAlias(name));
    }

    @Override
    public boolean optBoolean(final String name) {
        return internalGet(ObjectType.BOOLEAN, name, DEFAULT_BOOLEAN);
    }

    @Override
    public boolean optBoolean(final String name, final boolean fallback) {
        return internalGet(ObjectType.BOOLEAN, name, fallback);
    }

    @Override
    public Boolean optBooleanAsWrapperType(final String name) {
        return internalGet(ObjectType.BOOLEAN, name, null);
    }

    @Override
    public double optDouble(final String name) {
        return internalGet(ObjectType.DOUBLE, name, DEFAULT_DOUBLE);
    }

    @Override
    public double optDouble(final String name, final double fallback) {
        return internalGet(ObjectType.DOUBLE, name, fallback);
    }

    @Override
    public Double optDoubleAsWrapperType(final String name) {
        return internalGet(ObjectType.DOUBLE, name, null);
    }

    @Override
    public float optFloat(final String name) {
        return internalGet(ObjectType.FLOAT, name, DEFAULT_FLOAT);
    }

    @Override
    public float optFloat(final String name, final float fallback) {
        return internalGet(ObjectType.FLOAT, name, fallback);
    }

    @Override
    public Float optFloatAsWrapperType(final String name) {
        return internalGet(ObjectType.FLOAT, name, null);
    }

    @Override
    public int optInt(final String name) {
        return internalGet(ObjectType.INTEGER, name, DEFAULT_INT);
    }

    @Override
    public int optInt(final String name, final int fallback) {
        return internalGet(ObjectType.INTEGER, name, fallback);
    }

    @Override
    public Integer optIntAsWrapperType(final String name) {
        return internalGet(ObjectType.INTEGER, name, null);
    }

    public JSONArray optJSONArray(final int column) {
        return optJSONArray(mFieldAccessor.getFieldNameByIndex(column));
    }

    public JSONArray optJSONArray(final String name) {
        return getCurrentJsonObject().optJSONArray(applyAlias(name));
    }

    public JSONObject optJSONObject(final int column) {
        return optJSONObject(mFieldAccessor.getFieldNameByIndex(column));
    }

    public JSONObject optJSONObject(final String name) {
        return getCurrentJsonObject().optJSONObject(applyAlias(name));
    }

    @Override
    public long optLong(final String name) {
        return internalGet(ObjectType.LONG, name, DEFAULT_LONG);
    }

    @Override
    public long optLong(final String name, final long fallback) {
        return internalGet(ObjectType.LONG, name, fallback);
    }

    @Override
    public Long optLongAsWrapperType(final String name) {
        return internalGet(ObjectType.LONG, name, null);
    }

    public short optShort(final String name) {
        return internalGet(ObjectType.SHORT, name, DEFAULT_SHORT);
    }

    public short optShort(final String name, final short fallback) {
        return internalGet(ObjectType.SHORT, name, fallback);
    }

    public Short optShortAsWrapperType(final String name) {
        return internalGet(ObjectType.SHORT, name, null);
    }

    @Override
    public String optString(final String name) {
        return internalGet(ObjectType.STRING, name, DEFAULT_STRING);
    }

    @Override
    public String optString(final String name, final String fallback) {
        return internalGet(ObjectType.STRING, name, fallback);
    }
}
