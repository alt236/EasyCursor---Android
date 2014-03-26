package uk.co.alt236.easycursor.sampleapp.test;

import java.util.ArrayList;
import java.util.List;

import uk.co.alt236.easycursor.sampleapp.database.DbSingleton;
import uk.co.alt236.easycursor.sampleapp.database.ExampleDatabase;
import uk.co.alt236.easycursor.sampleapp.util.StaticModelBuilder;
import uk.co.alt236.easycursor.sqlcursor.EasySqlCursor;
import uk.co.alt236.easycursor.sqlcursor.EasySqlQueryModel;
import android.util.Log;

public class EasySqlCursorFieldAccessTests extends CommonTestCases {
	private final String TAG = getClass().getName();
	private List<EasySqlQueryModel> mCombinedList;
	private ExampleDatabase mDb;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		final List<EasySqlQueryModel> listSelect = new ArrayList<EasySqlQueryModel>();
		final List<EasySqlQueryModel> listModel = new ArrayList<EasySqlQueryModel>();
		mCombinedList = new ArrayList<EasySqlQueryModel>();
		mDb = DbSingleton.getInstance(getContext());

		final List<EasySqlQueryModel> tempSelectList = new ArrayList<EasySqlQueryModel>();

		tempSelectList.add(StaticModelBuilder.getCompatQueryModel());
		tempSelectList.add(StaticModelBuilder.getCustomBuilderModel());
		tempSelectList.add(StaticModelBuilder.getDefaultSelectModel());

		listSelect.addAll(tempSelectList);
		for(final EasySqlQueryModel model : tempSelectList){
			final String json = model.toJson();
			listSelect.add(new EasySqlQueryModel(json));
		}

		final List<EasySqlQueryModel> tempRawList = new ArrayList<EasySqlQueryModel>();
		tempRawList.add(StaticModelBuilder.getRawQueryModel());

		listModel.addAll(tempRawList);
		for(final EasySqlQueryModel model : tempRawList){
			final String json = model.toJson();
			listModel.add(new EasySqlQueryModel(json));
		}

		mCombinedList.addAll(listModel);
		mCombinedList.addAll(listSelect);
	}

	public void testBooleanFieldParsing(){
		for(int i = 0; i < mCombinedList.size(); i++){
			Log.v(TAG, "testBooleanFieldParsing: " + i);
			final EasySqlCursor cursor = (EasySqlCursor) mCombinedList.get(i).execute(mDb.getReadableDatabase());

			testBooleanFieldParsing(cursor);

			cursor.close();
		}
	}

	public void testIntegerFieldParsing(){
		for(int i = 0; i < mCombinedList.size(); i++){
			Log.v(TAG, "testIntegerFieldParsing: " + i);
			final EasySqlCursor cursor = (EasySqlCursor) mCombinedList.get(i).execute(mDb.getReadableDatabase());

			testIntegerFieldParsing(cursor);

			cursor.close();
		}
	}

	public void testLongFieldParsing(){
		for(int i = 0; i < mCombinedList.size(); i++){
			Log.v(TAG, "testLongFieldParsing: " + i);
			final EasySqlCursor cursor = (EasySqlCursor) mCombinedList.get(i).execute(mDb.getReadableDatabase());

			testLongFieldParsing(cursor);

			cursor.close();
		}
	}

	public void testDoubleFieldParsing(){
		for(int i = 0; i < mCombinedList.size(); i++){
			Log.v(TAG, "testDoubleFieldParsing: " + i);
			final EasySqlCursor cursor = (EasySqlCursor) mCombinedList.get(i).execute(mDb.getReadableDatabase());

			testDoubleFieldParsing(cursor);

			cursor.close();
		}
	}

	public void testFloatFieldParsing(){
		for(int i = 0; i < mCombinedList.size(); i++){
			Log.v(TAG, "testLongFieldParsing: " + i);
			final EasySqlCursor cursor = (EasySqlCursor) mCombinedList.get(i).execute(mDb.getReadableDatabase());

			testFloatFieldParsing(cursor);

			cursor.close();
		}
	}

	public void testStringFieldParsing(){
		for(int i = 0; i < mCombinedList.size(); i++){
			Log.v(TAG, "testStringFieldParsing: " + i);
			final EasySqlCursor cursor = (EasySqlCursor) mCombinedList.get(i).execute(mDb.getReadableDatabase());

			testStringFieldParsing(cursor);

			cursor.close();
		}
	}
}
