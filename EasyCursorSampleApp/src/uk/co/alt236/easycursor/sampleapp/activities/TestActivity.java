package uk.co.alt236.easycursor.sampleapp.activities;

import uk.co.alt236.easycursor.sampleapp.R;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

public class TestActivity extends FragmentActivity{
	private TextView mTextView;

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		mTextView = (TextView) findViewById(R.id.textview);
	}
	
	
}
