package uk.co.alt236.easycursor.sampleapp.database.loaders;

import uk.co.alt236.easycursor.EasyCursor;
import uk.co.alt236.easycursor.sampleapp.database.DbSingleton;
import uk.co.alt236.easycursor.sampleapp.database.ExampleDatabase;
import android.content.Context;
import android.util.Log;

import com.commonsware.cwac.loaderex.acl.AbstractCursorLoader;

public class DatabaseLoader extends AbstractCursorLoader {
	public final static int QUERY_TYPE_ANDROID_DEFAULT = 0;
	public final static int QUERY_TYPE_EASYCURSOR_DEFAULT = 1;
	public final static int QUERY_TYPE_EASYCURSOR_BUILDER = 2;
	public final static int QUERY_TYPE_EASYCURSOR_RAW = 3;
	public final static int QUERY_TYPE_EASYCURSOR_SAVED = 4;

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

		switch(mType){
		case QUERY_TYPE_ANDROID_DEFAULT:
			result = db.doAndroidDefaultQuery();
			break;
		case QUERY_TYPE_EASYCURSOR_DEFAULT:
			result = db.doEasyCursorDefaultQuery();
			break;
		case QUERY_TYPE_EASYCURSOR_BUILDER:
			result = db.doBuilderQuery();
			break;
		case QUERY_TYPE_EASYCURSOR_RAW:
			result = db.doRawQuery();
			break;
		case QUERY_TYPE_EASYCURSOR_SAVED:
			result = db.doSavedQuery(getContext());
			break;
		default:
			result = null;
		}

		return result;
	}
}