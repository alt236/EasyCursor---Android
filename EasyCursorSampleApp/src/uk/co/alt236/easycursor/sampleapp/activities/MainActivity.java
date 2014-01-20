/*******************************************************************************
 * Copyright 2013 Alexandros Schillings
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package uk.co.alt236.easycursor.sampleapp.activities;

import org.json.JSONException;

import uk.co.alt236.easycursor.EasyCursor;
import uk.co.alt236.easycursor.sampleapp.R;
import uk.co.alt236.easycursor.sampleapp.adapters.ExampleAdapter;
import uk.co.alt236.easycursor.sampleapp.database.loaders.DatabaseLoader;
import uk.co.alt236.easycursor.sampleapp.util.Constants;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements LoaderManager.LoaderCallbacks<Cursor> {
	private final static String EXTRA_QUERY_TYPE = "EXTRA_QUERY_TYPE";
	private final int LOADER_ID = 1;

	private SimpleCursorAdapter mAdapter;
	private Button mSaveQueryButton;
	private DatabaseLoader mLoader=null;
	private ListView mListView;
	private Spinner mSpinner;

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mListView = (ListView) findViewById(android.R.id.list);
		mListView.setEmptyView(findViewById(android.R.id.empty));
		mSaveQueryButton = (Button) findViewById(R.id.buttonSave);
		mSpinner = (Spinner) findViewById(R.id.spinner);


		final String[] from = new String[]{"artist", "album"};
		final int[] to = new int[]{R.id.artist, R.id.album};

		mAdapter = new ExampleAdapter(this, R.layout.list_item, (EasyCursor) null, from, to);
		mListView.setAdapter(mAdapter);
		mListView.setFastScrollEnabled(true);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		mLoader = new DatabaseLoader(this, arg1.getInt(EXTRA_QUERY_TYPE, -2));
		return mLoader;
	}

	public void onExecuteClick(View v){
		final Bundle bundle = generateLoaderBundle();

		if(bundle == null ){
			Toast.makeText(this, "Could not decide what query to run...", Toast.LENGTH_SHORT).show();
		} else {
			getSupportLoaderManager().restartLoader(
					LOADER_ID,
					bundle,
					this);
		}
	}

	private Bundle generateLoaderBundle(){
		final Bundle bundle;
		final String[] array = getResources().getStringArray(R.array.query_types);
		final String selectedQuery = mSpinner.getSelectedItem().toString();
		int res = -1;

		for(int i = 0; i < array.length; i++){
			if(selectedQuery.equals(array[i])){
				res = i;
				break;
			}
		}

		if(res == -1){
			bundle = null;
		} else {
			bundle = new Bundle();
			bundle.putInt(EXTRA_QUERY_TYPE, res);
		}

		return bundle;
	}


	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		mAdapter.changeCursor(null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
		if(cursor != null && cursor.getCount() != 0 && cursor instanceof EasyCursor){
			final EasyCursor eCursor = (EasyCursor) cursor;
			if(eCursor.getQueryModel() != null){
				mSaveQueryButton.setVisibility(View.VISIBLE);
			} else {
				mSaveQueryButton.setVisibility(View.INVISIBLE);
			}
		} else {
			mSaveQueryButton.setVisibility(View.INVISIBLE);
		}

		mAdapter.changeCursor(cursor);
	}

	public void onSaveQueryClick(View v){
		if(mAdapter.getCursor() instanceof EasyCursor){
			final EasyCursor cursor = (EasyCursor) mAdapter.getCursor();
			try {
				final String json = cursor.getQueryModel().toJson();
				final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
				final SharedPreferences.Editor editor = settings.edit();
				editor.putString(Constants.PREFS_SAVED_QUERY, json);

				// Commit the edits!
				editor.apply();
				Toast.makeText(this, "Query Saved Succesfully (size= "+json.length()+")!", Toast.LENGTH_SHORT).show();
			} catch (JSONException e) {
				Toast.makeText(this, "Error Converting QueryModel to JSON...", Toast.LENGTH_SHORT).show();
			}
		}
	}
}
