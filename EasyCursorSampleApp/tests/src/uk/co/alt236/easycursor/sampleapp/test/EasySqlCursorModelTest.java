package uk.co.alt236.easycursor.sampleapp.test;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import uk.co.alt236.easycursor.sampleapp.database.DbSingleton;
import uk.co.alt236.easycursor.sampleapp.database.ExampleDatabase;
import uk.co.alt236.easycursor.sampleapp.util.StaticModelBuilder;
import uk.co.alt236.easycursor.sqlcursor.EasySqlCursor;
import uk.co.alt236.easycursor.sqlcursor.EasySqlQueryModel;
import android.util.Log;

public class EasySqlCursorModelTest extends android.test.AndroidTestCase {
	private final String TAG = getClass().getName();

	private List<EasySqlQueryModel> mSelectQueryModelList;
	private List<EasySqlQueryModel> mRawQueryModelList;
	private List<EasySqlQueryModel> mCombinedList;

	private ExampleDatabase mDb;


	@Override
	protected void setUp() throws Exception {
		super.setUp();
		mSelectQueryModelList = new ArrayList<EasySqlQueryModel>();
		mRawQueryModelList = new ArrayList<EasySqlQueryModel>();
		mCombinedList = new ArrayList<EasySqlQueryModel>();
		mDb = DbSingleton.getInstance(getContext());

		final List<EasySqlQueryModel> tempSelectList = new ArrayList<EasySqlQueryModel>();

		tempSelectList.add(StaticModelBuilder.getCompatQueryModel());
		tempSelectList.add(StaticModelBuilder.getCustomBuilderModel());
		tempSelectList.add(StaticModelBuilder.getDefaultSelectModel());

		mSelectQueryModelList.addAll(tempSelectList);
		for(final EasySqlQueryModel model : tempSelectList){
			final String json = model.toJson();
			mSelectQueryModelList.add(new EasySqlQueryModel(json));
		}

		final List<EasySqlQueryModel> tempRawList = new ArrayList<EasySqlQueryModel>();
		tempRawList.add(StaticModelBuilder.getRawQueryModel());

		mRawQueryModelList.addAll(tempRawList);
		for(final EasySqlQueryModel model : tempRawList){
			final String json = model.toJson();
			mRawQueryModelList.add(new EasySqlQueryModel(json));
		}

		mCombinedList.addAll(mRawQueryModelList);
		mCombinedList.addAll(mSelectQueryModelList);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		mSelectQueryModelList = null;
		mRawQueryModelList = null;
		mCombinedList = null;
	}



	public void testSelectModelEquality(){
		final EasySqlQueryModel first = mSelectQueryModelList.get(0);
		for(int i = 1; i < mSelectQueryModelList.size(); i++){
			Log.v(TAG, "testSelectModelEquality: " + i);
			Assert.assertEquals("Comparing first model with item # " + i, first, mSelectQueryModelList.get(i));
		}
	}

	public void testRawModelEquality(){
		final EasySqlQueryModel first = mRawQueryModelList.get(0);
		for(int i = 1; i < mRawQueryModelList.size(); i++){
			Log.v(TAG, "testSelectModelEquality: " + i);
			Assert.assertEquals("Comparing first model with item # " + i, first, mRawQueryModelList.get(i));
		}
	}

	public void testModelResults(){
		EasySqlCursor cursor = (EasySqlCursor) mCombinedList.get(0).execute(mDb.getReadableDatabase());
		final int res = cursor.getCount();
		cursor.close();

		for(int i = 1; i < mCombinedList.size(); i++){
			cursor = (EasySqlCursor) mCombinedList.get(i).execute(mDb.getReadableDatabase());
			final int tmp = cursor.getCount();
			cursor.close();
			Log.v(TAG, "testModelResults: " + i + ": " + tmp);
			Assert.assertEquals("Comparing first model results with item # " + i, res, tmp);
		}
	}
}
