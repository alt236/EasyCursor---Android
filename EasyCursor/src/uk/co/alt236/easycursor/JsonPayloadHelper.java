package uk.co.alt236.easycursor;

import org.json.JSONException;
import org.json.JSONObject;

class JsonPayloadHelper {
	private static final String ARRAY_COUNT_POSTFIX = "Count";
	
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
			object.put(key + ARRAY_COUNT_POSTFIX, value.length);
			final JSONObject obj = new JSONObject();
			
			int count = 0;
			for(String str : value){
				obj.put(String.valueOf(count), str);
				count += 1;
			}
			
			object.put(key, obj);
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
		final String[] res;
		final int count = payload.optInt(key + ARRAY_COUNT_POSTFIX, 0);
		final JSONObject obj = payload.optJSONObject(key);
		
		if(count == 0 || obj == null){
			res = new String[0];
		} else {
			res = new String[count];
			for(int i = 0; i < count; i++){
				res[i]= obj.optString(String.valueOf(i));	
			}
		}
		
		return res;
	}
}
