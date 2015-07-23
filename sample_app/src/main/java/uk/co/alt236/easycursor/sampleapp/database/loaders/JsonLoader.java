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

package uk.co.alt236.easycursor.sampleapp.database.loaders;

import android.content.Context;
import android.database.Cursor;

import com.commonsware.cwac.loaderex.acl.AbstractCursorLoader;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;

import uk.co.alt236.easycursor.EasyCursor;
import uk.co.alt236.easycursor.jsoncursor.EasyJsonCursor;

public class JsonLoader extends AbstractCursorLoader {
    private static final String UTF_8 = "UTF-8";
    private static final String DATA_SAMPLE_JSON_JSON = "data/sample_json.json";


    public JsonLoader(final Context context) {
        super(context);
    }

    @Override
    protected Cursor buildCursor() {
        EasyCursor cursor;
        try {

            // the Json data we have do not have an "_id" field, so we will alias "_id" as "id"

            cursor = new EasyJsonCursor(
                    new JSONArray(loadAssetsFileAsString(DATA_SAMPLE_JSON_JSON)),
                    "id");
        } catch (final JSONException e) {
            e.printStackTrace();
            cursor = null;
        }

        return cursor;
    }

    public String loadAssetsFileAsString(final String path) {
        final String json;
        try {
            final InputStream is = getContext().getAssets().open(path);
            final int size = is.available();
            final byte[] buffer = new byte[size];

            is.read(buffer);
            is.close();
            json = new String(buffer, UTF_8);
        } catch (final IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
