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
	private final EasyQueryModel mQueryModel;
	private final JSONArray mJsonArray;
	private List<String> mPropertyList;
	private final Map<String, Integer> mPropertyToIndexMap;

	public EasyJsonCursor(JSONArray array) {
		this(array, null);
	}

	public EasyJsonCursor(JSONArray array, EasyQueryModel model) {
		mPropertyList = new ArrayList<String>();
		mPropertyToIndexMap = new HashMap<String, Integer>();

		mQueryModel = model;
		mJsonArray = array;
		populateMethodList(array);
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

	@Override
	public byte[] getBlob(String name) {
		throw new UnsupportedOperationException("getBlob is not supported");
	}

	@Override
	public boolean getBoolean(String name) {
		try {
			return getCurrentJsonObject().getBoolean(name);
		} catch (JSONException e) {
			throw new EasyJsonException(e);
		}
	}

	@Override
	public int getColumnIndex(String columnName) {
		if(mPropertyToIndexMap.containsKey(columnName)){
			return mPropertyToIndexMap.get(columnName).intValue();
		} else {
			return -1;
		}
	}

	@Override
	public int getColumnIndexOrThrow(String columnName) {
		if(mPropertyToIndexMap.containsKey(columnName)){
			return mPropertyToIndexMap.get(columnName).intValue();
		} else {
			throw new IllegalArgumentException("There is no column named '" + columnName + "'");
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
			return getCurrentJsonObject().getDouble(name);
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
			return (float) getCurrentJsonObject().getDouble(name);
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
			return getCurrentJsonObject().getInt(name);
		} catch (JSONException e) {
			throw new EasyJsonException(e);
		}
	}

	public JSONArray getJSONArray(String name) {
		try {
			return getCurrentJsonObject().getJSONArray(name);
		} catch (JSONException e) {
			throw new EasyJsonException(e);
		}
	}

	public JSONObject getJSONObject(String name) {
		try {
			return getCurrentJsonObject().getJSONObject(name);
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
			return getCurrentJsonObject().getLong(name);
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
		return (short) getInt(name);
	}

	@Override
	public String getString(int column) {
		return getString(mPropertyList.get(column));
	}

	@Override
	public String getString(String name) {
		try {
			return getCurrentJsonObject().getString(name);
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
		return getCurrentJsonObject().isNull(name);
	}

	@Override
	public boolean optBoolean(String name) {
		return getCurrentJsonObject().optBoolean(name);
	}

	@Override
	public boolean optBoolean(String name, boolean fallback) {
		return getCurrentJsonObject().optBoolean(name, fallback);
	}

	@Override
	public Boolean optBooleanAsWrapperType(String name) {
		if(getCurrentJsonObject().has(name)){
			return optBoolean(name);
		} else {
			return null;
		}
	}

	@Override
	public double optDouble(String name) {
		return getCurrentJsonObject().optDouble(name);
	}

	@Override
	public double optDouble(String name, double fallback) {
		return getCurrentJsonObject().optDouble(name, fallback);
	}

	@Override
	public Double optDoubleAsWrapperType(String name) {
		if(getCurrentJsonObject().has(name)){
			return optDouble(name);
		} else {
			return null;
		}
	}

	@Override
	public float optFloat(String name) {
		return (float) getCurrentJsonObject().optDouble(name);
	}

	@Override
	public float optFloat(String name, float fallback) {
		return (float) getCurrentJsonObject().optDouble(name, fallback);
	}

	@Override
	public Float optFloatAsWrapperType(String name) {
		if(getCurrentJsonObject().has(name)){
			return optFloat(name);
		} else {
			return null;
		}
	}

	@Override
	public int optInt(String name) {
		return getCurrentJsonObject().optInt(name);
	}

	@Override
	public int optInt(String name, int fallback) {
		return getCurrentJsonObject().optInt(name, fallback);
	}

	@Override
	public Integer optIntAsWrapperType(String name) {
		if(getCurrentJsonObject().has(name)){
			return optInt(name);
		} else {
			return null;
		}
	}

	public JSONArray optJSONArray(String name) {
		return getCurrentJsonObject().optJSONArray(name);
	}

	public JSONObject optJSONObject(String name) {
		return getCurrentJsonObject().optJSONObject(name);
	}

	@Override
	public long optLong(String name) {
		return getCurrentJsonObject().optLong(name);
	}

	@Override
	public long optLong(String name, long fallback) {
		return getCurrentJsonObject().optLong(name, fallback);
	}

	@Override
	public Long optLongAsWrapperType(String name) {
		if(getCurrentJsonObject().has(name)){
			return optLong(name);
		} else {
			return null;
		}
	}

	@Override
	public String optString(String name) {
		return getCurrentJsonObject().optString(name);
	}

	@Override
	public String optString(String name, String fallback) {
		return getCurrentJsonObject().optString(name, fallback);
	}

}