package uk.co.alt236.easycursor;

import java.util.Arrays;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.Build;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class EasyQueryModel {
	public static final int TYPE_UNKNOWN = 0;
	public static final int TYPE_MANAGED = 1;
	public static final int TYPE_RAW = 2;

	@SerializedName("queryType")
	private int mQueryType = TYPE_UNKNOWN;
	
	//
	// Raw Query
	//
	@SerializedName("rawSql")
	private String mRawSql;
	
	//
	// Managed Query
	//
	@SerializedName("distinct")
	private boolean mDistinct;

	@SerializedName("strict")
	private boolean mStrict;

	@SerializedName("tables")
	private String mTables;

	@SerializedName("projectionIn")
	private String[] mProjectionIn;

	@SerializedName("selectionArgs")
	private String[] mSelectionArgs;

	@SerializedName("selection")
	private String mSelection;

	@SerializedName("groupBy")
	private String mGroupBy;

	@SerializedName("having")
	private String mHaving; 

	@SerializedName("sortOrder")
	private String mSortOrder;

	@SerializedName("limit")
	private String mLimit;

	public EasyCursor execute(final SQLiteDatabase db){
		final int queryType = mQueryType;
		final Cursor cursor;

		switch (queryType) {
		case TYPE_MANAGED:
			final SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
			builder.setTables(getTables());
			builder.setDistinct(isDistinct());

			if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH){
				builder.setStrict(isStrict());
			}

			cursor = builder.query(db, mProjectionIn, mSelection, mSelectionArgs, mGroupBy, mHaving, mSortOrder, mLimit);
			break;
		case TYPE_RAW:
			cursor = db.rawQuery(mRawSql, mSelectionArgs);
			break;
		default:
			throw new IllegalStateException("Attempted to Generate an EasyCursor using an unknown query type: " + queryType);
		}

		cursor.moveToFirst();
		return new EasyCursor(cursor, this);
	}

	public String getGroupBy() {
		return mGroupBy;
	}

	public String getHaving() {
		return mHaving;
	}

	public String getLimit() {
		return mLimit;
	}

	public String[] getProjectionIn() {
		return mProjectionIn;
	}

	public int getQueryType() {
		return mQueryType;
	}

	public String getRawSql() {
		return mRawSql;
	}

	public String getSelection() {
		return mSelection;
	}

	public String[] getSelectionArgs() {
		return mSelectionArgs;
	}

	public String getSortOrder() {
		return mSortOrder;
	}

	public String getTables(){
		return mTables;
	}

	public boolean isDistinct() {
		return mDistinct;
	}

	public boolean isStrict() {
		return mStrict;
	}

    /**
     * Mark the query as DISTINCT.
     *
     * @param distinct if true the query is DISTINCT, otherwise it isn't
     */
	public void setDistinct(boolean value){
		mDistinct = value;
	}

    /**
	 * Sets the query parameters.
	 *
	 * Will throw an IllegalStateExcetion if one tries to set
	 * the parameters more than once.
     *
     * @param sql the SQL query. The SQL string must not be ; terminated
     * @param selectionArgs You may include ?s in where clause in the query,
     *     which will be replaced by the values from selectionArgs. The
     *     values will be bound as Strings.     
     */
	public void setQueryParams(final String rawSql, final String[] selectionArgs) {
		if(mQueryType != TYPE_UNKNOWN){
			throw new IllegalStateException("A Model file's query parameters can only be set once!");
		}
		
		mQueryType = TYPE_RAW;
		mSelectionArgs = selectionArgs;
		mRawSql = rawSql;
	}

	/**
	 * Sets the query parameters.
	 *
	 * Will throw an IllegalStateExcetion if one tries to set
	 * the parameters more than once.
	 * 
	 * @param projectionIn A list of which columns to return. Passing
	 *   null will return all columns, which is discouraged to prevent
	 *   reading data from storage that isn't going to be used.
	 * @param selection A filter declaring which rows to return,
	 *   formatted as an SQL WHERE clause (excluding the WHERE
	 *   itself). Passing null will return all rows for the given URL.
	 * @param selectionArgs You may include ?s in selection, which
	 *   will be replaced by the values from selectionArgs, in order
	 *   that they appear in the selection. The values will be bound
	 *   as Strings.
	 * @param sortOrder How to order the rows, formatted as an SQL
	 *   ORDER BY clause (excluding the ORDER BY itself). Passing null
	 *   will use the default sort order, which may be unordered.
	 */
	public void setQueryParams(String[] projectionIn, String selection, String[] selectionArgs, String sortOrder) {
		setQueryParams(projectionIn, selection, selectionArgs, null, null, sortOrder, null);
	}

	/**
	 * Sets the query parameters.
	 *
	 * Will throw an IllegalStateExcetion if one tries to set
	 * the parameters more than once.
	 *
	 * @param projectionIn A list of which columns to return. Passing
	 *   null will return all columns, which is discouraged to prevent
	 *   reading data from storage that isn't going to be used.
	 * @param selection A filter declaring which rows to return,
	 *   formatted as an SQL WHERE clause (excluding the WHERE
	 *   itself). Passing null will return all rows for the given URL.
	 * @param selectionArgs You may include ?s in selection, which
	 *   will be replaced by the values from selectionArgs, in order
	 *   that they appear in the selection. The values will be bound
	 *   as Strings.
	 * @param groupBy A filter declaring how to group rows, formatted
	 *   as an SQL GROUP BY clause (excluding the GROUP BY
	 *   itself). Passing null will cause the rows to not be grouped.
	 * @param having A filter declare which row groups to include in
	 *   the cursor, if row grouping is being used, formatted as an
	 *   SQL HAVING clause (excluding the HAVING itself).  Passing
	 *   null will cause all row groups to be included, and is
	 *   required when row grouping is not being used.
	 * @param sortOrder How to order the rows, formatted as an SQL
	 *   ORDER BY clause (excluding the ORDER BY itself). Passing null
	 *   will use the default sort order, which may be unordered.
	 */
	public void setQueryParams(String[] projectionIn, String selection, String[] selectionArgs, String groupBy, String having, String sortOrder) {
		setQueryParams(projectionIn, selection, selectionArgs, groupBy, having, sortOrder, null);
	}

	/**
	 * Sets the query parameters.
	 *
	 * Will throw an IllegalStateExcetion if one tries to set
	 * the parameters more than once.
	 *
	 * @param projectionIn A list of which columns to return. Passing
	 *   null will return all columns, which is discouraged to prevent
	 *   reading data from storage that isn't going to be used.
	 * @param selection A filter declaring which rows to return,
	 *   formatted as an SQL WHERE clause (excluding the WHERE
	 *   itself). Passing null will return all rows for the given URL.
	 * @param selectionArgs You may include ?s in selection, which
	 *   will be replaced by the values from selectionArgs, in order
	 *   that they appear in the selection. The values will be bound
	 *   as Strings.
	 * @param groupBy A filter declaring how to group rows, formatted
	 *   as an SQL GROUP BY clause (excluding the GROUP BY
	 *   itself). Passing null will cause the rows to not be grouped.
	 * @param having A filter declare which row groups to include in
	 *   the cursor, if row grouping is being used, formatted as an
	 *   SQL HAVING clause (excluding the HAVING itself).  Passing
	 *   null will cause all row groups to be included, and is
	 *   required when row grouping is not being used.
	 * @param sortOrder How to order the rows, formatted as an SQL
	 *   ORDER BY clause (excluding the ORDER BY itself). Passing null
	 *   will use the default sort order, which may be unordered.
	 * @param limit Limits the number of rows returned by the query,
	 *   formatted as LIMIT clause. Passing null denotes no LIMIT clause.
	 */
	public void setQueryParams(String[] projectionIn, String selection, String[] selectionArgs, String groupBy, String having, String sortOrder, String limit) {
		if(mQueryType != TYPE_UNKNOWN){
			throw new IllegalStateException("A Model file's query parameters can only be set once!");
		}
		
		mQueryType = TYPE_MANAGED;

		mProjectionIn = projectionIn;
		mSelection = selection;
		mSelectionArgs = selectionArgs;
		mGroupBy = groupBy;
		mHaving = having;
		mSortOrder = sortOrder;
		mLimit = limit;
	}

	public void setStrict(boolean value){
		mStrict = value;
	}

	/**
	 * Sets the list of tables to query. Multiple tables can be specified to perform a join.
	 * For example:
	 *   setTables("foo, bar")
	 *   setTables("foo LEFT OUTER JOIN bar ON (foo.id = bar.foo_id)")
	 *
	 * @param inTables the list of tables to query on
	 */
	public void setTables(String inTables){
		mTables = inTables;
	}

	public String toJson(){
		final Gson gson = new Gson();
		return gson.toJson(this, EasyQueryModel.class);
	}

	@Override
	public String toString() {
		return "EasyQueryModel [mQueryType=" + mQueryType + ", mDistinct="
				+ mDistinct + ", mStrict=" + mStrict + ", mTables=" + mTables
				+ ", mProjectionIn=" + Arrays.toString(mProjectionIn)
				+ ", mSelection=" + mSelection + ", mSelectionArgs="
				+ Arrays.toString(mSelectionArgs) + ", mGroupBy=" + mGroupBy
				+ ", mHaving=" + mHaving + ", mSortOrder=" + mSortOrder
				+ ", mLimit=" + mLimit + ", mRawSql=" + mRawSql + "]";
	}


	public static EasyQueryModel getInstance(String json){
		final Gson gson = new Gson();
		return gson.fromJson(json, EasyQueryModel.class);
	}

}