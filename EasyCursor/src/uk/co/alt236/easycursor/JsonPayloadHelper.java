package uk.co.alt236.easycursor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class JsonPayloadHelper {

	public static void add(final JSONObject object, final String key, final Boolean value) throws JSONException{
		if(value != null){
			object.put(key, value);
		}
	}

	public static void add(final JSONObject object, final String key, final Double value) throws JSONException{
		if(value != null){
			object.put(key, value);
		}
	}

	public static void add(final JSONObject object, final String key, final Integer value) throws JSONException{
		if(value != null){
			object.put(key, value);
		}
	}

	public static void add(final JSONObject object, final String key, final Long value) throws JSONException{
		if(value != null){
			object.put(key, value);
		}
	}

	public static void add(final JSONObject object, final String key, final Object value) throws JSONException{
		if(value != null){
			object.put(key, value);
		}
	}

	public static void add(final JSONObject object, final String key, final String value) throws JSONException{
		if(value != null){
			object.put(key, value);
		}
	}

	public static void add(final JSONObject object, final String key, final String[] value) throws JSONException{
		if(value != null && value.length > 0){
			object.put(key, value);
		}
	}


	public static boolean getBoolean(final JSONObject object, final String key){
		return object.optBoolean(key, false);
	}

	public static int getInt(JSONObject object, String key) {
		return object.optInt(key, 0);
	}

	public static String getString(final JSONObject object, final String key){
		final String res = object.optString(key, null);
		if(key != null && key.equalsIgnoreCase("null")){
			return null;
		} else {
			return res;
		}
	}

	public static String[] getStringArray(JSONObject payload, String key) {
		final JSONArray arr = payload.optJSONArray(key);
		final String[] res;

		if(arr != null && arr.length() > 0){
			final int count = arr.length();
			res = new String[count];

			for(int i = 0; i < count; i++){
				res[i] = arr.optString(i);
			}
		} else {
			res = new String[0];
		}

		return res;
	}
}
