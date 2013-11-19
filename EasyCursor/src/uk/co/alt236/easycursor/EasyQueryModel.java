package uk.co.alt236.easycursor;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import org.json.JSONException;
import org.json.JSONObject;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.Build;

public class EasyQueryModel {
	public static final int QUERY_TYPE_UNINITIALISED = 0;
	public static final int QUERY_TYPE_MANAGED = 1;
	public static final int QUERY_TYPE_RAW = 2;

	private static final String FIELD_DISTINCT = "distinct";
	private static final String FIELD_GROUP_BY = "groupBy";
	private static final String FIELD_HAVING = "having";
	private static final String FIELD_LIMIT = "limit";
	private static final String FIELD_MODEL_COMMENT = "comment";
	private static final String FIELD_MODEL_TAG = "tag";
	private static final String FIELD_MODEL_VERSION = "version";
	private static final String FIELD_PROJECTION_IN = "projectionIn";
	private static final String FIELD_QUERY_TYPE = "queryType";
	private static final String FIELD_RAW_SQL = "rawSql";
	private static final String FIELD_SELECTION = "selection";
	private static final String FIELD_SELECTION_ARGS = "selectionArgs";
	private static final String FIELD_SORT_ORDER = "sortOrder";
	private static final String FIELD_STRICT = "strict";
	private static final String FIELD_TABLES = "tables";

	private int mQueryType = QUERY_TYPE_UNINITIALISED;

	//
	// Metadata
	//
	private int mModelVersion;
	private String mModelTag;
	private String mModelComment;

	//
	// Raw Query
	//
	private String mRawSql;

	//
	// Managed Query
	//
	private boolean mDistinct;
	private boolean mStrict;
	private String mTables;
	private String[] mProjectionIn;
	private String[] mSelectionArgs;
	private String mSelection;
	private String mGroupBy;
	private String mHaving; 
	private String mSortOrder;
	private String mLimit;

	public EasyQueryModel(){}
	
	protected EasyQueryModel(String json) throws JSONException{
		final JSONObject payload = new JSONObject(json);
		mDistinct = JsonPayloadHelper.getBoolean(payload, FIELD_DISTINCT);
		mGroupBy = JsonPayloadHelper.getString(payload, FIELD_GROUP_BY);
		mHaving = JsonPayloadHelper.getString(payload, FIELD_HAVING);
		mLimit = JsonPayloadHelper.getString(payload, FIELD_LIMIT);
		mModelComment = JsonPayloadHelper.getString(payload, FIELD_MODEL_COMMENT);
		mModelTag = JsonPayloadHelper.getString(payload, FIELD_MODEL_TAG);
		mModelVersion = JsonPayloadHelper.getInt(payload, FIELD_MODEL_VERSION);
		mProjectionIn = JsonPayloadHelper.getStringArray(payload, FIELD_PROJECTION_IN);
		mRawSql = JsonPayloadHelper.getString(payload, mRawSql);
		mSelection = JsonPayloadHelper.getString(payload, FIELD_SELECTION);
		mSelectionArgs = JsonPayloadHelper.getStringArray(payload, FIELD_SELECTION_ARGS);
		mSortOrder = JsonPayloadHelper.getString(payload, FIELD_SORT_ORDER);
		mStrict = JsonPayloadHelper.getBoolean(payload, FIELD_STRICT);
		mTables = JsonPayloadHelper.getString(payload, FIELD_TABLES);
		mQueryType = JsonPayloadHelper.getInt(payload, FIELD_QUERY_TYPE);
	}

	/**
	 * Execute the query described by this model.
	 * 
	 * If the model is initialised, or if the query model is of an unsupported type, 
	 * this method will throw an IllegalStateException.
	 *
	 * @param db the database to run the query against
	 * @return the {@link EasyCursor} containing the result of the query.
	 */
	public EasyCursor execute(final SQLiteDatabase db){
		return new EasyCursor(executeQuery(db), this);
	}

