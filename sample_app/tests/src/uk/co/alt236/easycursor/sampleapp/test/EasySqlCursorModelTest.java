package uk.co.alt236.easycursor.sampleapp.test;

import android.util.Log;

import junit.framework.Assert;

import java.util.ArrayList;
import java.util.List;

import uk.co.alt236.easycursor.sampleapp.database.DbSingleton;
import uk.co.alt236.easycursor.sampleapp.database.ExampleDatabase;
import uk.co.alt236.easycursor.sampleapp.util.StaticModelBuilder;
import uk.co.alt236.easycursor.sqlcursor.EasySqlCursor;
import uk.co.alt236.easycursor.sqlcursor.querymodels.SqlQueryModel;

public class EasySqlCursorModelTest extends android.test.AndroidTestCase {
    private final String TAG = getClass().getName();

    private List<SqlQueryModel> mSelectQueryModelList;
    private List<SqlQueryModel> mRawQueryModelList;
    private List<SqlQueryModel> mCombinedList;

    private ExampleDatabase mDb;


    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mSelectQueryModelList = new ArrayList<SqlQueryModel>();
        mRawQueryModelList = new ArrayList<SqlQueryModel>();
        mCombinedList = new ArrayList<SqlQueryModel>();
        mDb = DbSingleton.getInstance(getContext());

        final List<SqlQueryModel> tempSelectList = new ArrayList<SqlQueryModel>();

        tempSelectList.add(StaticModelBuilder.getCompatQueryModel());
        tempSelectList.add(StaticModelBuilder.getCustomBuilderModel());
        tempSelectList.add(StaticModelBuilder.getDefaultSelectModel());

        mSelectQueryModelList.addAll(tempSelectList);
        for (final SqlQueryModel model : tempSelectList) {
            final String json = model.toJson();
            mSelectQueryModelList.add(new SqlQueryModel(json));
        }

        final List<SqlQueryModel> tempRawList = new ArrayList<SqlQueryModel>();
        tempRawList.add(StaticModelBuilder.getRawQueryModel());

        mRawQueryModelList.addAll(tempRawList);
        for (final SqlQueryModel model : tempRawList) {
            final String json = model.toJson();
            mRawQueryModelList.add(new SqlQueryModel(json));
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

    public void testModelResults() {
        EasySqlCursor cursor = (EasySqlCursor) mCombinedList.get(0).execute(mDb.getReadableDatabase());
        final int res = cursor.getCount();
        cursor.close();

        for (int i = 1; i < mCombinedList.size(); i++) {
            cursor = (EasySqlCursor) mCombinedList.get(i).execute(mDb.getReadableDatabase());
            final int tmp = cursor.getCount();
            cursor.close();
            Log.v(TAG, "testModelResults: " + i + ": " + tmp);
            Assert.assertEquals("Comparing first model results with item # " + i, res, tmp);
        }
    }

    public void testRawModelEquality() {
        final SqlQueryModel first = mRawQueryModelList.get(0);
        for (int i = 1; i < mRawQueryModelList.size(); i++) {
            Log.v(TAG, "testSelectModelEquality: " + i);
            Assert.assertEquals("Comparing first model with item # " + i, first, mRawQueryModelList.get(i));
        }
    }

    public void testSelectModelEquality() {
        final SqlQueryModel first = mSelectQueryModelList.get(0);
        for (int i = 1; i < mSelectQueryModelList.size(); i++) {
            Log.v(TAG, "testSelectModelEquality: " + i);
            Assert.assertEquals("Comparing first model with item # " + i, first, mSelectQueryModelList.get(i));
        }
    }
}
