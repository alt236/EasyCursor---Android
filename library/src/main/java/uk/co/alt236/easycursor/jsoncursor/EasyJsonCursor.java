package uk.co.alt236.easycursor.jsoncursor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import uk.co.alt236.easycursor.EasyCursor;
import uk.co.alt236.easycursor.EasyQueryModel;
import android.database.AbstractCursor;

public class EasyJsonCursor extends AbstractCursor implements EasyCursor{
	private static final String _ID = "_id";

	private List<String> mPropertyList;
	private final EasyQueryModel mQueryModel;
	private final JSONArray mJsonArray;
	private final Map<String, Integer> mPropertyToIndexMap;
	private final String m_IdAlias;

	public EasyJsonCursor(JSONArray array, String _idAlias) {
		this(array, _idAlias, null);
	}

	public EasyJsonCursor(JSONArray array, String _idAlias, EasyQueryModel model) {
		mPropertyList = new ArrayList<String>();
		mPropertyToIndexMap = new HashMap<String, Integer>();
		m_IdAlias = _idAlias;
		mQueryModel = model;
		mJsonArray = array;
		populateMethodList(array);
	}

	private String applyAlias(String columnName){
		if(_ID.equals(columnName)){
			if(m_IdAlias != null){
				return m_IdAlias;
			}
		}

		return columnName;
	}

	public String get_IdAlias(){
		return m_IdAlias;
	}

	@Override
	public byte[] getBlob(String name) {
		throw new UnsupportedOperationException("getBlob is not supported");
	}

	@Override
	public boolean getBoolean(String name) {
		try {
			return getCurrentJsonObject().getBoolean(applyAlias(name));
		} catch (JSONException e) {
			throw new EasyJsonException(e);
		}
	}

	@Override
	public int getColumnIndex(String name) {
		final String column = applyAlias(name);

		if(mPropertyToIndexMap.containsKey(column)){
			return mPropertyToIndexMap.get(column).intValue();
		} else {
			return -1;
		}
	}

	@Override
	public int getColumnIndexOrThrow(String name) {
		final String column = applyAlias(name);

		if(mPropertyToIndexMap.containsKey(column)){
			return mPropertyToIndexMap.get(column).intValue();
		} else {
			throw new IllegalArgumentException("There is no column named '" + column + "'");
		}
	}