	/**
	 * Execute the query described by this model.
	 * 
	 * If the model is initialised, or if the query model is of an unsupported type, 
	 * this method will throw an IllegalStateException.
	 *
	 * @param db the database to run the query against
	 * @param easyCursorClass the Class of an EasyCursor implementation. Will use {@link EasyCursor} if null.
	 * @return the {@link EasyCursor} containing the result of the query.
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException if an exception was thrown by the invoked constructor
	 * @throws IllegalAccessException if this constructor is not accessible
	 * @throws InstantiationException if the class cannot be instantiated
	 * @throws IllegalArgumentException if an incorrect number of arguments are passed, or an argument could not be converted by a widening conversion
	 */
	public EasyCursor execute(final SQLiteDatabase db, Class<? extends EasyCursor> easyCursorClass) throws IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		
		final Cursor c = executeQuery(db);
		if(easyCursorClass == null){
			return new EasyCursor(c, this);
		} else {
			return easyCursorClass.getDeclaredConstructor(Cursor.class, EasyQueryModel.class).newInstance(c, this);
		}
	}
	
	private Cursor executeQuery(final SQLiteDatabase db){
		final int queryType = mQueryType;
		final Cursor cursor;
		
		switch (queryType) {
		case QUERY_TYPE_MANAGED:
			final SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
			builder.setTables(getTables());
			builder.setDistinct(isDistinct());

			if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH){
				builder.setStrict(isStrict());
			}

			cursor = builder.query(db, mProjectionIn, mSelection, mSelectionArgs, mGroupBy, mHaving, mSortOrder, mLimit);
			break;
		case QUERY_TYPE_RAW:
			cursor = db.rawQuery(mRawSql, mSelectionArgs);
			break;
		case QUERY_TYPE_UNINITIALISED:
			throw new IllegalStateException("Attempted to execute an uninitialised query model");
		default:
			throw new IllegalStateException("Attempted to execute a query of an unknown query type: " + queryType);
		}

		cursor.moveToFirst();
		return cursor;
	}
	
	/**
	 * Gets the user specified comment of this Model
	 * 
	 * @return the comment
	 */
	public String getComment() {
		return mModelComment;
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

	/**
	 * Gets the user specified tag of this Model
	 * 
	 * @return the tag
	 */
	public String getModelTag() {
		return mModelTag;
	}

	/**
	 * Gets the user specified version of this Model
	 * The default value is 0
	 * 
	 * @return the tag
	 */
	public int getModelVersion() {
		return mModelVersion;
	}

	public String[] getProjectionIn() {
		return mProjectionIn;
	}

	/**
	 * Gets the type of this query.
	 * The supported types are provided as QUERY_TYPE_* constants in this class
	 * 
	 * @return the type of the query
	 */
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

	public void setComment(String modelComment) {
		mModelComment = modelComment;
	}

	/**
	 * Mark the query as DISTINCT.
	 *
	 * @param distinct true if true the query is DISTINCT, otherwise it isn't
	 */
	public void setDistinct(boolean distinct){
		mDistinct = distinct;
	}

	/**
	 * Sets the query parameters.
	 *
	 * Will throw an IllegalStateExcetion if one tries to set
	 * the parameters more than once.
	 *
	 * @param rawSql the SQL query. The SQL string must not be ; terminated
	 * @param selectionArgs You may include ?s in where clause in the query,
	 *     which will be replaced by the values from selectionArgs. The
	 *     values will be bound as Strings.     
	 */
	public void setQueryParams(final String rawSql, final String[] selectionArgs) {
		if(mQueryType != QUERY_TYPE_UNINITIALISED){
			throw new IllegalStateException("A Model file's query parameters can only be set once!");
		}

		mQueryType = QUERY_TYPE_RAW;
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
		if(mQueryType != QUERY_TYPE_UNINITIALISED){
			throw new IllegalStateException("A Model file's query parameters can only be set once!");
		}

		mQueryType = QUERY_TYPE_MANAGED;

		mProjectionIn = projectionIn;
		mSelection = selection;
		mSelectionArgs = selectionArgs;
		mGroupBy = groupBy;
		mHaving = having;
		mSortOrder = sortOrder;
		mLimit = limit;
	}

	/**
	 * When set, the selection is verified against malicious arguments.
	 * When using this class to create a statement using
	 * {@link #buildQueryString(boolean, String, String[], String, String, String, String, String)},
	 * non-numeric limits will raise an exception. If a projection map is specified, fields
	 * not in that map will be ignored.
	 * If this class is used to execute the statement directly using
	 * {@link #query(SQLiteDatabase, String[], String, String[], String, String, String)}
	 * or
	 * {@link #query(SQLiteDatabase, String[], String, String[], String, String, String, String)},
	 * additionally also parenthesis escaping selection are caught.
	 *
	 * To summarize: To get maximum protection against malicious third party apps (for example
	 * content provider consumers), make sure to do the following:
	 * <ul>
	 * <li>Set this value to true</li>
	 * <li>Use a projection map</li>
	 * <li>Use one of the query overloads instead of getting the statement as a sql string</li>
	 * </ul>
	 * By default, this value is false.
	 * This value is ignored if you are on a device running API < 14.
	 */
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

	public void setTag(String modelTag) {
		mModelTag = modelTag;
	}

	public void setVersion(int modelVersion) {
		mModelVersion = modelVersion;
	}

	/**
	 * Will return the JSON representation of this QueryModel.
	 * It can be converted back into a QueryModel object using the
	 * {@link #getInstance(String)} static method.
	 *
	 * @return the resulting JSON String
	 * @throws JSONException if there was an error creating the JSON
	 */
	public String toJson() throws JSONException{
		final JSONObject payload = new JSONObject();

		JsonPayloadHelper.add(payload, FIELD_DISTINCT, mDistinct);
		JsonPayloadHelper.add(payload, FIELD_GROUP_BY, mGroupBy);
		JsonPayloadHelper.add(payload, FIELD_HAVING, mHaving);
		JsonPayloadHelper.add(payload, FIELD_LIMIT, mLimit);
		JsonPayloadHelper.add(payload, FIELD_MODEL_COMMENT, mModelComment);
		JsonPayloadHelper.add(payload, FIELD_MODEL_TAG, mModelTag);
		JsonPayloadHelper.add(payload, FIELD_MODEL_VERSION, mModelVersion);
		JsonPayloadHelper.add(payload, FIELD_PROJECTION_IN, mProjectionIn);
		JsonPayloadHelper.add(payload, FIELD_RAW_SQL, mRawSql);
		JsonPayloadHelper.add(payload, FIELD_SELECTION, mSelection);
		JsonPayloadHelper.add(payload, FIELD_SELECTION_ARGS, mSelectionArgs);
		JsonPayloadHelper.add(payload, FIELD_SORT_ORDER, mSortOrder);
		JsonPayloadHelper.add(payload, FIELD_STRICT, mStrict);
		JsonPayloadHelper.add(payload, FIELD_TABLES, mTables);
		JsonPayloadHelper.add(payload, FIELD_QUERY_TYPE, mQueryType);
		return payload.toString();
	}

	@Override
	public String toString() {
		return "EasyQueryModel [mQueryType=" + mQueryType + ", mVersion="
				+ mModelVersion + ", mTag=" + mModelTag + ", mComment=" + mModelComment
				+ ", mRawSql=" + mRawSql + ", mDistinct=" + mDistinct
				+ ", mStrict=" + mStrict + ", mTables=" + mTables
				+ ", mProjectionIn=" + Arrays.toString(mProjectionIn)
				+ ", mSelectionArgs=" + Arrays.toString(mSelectionArgs)
				+ ", mSelection=" + mSelection + ", mGroupBy=" + mGroupBy
				+ ", mHaving=" + mHaving + ", mSortOrder=" + mSortOrder
				+ ", mLimit=" + mLimit + "]";
	}

	public static EasyQueryModel getInstance(String json) throws JSONException{
		return new EasyQueryModel(json);
	}
}