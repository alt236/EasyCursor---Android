package uk.co.alt236.easycursor.sampleapp.database;

import org.json.JSONException;

import uk.co.alt236.easycursor.EasyCursor;
import uk.co.alt236.easycursor.sampleapp.util.Constants;
import uk.co.alt236.easycursor.sqlcursor.EasySqlQueryModel;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class ExampleDatabase extends SQLiteAssetHelper {
	private static final String DATABASE_NAME = "Chinook_Sqlite.db";
	private static final int DATABASE_VERSION = 1;
	
	private static final String RAW_QUERY = "SELECT track.trackId AS _id, artist.name AS artist, album.title AS album, track.name AS track, mediatype.name AS media, track.composer AS composer, (ifnull(track.composer, 0)>0) AS hascomposer"
			+ " FROM track"
			+ " LEFT OUTER JOIN album ON track.albumId = album.albumid"
			+ " LEFT OUTER JOIN artist ON artist.artistId = album.artistid "
			+ " LEFT OUTER JOIN mediatype ON track.mediatypeid = mediatype.mediatypeid "
			+ " WHERE media=?" + " ORDER BY artist, album, track, composer;";
	private static final String[] RAW_SQL_PARAMS = new String[] { "MPEG audio file" };
	
	public ExampleDatabase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public EasyCursor doRawQuery() {
		final EasySqlQueryModel model = new EasySqlQueryModel();
		model.setQueryParams(RAW_QUERY, RAW_SQL_PARAMS);
		return model.execute(getReadableDatabase());
	}

	public EasyCursor doSavedQuery(Context context) {
		final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
		final String json = settings.getString(Constants.PREFS_SAVED_QUERY, null);
		EasyCursor result;
		if (json == null) {
			result = null;
		} else {
			try {
				final EasySqlQueryModel model = EasySqlQueryModel.getInstance(json);
				result = model.execute(getReadableDatabase());
			} catch (JSONException e) {
				e.printStackTrace();
				result = null;
			}
		}

		return result;
	}
}