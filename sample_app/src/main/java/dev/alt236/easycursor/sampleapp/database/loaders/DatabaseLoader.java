/*
 * ***************************************************************************
 * Copyright 2024 Alexandros Schillings
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

package dev.alt236.easycursor.sampleapp.database.loaders;

import android.content.Context;
import android.util.Log;

import com.commonsware.cwac.loaderex.acl.AbstractCursorLoader;

import dev.alt236.easycursor.EasyCursor;
import dev.alt236.easycursor.sampleapp.database.DbSingleton;
import dev.alt236.easycursor.sampleapp.database.ExampleDatabase;

public class DatabaseLoader extends AbstractCursorLoader {
    public final static int QUERY_TYPE_ANDROID_DEFAULT = 0;
    public final static int QUERY_TYPE_EASYCURSOR_DEFAULT_SELECT = 1;
    public final static int QUERY_TYPE_EASYCURSOR_DEFAULT_RAW = 2;
    public final static int QUERY_TYPE_EASYCURSOR_CUSTOM_BUILDER = 3;
    public final static int QUERY_TYPE_EASYCURSOR_COMPATIBILITY = 4;
    public final static int QUERY_TYPE_EASYCURSOR_OBJECT_CURSOR = 5;
    public final static int QUERY_TYPE_EASYCURSOR_SAVED = 6;
    private final int mType;

    public DatabaseLoader(final Context context, final int type) {
        super(context);

        mType = type;
    }

    @Override
    protected EasyCursor buildCursor() {
        Log.d("LOADER", "^ About to execute query type: " + mType);

        final ExampleDatabase db = DbSingleton.getInstance(getContext());
        final EasyCursor result;

        switch (mType) {
            case QUERY_TYPE_ANDROID_DEFAULT:
                result = db.doAndroidDefaultQuery();
                break;
            case QUERY_TYPE_EASYCURSOR_DEFAULT_SELECT:
                result = db.doEasySelectQuery();
                break;
            case QUERY_TYPE_EASYCURSOR_DEFAULT_RAW:
                result = db.doEasyRawQuery();
                break;
            case QUERY_TYPE_EASYCURSOR_CUSTOM_BUILDER:
                result = db.doEasyCustomBuilderQuery();
                break;
            case QUERY_TYPE_EASYCURSOR_COMPATIBILITY:
                result = db.doEasyCursorCompatQuery();
                break;
            case QUERY_TYPE_EASYCURSOR_SAVED:
                result = db.doSavedQuery(getContext());
                break;
            case QUERY_TYPE_EASYCURSOR_OBJECT_CURSOR:
                result = db.doObjectCursorQuery();
                break;
            default:
                result = null;
        }

        return result;
    }
}