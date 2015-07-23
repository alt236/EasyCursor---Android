package uk.co.alt236.easycursor.jsoncursor;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import uk.co.alt236.easycursor.internal.FieldAccessor;

/**
 *
 */
class JsonFieldAccessor implements FieldAccessor {
    private final List<String> mPropertyList;
    private final Map<String, Integer> mPropertyToIndexMap;

    public JsonFieldAccessor(final JSONArray array) {
        mPropertyList = new ArrayList<>();
        mPropertyToIndexMap = new HashMap<>();
        populateMethodList(array);
    }

    @Override
    public int getFieldIndexByName(final String name) {
        if (mPropertyToIndexMap.containsKey(name)) {
            return mPropertyToIndexMap.get(name);
        } else {
            return -1;
        }
    }

    @Override
    public String getFieldNameByIndex(final int index) {
        return mPropertyList.get(index);
    }

    @Override
    public String[] getFieldNames() {
        return mPropertyList.toArray(new String[mPropertyList.size()]);
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
