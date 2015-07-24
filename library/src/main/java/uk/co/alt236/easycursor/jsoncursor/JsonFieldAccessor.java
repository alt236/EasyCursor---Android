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
