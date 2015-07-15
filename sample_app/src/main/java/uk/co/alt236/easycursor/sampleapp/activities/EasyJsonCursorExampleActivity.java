/**
 * ****************************************************************************
 * Copyright 2013 Alexandros Schillings
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ****************************************************************************
 */
package uk.co.alt236.easycursor.sampleapp.activities;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import uk.co.alt236.easycursor.EasyCursor;
import uk.co.alt236.easycursor.sampleapp.R;
import uk.co.alt236.easycursor.sampleapp.database.loaders.JsonLoader;

public class EasyJsonCursorExampleActivity extends FragmentActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private final int LOADER_ID = 1;

    private SimpleCursorAdapter mAdapter;
    private Button mSaveQueryButton;
    private Loader<Cursor> mLoader = null;
    private ListView mListView;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cursor_selection);
        mListView = (ListView) findViewById(android.R.id.list);
        mListView.setEmptyView(findViewById(android.R.id.empty));
        mSaveQueryButton = (Button) findViewById(R.id.buttonSave);
        findViewById(R.id.spinner_container).setVisibility(View.GONE);

        final String[] from = new String[]{"name", "gender", "age", "guid", "about"};
        final int[] to = new int[]{R.id.name, R.id.gender, R.id.age, R.id.guid, R.id.about};

        mAdapter = new SimpleCursorAdapter(this, R.layout.list_item_json, null, from, to, 0);
        mListView.setAdapter(mAdapter);
        mListView.setFastScrollEnabled(true);
    }

    @Override
    public Loader<Cursor> onCreateLoader(final int arg0, final Bundle arg1) {
        mLoader = new JsonLoader(this);
        return mLoader;
    }

    public void onExecuteClick(final View v) {
        getSupportLoaderManager().restartLoader(
                LOADER_ID,
                null,
                this);
    }

    @Override
    public void onLoadFinished(final Loader<Cursor> arg0, final Cursor cursor) {
        if (cursor != null && cursor.getCount() != 0 && cursor instanceof EasyCursor) {
            final EasyCursor eCursor = (EasyCursor) cursor;
            if (eCursor.getQueryModel() != null) {
                mSaveQueryButton.setVisibility(View.VISIBLE);
            } else {
                mSaveQueryButton.setVisibility(View.INVISIBLE);
            }
        } else {
            mSaveQueryButton.setVisibility(View.INVISIBLE);
        }

        mAdapter.changeCursor(cursor);
    }

    @Override
    public void onLoaderReset(final Loader<Cursor> arg0) {
        mAdapter.changeCursor(null);
    }
}
