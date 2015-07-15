package uk.co.alt236.easycursor.jsoncursor;

import android.database.AbstractCursor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import uk.co.alt236.easycursor.EasyCursor;
import uk.co.alt236.easycursor.EasyQueryModel;

public class EasyJsonCursor extends AbstractCursor implements EasyCursor {
    private static final String _ID = "_id";
    private final EasyQueryModel mQueryModel;
    private final JSONArray mJsonArray;
    private final Map<String, Integer> mPropertyToIndexMap;
    private final String m_IdAlias;
    private final List<String> mPropertyList;

    public EasyJsonCursor(final JSONArray array, final String _idAlias) {
        this(array, _idAlias, null);
    }

    public EasyJsonCursor(final JSONArray array, final String _idAlias, final EasyQueryModel model) {
        mPropertyList = new ArrayList<>();
        mPropertyToIndexMap = new HashMap<>();
        m_IdAlias = _idAlias;
        mQueryModel = model;
        mJsonArray = array;
        populateMethodList(array);
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
            throw new EasyJsonException(e);
        }
    }

    @Override
    public int getColumnIndex(final String name) {
        final String column = applyAlias(name);

        if (mPropertyToIndexMap.containsKey(column)) {
            return mPropertyToIndexMap.get(column).intValue();
        } else {
            return -1;
        }
    }

    @Override
    public int getColumnIndexOrThrow(final String name) {
        final String column = applyAlias(name);

        if (mPropertyToIndexMap.containsKey(column)) {
            return mPropertyToIndexMap.get(column).intValue();
        } else {
            throw new IllegalArgumentException("There is no column named '" + column + "'");
        }
    }

    @Override
    public String getColumnName(final int columnIndex) {
        return mPropertyList.get(columnIndex);
    }

    @Override
    public String[] getColumnNames() {
        return mPropertyList.toArray(new String[mPropertyList.size()]);
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
        return getDouble(mPropertyList.get(column));
    }

    @Override
    public double getDouble(final String name) {
        try {
            return getCurrentJsonObject().getDouble(applyAlias(name));
        } catch (final JSONException e) {
            throw new EasyJsonException(e);
        }
    }

    @Override
    public float getFloat(final int column) {
        return getFloat(mPropertyList.get(column));
    }

    @Override
    public float getFloat(final String name) {
        try {
            return (float) getCurrentJsonObject().getDouble(applyAlias(name));
        } catch (final JSONException e) {
            throw new EasyJsonException(e);
        }
    }

    @Override
    public int getInt(final int column) {
        return getInt(mPropertyList.get(column));
    }

    @Override
    public int getInt(final String name) {
        try {
            return getCurrentJsonObject().getInt(applyAlias(name));
        } catch (final JSONException e) {
            throw new EasyJsonException(e);
        }
    }

    public JSONArray getJSONArray(final int column) {
        return getJSONArray(mPropertyList.get(column));
    }

    public JSONArray getJSONArray(final String name) {
        try {
            return getCurrentJsonObject().getJSONArray(applyAlias(name));
        } catch (final JSONException e) {
            throw new EasyJsonException(e);
        }
    }

    public JSONObject getJSONObject(final int column) {
        return getJSONObject(mPropertyList.get(column));
    }

    public JSONObject getJSONObject(final String name) {
        try {
            return getCurrentJsonObject().getJSONObject(applyAlias(name));
        } catch (final JSONException e) {
            throw new EasyJsonException(e);
        }
    }

    @Override
    public long getLong(final int column) {
        return getLong(mPropertyList.get(column));
    }

    @Override
    public long getLong(final String name) {
        try {
            return getCurrentJsonObject().getLong(applyAlias(name));
        } catch (final JSONException e) {
            throw new EasyJsonException(e);
        }
    }

    @Override
    public EasyQueryModel getQueryModel() {
        return mQueryModel;
    }

    @Override
    public short getShort(final int column) {
        return getShort(mPropertyList.get(column));
    }

    public short getShort(final String name) {
        return (short) getInt(applyAlias(name));
    }

    @Override
    public String getString(final int column) {
        return getString(mPropertyList.get(column));
    }

    @Override
    public String getString(final String name) {
        try {
            return getCurrentJsonObject().getString(applyAlias(name));
        } catch (final JSONException e) {
            throw new EasyJsonException(e);
        }
    }

    public String get_IdAlias() {
        return m_IdAlias;
    }

    @Override
    public boolean isNull(final int column) {
        return isNull(mPropertyList.get(column));
    }

    @Override
    public boolean isNull(final String name) {
        return getCurrentJsonObject().isNull(applyAlias(name));
    }

    @Override
    public boolean optBoolean(final String name) {
        return getCurrentJsonObject().optBoolean(applyAlias(name));
    }

    @Override
    public boolean optBoolean(final String name, final boolean fallback) {
        return getCurrentJsonObject().optBoolean(applyAlias(name), fallback);
    }

    @Override
    public Boolean optBooleanAsWrapperType(final String name) {
        final String column = applyAlias(name);
        if (getCurrentJsonObject().has(column)) {
            return optBoolean(column);
        } else {
            return null;
        }
    }

    @Override
    public double optDouble(final String name) {
        return getCurrentJsonObject().optDouble(applyAlias(name));
    }

    @Override
    public double optDouble(final String name, final double fallback) {
        return getCurrentJsonObject().optDouble(applyAlias(name), fallback);
    }

    @Override
    public Double optDoubleAsWrapperType(final String name) {
        final String column = applyAlias(name);
        if (getCurrentJsonObject().has(column)) {
            return optDouble(column);
        } else {
            return null;
        }
    }

    @Override
    public float optFloat(final String name) {
        return (float) getCurrentJsonObject().optDouble(applyAlias(name));
    }

    @Override
    public float optFloat(final String name, final float fallback) {
        return (float) getCurrentJsonObject().optDouble(applyAlias(name), fallback);
    }

    @Override
    public Float optFloatAsWrapperType(final String name) {
        final String column = applyAlias(name);
        if (getCurrentJsonObject().has(column)) {
            return optFloat(column);
        } else {
            return null;
        }
    }

    @Override
    public int optInt(final String name) {
        return getCurrentJsonObject().optInt(applyAlias(name));
    }

    @Override
    public int optInt(final String name, final int fallback) {
        return getCurrentJsonObject().optInt(applyAlias(name), fallback);
    }

    @Override
    public Integer optIntAsWrapperType(final String name) {
        if (getCurrentJsonObject().has(name)) {
            return optInt(name);
        } else {
            return null;
        }
    }

    public JSONArray optJSONArray(final int column) {
        return optJSONArray(mPropertyList.get(column));
    }

    public JSONArray optJSONArray(final String name) {
        return getCurrentJsonObject().optJSONArray(applyAlias(name));
    }

    public JSONObject optJSONObject(final int column) {
        return optJSONObject(mPropertyList.get(column));
    }

    public JSONObject optJSONObject(final String name) {
        return getCurrentJsonObject().optJSONObject(applyAlias(name));
    }

    @Override
    public long optLong(final String name) {
        return getCurrentJsonObject().optLong(applyAlias(name));
    }

    @Override
    public long optLong(final String name, final long fallback) {
        return getCurrentJsonObject().optLong(applyAlias(name), fallback);
    }

    @Override
    public Long optLongAsWrapperType(final String name) {
        final String column = applyAlias(name);
        if (getCurrentJsonObject().has(column)) {
            return optLong(column);
        } else {
            return null;
        }
    }

    @Override
    public String optString(final String name) {
        return getCurrentJsonObject().optString(applyAlias(name));
    }

    @Override
    public String optString(final String name, final String fallback) {
        return getCurrentJsonObject().optString(applyAlias(name), fallback);
    }

    private void populateMethodList(final JSONArray array) {
        int count = 0;
        final JSONObject obj = array.optJSONObject(0);

        @SuppressWarnings("unchecked")
        final Iterator<String> keyIterator = obj.keys();
        while (keyIterator.hasNext()) {
            final String key = keyIterator.next();
            mPropertyList.add(key);
            mPropertyToIndexMap.put(key, count);
            count++;
        }
    }
}
