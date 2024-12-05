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

package dev.alt236.easycursor.jsoncursor;

import android.database.AbstractCursor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dev.alt236.easycursor.EasyCursor;
import dev.alt236.easycursor.EasyQueryModel;
import dev.alt236.easycursor.exceptions.ConversionErrorException;
import dev.alt236.easycursor.internal.FieldAccessor;
import dev.alt236.easycursor.internal.conversion.ObjectConverter;
import dev.alt236.easycursor.internal.conversion.ObjectType;

public class EasyJsonCursor extends AbstractCursor implements EasyCursor {
    public static final String DEFAULT_STRING = null;
    public static final boolean DEFAULT_BOOLEAN = false;
    public static final double DEFAULT_DOUBLE = 0d;
    public static final float DEFAULT_FLOAT = 0f;
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
        return internalGet(ObjectType.BOOLEAN, name, DEFAULT_BOOLEAN);
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
        return internalGet(ObjectType.DOUBLE, name, DEFAULT_DOUBLE);
    }

    @Override
    public float getFloat(final int column) {
        return getFloat(mFieldAccessor.getFieldNameByIndex(column));
    }

    @Override
    public float getFloat(final String name) {
        return internalGet(ObjectType.FLOAT, name, DEFAULT_FLOAT);
    }

    @Override
    public int getInt(final int column) {
        return getInt(mFieldAccessor.getFieldNameByIndex(column));
    }

    @Override
    public int getInt(final String name) {
        return internalGet(ObjectType.INTEGER, name, DEFAULT_INT);
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
        return internalGet(ObjectType.LONG, name, DEFAULT_LONG);
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
        return internalGet(ObjectType.SHORT, name, DEFAULT_SHORT);
    }

    @Override
    public String getString(final int column) {
        return getString(mFieldAccessor.getFieldNameByIndex(column));
    }

    @Override
    public String getString(final String name) {
        return internalGet(ObjectType.STRING, name, DEFAULT_STRING);
    }

    public String get_IdAlias() {
        return m_IdAlias;
    }

    @SuppressWarnings("unchecked")
    private <R> R internalGet(final ObjectType type,
                              final String name,
                              final R conversionErrorFallback) {
        final String alias = applyAlias(name);

        if (!getCurrentJsonObject().has(alias)) {
            throw new IllegalArgumentException("Field '" + alias + "' does not exist");
        }

        try {

            if (getCurrentJsonObject().isNull(alias)) {
                return (R) mObjectConverter.toType(type, null);
            } else {
                final Object value = getCurrentJsonObject().get(alias);
                return (R) mObjectConverter.toType(type, value);
            }
        } catch (final ConversionErrorException e) {
            return conversionErrorFallback;
        } catch (final JSONException e) {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    private <R> R internalOpt(final ObjectType type,
                              final String fieldName,
                              final R fieldMissingFallback,
                              final R conversionErrorFallback) {
        final String alias = applyAlias(fieldName);

        if (!getCurrentJsonObject().has(alias)) {
            return fieldMissingFallback;
        } else {
            try {
                if (getCurrentJsonObject().isNull(alias)) {
                    return (R) mObjectConverter.toType(type, null);
                } else {
                    final Object value = getCurrentJsonObject().opt(alias);
                    return (R) mObjectConverter.toType(type, value);
                }
            } catch (final ConversionErrorException e) {
                return conversionErrorFallback;
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
        return internalOpt(ObjectType.BOOLEAN, name, DEFAULT_BOOLEAN, DEFAULT_BOOLEAN);
    }

    @Override
    public boolean optBoolean(final String name, final boolean fallback) {
        return internalOpt(ObjectType.BOOLEAN, name, fallback, DEFAULT_BOOLEAN);
    }

    @Override
    public Boolean optBooleanAsWrapperType(final String name) {
        return internalOpt(ObjectType.BOOLEAN, name, null, DEFAULT_BOOLEAN);
    }

    @Override
    public double optDouble(final String name) {
        return internalOpt(ObjectType.DOUBLE, name, DEFAULT_DOUBLE, DEFAULT_DOUBLE);
    }

    @Override
    public double optDouble(final String name, final double fallback) {
        return internalOpt(ObjectType.DOUBLE, name, fallback, DEFAULT_DOUBLE);
    }

    @Override
    public Double optDoubleAsWrapperType(final String name) {
        return internalOpt(ObjectType.DOUBLE, name, null, DEFAULT_DOUBLE);
    }

    @Override
    public float optFloat(final String name) {
        return internalOpt(ObjectType.FLOAT, name, DEFAULT_FLOAT, DEFAULT_FLOAT);
    }

    @Override
    public float optFloat(final String name, final float fallback) {
        return internalOpt(ObjectType.FLOAT, name, fallback, DEFAULT_FLOAT);
    }

    @Override
    public Float optFloatAsWrapperType(final String name) {
        return internalOpt(ObjectType.FLOAT, name, null, DEFAULT_FLOAT);
    }

    @Override
    public int optInt(final String name) {
        return internalOpt(ObjectType.INTEGER, name, DEFAULT_INT, DEFAULT_INT);
    }

    @Override
    public int optInt(final String name, final int fallback) {
        return internalOpt(ObjectType.INTEGER, name, fallback, DEFAULT_INT);
    }

    @Override
    public Integer optIntAsWrapperType(final String name) {
        return internalOpt(ObjectType.INTEGER, name, null, DEFAULT_INT);
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
        return internalOpt(ObjectType.LONG, name, DEFAULT_LONG, DEFAULT_LONG);
    }

    @Override
    public long optLong(final String name, final long fallback) {
        return internalOpt(ObjectType.LONG, name, fallback, DEFAULT_LONG);
    }

    @Override
    public Long optLongAsWrapperType(final String name) {
        return internalOpt(ObjectType.LONG, name, null, DEFAULT_LONG);
    }

    public short optShort(final String name) {
        return internalOpt(ObjectType.SHORT, name, DEFAULT_SHORT, DEFAULT_SHORT);
    }

    public short optShort(final String name, final short fallback) {
        return internalOpt(ObjectType.SHORT, name, fallback, DEFAULT_SHORT);
    }

    public Short optShortAsWrapperType(final String name) {
        return internalOpt(ObjectType.SHORT, name, null, DEFAULT_SHORT);
    }

    @Override
    public String optString(final String name) {
        return internalOpt(ObjectType.STRING, name, DEFAULT_STRING, DEFAULT_STRING);
    }

    @Override
    public String optString(final String name, final String fallback) {
        return internalOpt(ObjectType.STRING, name, fallback, DEFAULT_STRING);
    }
}