	@Override
	public String getColumnName(int columnIndex) {
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

	public JSONObject getCurrentJsonObject(){
		return mJsonArray.optJSONObject(getPosition());
	}

	@Override
	public double getDouble(int column) {
		return getDouble(mPropertyList.get(column));
	}

	@Override
	public double getDouble(String name) {
		try {
			return getCurrentJsonObject().getDouble(applyAlias(name));
		} catch (JSONException e) {
			throw new EasyJsonException(e);
		}
	}

	@Override
	public float getFloat(int column) {
		return getFloat(mPropertyList.get(column));
	}

	@Override
	public float getFloat(String name) {
		try {
			return (float) getCurrentJsonObject().getDouble(applyAlias(name));
		} catch (JSONException e) {
			throw new EasyJsonException(e);
		}
	}

	@Override
	public int getInt(int column) {
		return getInt(mPropertyList.get(column));
	}

	@Override
	public int getInt(String name) {
		try {
			return getCurrentJsonObject().getInt(applyAlias(name));
		} catch (JSONException e) {
			throw new EasyJsonException(e);
		}
	}

	public JSONArray getJSONArray(int column) {
		return getJSONArray(mPropertyList.get(column));
	}

	public JSONArray getJSONArray(String name) {
		try {
			return getCurrentJsonObject().getJSONArray(applyAlias(name));
		} catch (JSONException e) {
			throw new EasyJsonException(e);
		}
	}

	public JSONObject getJSONObject(int column) {
		return getJSONObject(mPropertyList.get(column));
	}

	public JSONObject getJSONObject(String name) {
		try {
			return getCurrentJsonObject().getJSONObject(applyAlias(name));
		} catch (JSONException e) {
			throw new EasyJsonException(e);
		}
	}

	@Override
	public long getLong(int column) {
		return getLong(mPropertyList.get(column));
	}

	@Override
	public long getLong(String name) {
		try {
			return getCurrentJsonObject().getLong(applyAlias(name));
		} catch (JSONException e) {
			throw new EasyJsonException(e);
		}
	}

	@Override
	public EasyQueryModel getQueryModel() {
		return mQueryModel;
	}

	@Override
	public short getShort(int column) {
		return getShort(mPropertyList.get(column));
	}

	public short getShort(String name) {
		return (short) getInt(applyAlias(name));
	}

	@Override
	public String getString(int column) {
		return getString(mPropertyList.get(column));
	}

	@Override
	public String getString(String name) {
		try {
			return getCurrentJsonObject().getString(applyAlias(name));
		} catch (JSONException e) {
			throw new EasyJsonException(e);
		}
	}

	@Override
	public boolean isNull(int column) {
		return isNull(mPropertyList.get(column));
	}

	@Override
	public boolean isNull(String name) {
		return getCurrentJsonObject().isNull(applyAlias(name));
	}

	@Override
	public boolean optBoolean(String name) {
		return getCurrentJsonObject().optBoolean(applyAlias(name));
	}

	@Override
	public boolean optBoolean(String name, boolean fallback) {
		return getCurrentJsonObject().optBoolean(applyAlias(name), fallback);
	}

	@Override
	public Boolean optBooleanAsWrapperType(String name) {
		final String column = applyAlias(name);
		if(getCurrentJsonObject().has(column)){
			return optBoolean(column);
		} else {
			return null;
		}
	}

	@Override
	public double optDouble(String name) {
		return getCurrentJsonObject().optDouble(applyAlias(name));
	}

	@Override
	public double optDouble(String name, double fallback) {
		return getCurrentJsonObject().optDouble(applyAlias(name), fallback);
	}

	@Override
	public Double optDoubleAsWrapperType(String name) {
		final String column = applyAlias(name);
		if(getCurrentJsonObject().has(column)){
			return optDouble(column);
		} else {
			return null;
		}
	}

	@Override
	public float optFloat(String name) {
		return (float) getCurrentJsonObject().optDouble(applyAlias(name));
	}

	@Override
	public float optFloat(String name, float fallback) {
		return (float) getCurrentJsonObject().optDouble(applyAlias(name), fallback);
	}

	@Override
	public Float optFloatAsWrapperType(String name) {
		final String column = applyAlias(name);
		if(getCurrentJsonObject().has(column)){
			return optFloat(column);
		} else {
			return null;
		}
	}

	@Override
	public int optInt(String name) {
		return getCurrentJsonObject().optInt(applyAlias(name));
	}

	@Override
	public int optInt(String name, int fallback) {
		return getCurrentJsonObject().optInt(applyAlias(name), fallback);
	}

	@Override
	public Integer optIntAsWrapperType(String name) {
		if(getCurrentJsonObject().has(name)){
			return optInt(name);
		} else {
			return null;
		}
	}

	public JSONArray optJSONArray(int column) {
		return optJSONArray(mPropertyList.get(column));
	}

	public JSONArray optJSONArray(String name) {
		return getCurrentJsonObject().optJSONArray(applyAlias(name));
	}

	public JSONObject optJSONObject(int column) {
		return optJSONObject(mPropertyList.get(column));
	}

	public JSONObject optJSONObject(String name) {
		return getCurrentJsonObject().optJSONObject(applyAlias(name));
	}

	@Override
	public long optLong(String name) {
		return getCurrentJsonObject().optLong(applyAlias(name));
	}

	@Override
	public long optLong(String name, long fallback) {
		return getCurrentJsonObject().optLong(applyAlias(name), fallback);
	}

	@Override
	public Long optLongAsWrapperType(String name) {
		final String column = applyAlias(name);
		if(getCurrentJsonObject().has(column)){
			return optLong(column);
		} else {
			return null;
		}
	}

	@Override
	public String optString(String name) {
		return getCurrentJsonObject().optString(applyAlias(name));
	}

	@Override
	public String optString(String name, String fallback) {
		return getCurrentJsonObject().optString(applyAlias(name), fallback);
	}

	private void populateMethodList(JSONArray array){
	    int count = 0;
		final JSONObject obj = array.optJSONObject(0);

	    @SuppressWarnings("unchecked")
		final Iterator<String> keyIterator = obj.keys();
	    while (keyIterator.hasNext()){
	    	final String key = keyIterator.next();
	    	mPropertyList.add(key);
	    	mPropertyToIndexMap.put(key, count);
	    	count ++;
	    }
	}
}
