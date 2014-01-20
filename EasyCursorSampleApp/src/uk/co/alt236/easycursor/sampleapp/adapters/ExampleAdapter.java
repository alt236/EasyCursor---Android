package uk.co.alt236.easycursor.sampleapp.adapters;

import uk.co.alt236.easycursor.EasyCursor;
import uk.co.alt236.easycursor.sampleapp.R;
import android.content.Context;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ExampleAdapter extends SimpleCursorAdapter{
	private static final String NO_SUCH_COLUMN = "<no column>";
	private static final String HAS_COMPOSER = "Has composer";
	private static final String NO_COMPOSER = "No composer";
	
	@SuppressWarnings("deprecation")
	public ExampleAdapter(Context context, int layout, EasyCursor c, String[] from, int[] to) {
		super(context, layout, c, from, to);
	}

	
	@Override
	public EasyCursor getCursor(){
		return (EasyCursor) super.getCursor();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) { 
		final View view = super.getView(position, convertView, parent);
		final TextView composer = (TextView) view.findViewById(R.id.hasComposer);
		final TextView media = (TextView) view.findViewById(R.id.medium);
		final TextView track = (TextView) view.findViewById(R.id.track);
		
		media.setText(getCursor().optString("media", NO_SUCH_COLUMN));
		track.setText(getCursor().optString("track", NO_SUCH_COLUMN));
		
		
		final boolean composerPresent = getCursor().optBoolean("hascomposer");
		
		if(composerPresent){
			composer.setText(HAS_COMPOSER);
		} else {
			composer.setText(NO_COMPOSER);
		}
		
		return view;
	}
}
