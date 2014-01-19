package uk.co.alt236.easycursor.sampleapp.database.loaders;

import uk.co.alt236.easycursor.EasyCursor;
import uk.co.alt236.easycursor.sampleapp.database.DbSingleton;
import uk.co.alt236.easycursor.sampleapp.database.ExampleDatabase;
import android.content.Context;
import android.util.Log;

import com.commonsware.cwac.loaderex.acl.AbstractCursorLoader;

public class DatabaseLoader extends AbstractCursorLoader {	
	public final static int QUERY_TYPE_ANDROID_DEFAULT = 1;
	public final static int QUERY_TYPE_EASYCURSOR_DEFAULT = 2;
	public final static int QUERY_TYPE_EASYCURSOR_BUILDER = 3;
	public final static int QUERY_TYPE_EASYCURSOR_RAW = 4;
	public final static int QUERY_TYPE_EASYCURSOR_SAVED = 5;
	
	private final int mType;

	public DatabaseLoader(final Context context, final int type) {
		super(context);
		
		mType = type;
	}

	@Override
	protected EasyCursor buildCursor() {
		Log.d("LOADER", "^ About to execute query type: " + mType);
		
		final ExampleDatabase db = DbSingleton.getInstance(getContext());
		
		switch(mType){
		case QUERY_TYPE_ANDROID_DEFAULT:
			break;
		case QUERY_TYPE_EASYCURSOR_DEFAULT:
			break;
		case QUERY_TYPE_EASYCURSOR_BUILDER:
			break;
		case QUERY_TYPE_EASYCURSOR_RAW:
			break;
		case QUERY_TYPE_EASYCURSOR_SAVED:
			break;
		}
		
		return null;
	}
}