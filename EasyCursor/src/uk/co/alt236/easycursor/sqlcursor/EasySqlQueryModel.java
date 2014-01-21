package uk.co.alt236.easycursor.sqlcursor;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import org.json.JSONException;
import org.json.JSONObject;

import uk.co.alt236.easycursor.EasyCursor;
import uk.co.alt236.easycursor.EasyQueryModel;
import uk.co.alt236.easycursor.sqlcursor.querybuilders.EasyCompatSqlModelBuilder;
import uk.co.alt236.easycursor.sqlcursor.querybuilders.interfaces.SqlRawQueryBuilder;
import uk.co.alt236.easycursor.sqlcursor.querybuilders.interfaces.SqlSelectBuilder;
import uk.co.alt236.easycursor.util.JsonPayloadHelper;
import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.Build;

public class EasySqlQueryModel implements EasyQueryModel{
	public static final int QUERY_TYPE_UNINITIALISED = 0;
	public static final int QUERY_TYPE_MANAGED = 1;
	public static final int QUERY_TYPE_RAW = 2;

	private static final String FIELD_DISTINCT = "distinct";
	private static final String FIELD_GROUP_BY = "groupBy";
	private static final String FIELD_HAVING = "having";
	private static final String FIELD_LIMIT = "limit";
	private static final String FIELD_MODEL_COMMENT = "modelComment";
	private static final String FIELD_MODEL_TAG = "modelTag";
	private static final String FIELD_MODEL_VERSION = "modelVersion";
	private static final String FIELD_PROJECTION_IN = "projectionIn";
	private static final String FIELD_QUERY_TYPE = "queryType";
	private static final String FIELD_RAW_SQL = "rawSql";
	private static final String FIELD_SELECTION = "selection";
	private static final String FIELD_SELECTION_ARGS = "selectionArgs";
	private static final String FIELD_SORT_ORDER = "sortOrder";
	private static final String FIELD_STRICT = "strict";
	private static final String FIELD_TABLES = "tables";

	private final int mQueryType;

	//
	// Metadata
	//
	private int mModelVersion;
	private String mModelTag;
	private String mModelComment;

	//
	// Raw Query
	//
	final private String mRawSql;

	//
	// Managed Query
	//
	final private boolean mDistinct;
	final private boolean mStrict;
	final private String mTables;
	final private String[] mProjectionIn;
	final private String[] mSelectionArgs;
	final private String mSelection;
	final private String mGroupBy;
	final private String mHaving;
	final private String mSortOrder;
	final private String mLimit;

	public EasySqlQueryModel (EasyCompatSqlModelBuilder builder){
		mDistinct = builder.isDistinct();
		mGroupBy = builder.getGroupBy();
		mHaving = builder.getHaving();
		mLimit = builder.getLimit();
		mModelComment = null;
		mModelTag = null;
		mModelVersion = 0;
		mProjectionIn = builder.getProjectionIn();
		mRawSql = builder.getRawSql();
		mSelection = builder.getSelection();
		mSelectionArgs = builder.getSelectionArgs();
		mSortOrder = builder.getSortOrder();
		mStrict = builder.isStrict();
		mTables = builder.getTables();
		mQueryType = builder.getQueryType();
	}

	private EasySqlQueryModel(RawQueryBuilder builder) {
		mDistinct = false;
		mGroupBy = null;
		mHaving = null;
		mLimit = null;
		mModelComment = builder.modelComment;
		mModelTag = builder.modelTag;
		mModelVersion = builder.modelVersion;
		mProjectionIn = null;
		mRawSql = builder.rawSql;
		mSelection = null;
		mSelectionArgs = builder.selectionArgs;
		mSortOrder = null;
		mStrict = false;
		mTables = null;
		mQueryType = builder.queryType;
	}

	private EasySqlQueryModel(SelectQueryBuilder builder) {
		mDistinct = builder.distinct;
		mGroupBy = builder.groupBy;
		mHaving = builder.having;
		mLimit = builder.limit;
		mModelComment = builder.modelComment;
		mModelTag = builder.modelTag;
		mModelVersion = builder.modelVersion;
		mProjectionIn = builder.projectionIn;
		mRawSql = null;
		mSelection = builder.selection;
		mSelectionArgs = builder.selectionArgs;
		mSortOrder = builder.sortOrder;
		mStrict = builder.strict;
		mTables = builder.tables;
		mQueryType = builder.queryType;
	}

	public EasySqlQueryModel(SqlRawQueryBuilder builder) {
		mDistinct = false;
		mGroupBy = null;
		mHaving = null;
		mLimit = null;
		mModelComment = null;
		mModelTag = null;
		mModelVersion = 0;
		mProjectionIn = null;
		mRawSql = builder.getRawSql();
		mSelection = null;
		mSelectionArgs = builder.getWhereArgs();
		mSortOrder = null;
		mStrict = false;
		mTables = null;
		mQueryType = QUERY_TYPE_RAW;
	}

	public EasySqlQueryModel(SqlSelectBuilder builder) {
		mDistinct = builder.isDistinct();
		mGroupBy = builder.getGroupBy();
		mHaving = builder.getHaving();
		mLimit = builder.getLimit();
		mModelComment = null;
		mModelTag = null;
		mModelVersion = 0;
		mProjectionIn = builder.getSelect();
		mRawSql = null;
		mSelection = builder.getWhere();
		mSelectionArgs = builder.getWhereArgs();
		mSortOrder = builder.getOrderBy();
		mStrict = builder.isStrict();
		mTables = builder.getTables();
		mQueryType = QUERY_TYPE_MANAGED;
	}

	public EasySqlQueryModel(String json) throws JSONException{
		final JSONObject payload = new JSONObject(json);
		mDistinct = JsonPayloadHelper.getBoolean(payload, FIELD_DISTINCT);
		mGroupBy = JsonPayloadHelper.getString(payload, FIELD_GROUP_BY);
		mHaving = JsonPayloadHelper.getString(payload, FIELD_HAVING);
		mLimit = JsonPayloadHelper.getString(payload, FIELD_LIMIT);
		mModelComment = JsonPayloadHelper.getString(payload, FIELD_MODEL_COMMENT);
		mModelTag = JsonPayloadHelper.getString(payload, FIELD_MODEL_TAG);
		mModelVersion = JsonPayloadHelper.getInt(payload, FIELD_MODEL_VERSION);
		mProjectionIn = JsonPayloadHelper.getStringArray(payload, FIELD_PROJECTION_IN);
		mRawSql = JsonPayloadHelper.getString(payload, FIELD_RAW_SQL);
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
	 * @return the {@link EasySqlCursor} containing the result of the query.
	 */
	public EasyCursor execute(final SQLiteDatabase db){
		return new EasySqlCursor(executeQuery(db), this);
	}

	/**
	 * Execute the query described by this model.
	 *
	 * If the model is initialised, or if the query model is of an unsupported type,
	 * this method will throw an IllegalStateException.
	 *
	 * @param db the database to run the query against
	 * @param easyCursorClass the Class of an EasyCursor implementation. Will use {@link EasySqlCursor} if null.
	 * @return the {@link EasySqlCursor} containing the result of the query.
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException if an exception was thrown by the invoked constructor
	 * @throws IllegalAccessException if this constructor is not accessible
	 * @throws InstantiationException if the class cannot be instantiated
	 * @throws IllegalArgumentException if an incorrect number of arguments are passed, or an argument could not be converted by a widening conversion
	 */
	public EasyCursor execute(final SQLiteDatabase db, Class<? extends EasySqlCursor> easyCursorClass) throws IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{

		final Cursor c = executeQuery(db);
		if(easyCursorClass == null){
			return new EasySqlCursor(c, this);
		} else {
			return easyCursorClass.getDeclaredConstructor(Cursor.class, EasySqlQueryModel.class).newInstance(c, this);
		}
	}

	@SuppressLint("NewApi")
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
	 * Returns the GroupBy clause of this model.
	 * If no such clause is set, it return null.
	 *
	 * @return the GroupBy clause
	 */
	public String getGroupBy() {
		return mGroupBy;
	}

	/**
	 * Returns the Having clause of this model.
	 * If no such clause is set, it return null.
	 *
	 * @return the Having clause
	 */
	public String getHaving() {
		return mHaving;
	}

	/**
	 * Returns the Limit clause of this model.
	 * If no such clause is set, it return null.
	 *
	 * @return the Limit clause
	 */
	public String getLimit() {
		return mLimit;
	}

	@Override
	public String getModelComment() {
		return mModelComment;
	}

	@Override
	public String getModelTag() {
		return mModelTag;
	}

	@Override
	public int getModelVersion() {
		return mModelVersion;
	}

	/**
	 * Returns the Projection clause of this model.
	 * If no such clause is set, it return null.
	 *
	 * @return the Projection clause
	 */
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

	/**
	 * Returns the Selection clause of this model.
	 * If no such clause is set, it return null.
	 *
	 * @return the Selection clause
	 */
	public String getSelection() {
		return mSelection;
	}

	/**
	 * Returns the Selection arguments of this model.
	 * If no such arguments are set, it return null.
	 *
	 * @return the Selection clause
	 */
	public String[] getSelectionArgs() {
		return mSelectionArgs;
	}

	/**
	 * Returns the Sort Order clause of this model.
	 * If no such clause is set, it return null.
	 *
	 * @return the Sort Order clause
	 */
	public String getSortOrder() {
		return mSortOrder;
	}

	/**
	 * Returns the Tables this model is set to run against.
	 * If no such clause is set, it return null.
	 *
	 * @return the Tables clause
	 */
	public String getTables(){
		return mTables;
	}

	/**
	 * Returns whether or not this query is set to be Distinct.
	 *
	 * @return the Distinct setting
	 */
	public boolean isDistinct() {
		return mDistinct;
	}

	/**
	 * Returns whether or not this query is set to be Strict.
	 * This value is ignored if you are on a device running API < 14.
	 *
	 * @return the Strict setting
	 */
	public boolean isStrict() {
		return mStrict;
	}

	@Override
	public void setModelComment(String modelComment) {
		mModelComment = modelComment;
	}

	@Override
	public void setModelTag(String modelTag) {
		mModelTag = modelTag;
	}

	@Override
	public void setModelVersion(int modelVersion) {
		mModelVersion = modelVersion;
	}

	/**
	 * Will return the JSON representation of this QueryModel.
	 * It can be converted back into a QueryModel object using the
	 * {@link #getInstance(String)} static method.
	 *
	 * @throws IllegalStateExcetion if one tries to create
	 *   a JSON representation when the model is uninitialised.
	 * @return the resulting JSON String
	 * @throws JSONException if there was an error creating the JSON
	 */
	@Override
	public String toJson() throws JSONException{
		final JSONObject payload = new JSONObject();

		switch(mQueryType){
		case QUERY_TYPE_MANAGED:
			JsonPayloadHelper.add(payload, FIELD_DISTINCT, mDistinct);
			JsonPayloadHelper.add(payload, FIELD_GROUP_BY, mGroupBy);
			JsonPayloadHelper.add(payload, FIELD_HAVING, mHaving);
			JsonPayloadHelper.add(payload, FIELD_LIMIT, mLimit);
			JsonPayloadHelper.add(payload, FIELD_PROJECTION_IN, mProjectionIn);
			JsonPayloadHelper.add(payload, FIELD_SELECTION, mSelection);
			JsonPayloadHelper.add(payload, FIELD_SELECTION_ARGS, mSelectionArgs);
			JsonPayloadHelper.add(payload, FIELD_SORT_ORDER, mSortOrder);
			JsonPayloadHelper.add(payload, FIELD_STRICT, mStrict);
			JsonPayloadHelper.add(payload, FIELD_TABLES, mTables);
			break;
		case QUERY_TYPE_RAW:
			JsonPayloadHelper.add(payload, FIELD_RAW_SQL, mRawSql);
			JsonPayloadHelper.add(payload, FIELD_SELECTION_ARGS, mSelectionArgs);
			break;
		case QUERY_TYPE_UNINITIALISED:
			throw new IllegalStateException("Cannot produce the JSON representation of a uninitialised model file!");
		default:
			throw new IllegalStateException("Attempted to create JSON representation of an unknown query type: " + mQueryType);
		}

		// Common Fields
		JsonPayloadHelper.add(payload, FIELD_MODEL_COMMENT, mModelComment);
		JsonPayloadHelper.add(payload, FIELD_MODEL_TAG, mModelTag);
		JsonPayloadHelper.add(payload, FIELD_MODEL_VERSION, mModelVersion);
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



	public static class RawQueryBuilder{
		private final int queryType = QUERY_TYPE_RAW;

		//
		// Metadata
		//
		private int modelVersion;
		private String modelTag;
		private String modelComment;

		//
		// Raw Query
		//
		private String rawSql;
		private String[] selectionArgs;

		public EasySqlQueryModel build(){
			return new EasySqlQueryModel(this);
		}

		public RawQueryBuilder setModelComment(String comment){
			this.modelComment = comment;
			return this;
		}

		public RawQueryBuilder setModelTag(String tag){
			this.modelTag = tag;
			return this;
		}

		public RawQueryBuilder setModelVersion(int version){
			this.modelVersion = version;
			return this;
		}

		public RawQueryBuilder setRawSql(String sql){
			this.rawSql = sql;
			return this;
		}

		public RawQueryBuilder setSelectionArgs(String args[]){
			this.selectionArgs = args;
			return this;
		}
	}



	public static class SelectQueryBuilder{
		private final int queryType = QUERY_TYPE_MANAGED;
		//
		// Metadata
		//
		private int modelVersion;
		private String modelTag;
		private String modelComment;

		//
		// Select Query
		//
		private boolean distinct;
		private boolean strict;
		private String tables;
		private String[] projectionIn;
		private String[] selectionArgs;
		private String selection;
		private String groupBy;
		private String having;
		private String sortOrder;
		private String limit;

		public EasySqlQueryModel build(){
			return new EasySqlQueryModel(this);
		}

		public SelectQueryBuilder setDistict(boolean distinct){
			this.distinct = distinct;
			return this;
		}

		public SelectQueryBuilder setGroupBy(String groupBy){
			this.groupBy = groupBy;
			return this;
		}

		public SelectQueryBuilder setHaving(String having){
			this.having = having;
			return this;
		}

		public SelectQueryBuilder setLimit(String limit){
			this.limit = limit;
			return this;
		}

		public SelectQueryBuilder setModelComment(String comment){
			this.modelComment = comment;
			return this;
		}

		public SelectQueryBuilder setModelTag(String tag){
			this.modelTag = tag;
			return this;
		}

		public SelectQueryBuilder setModelVersion(int version){
			this.modelVersion = version;
			return this;
		}

		public SelectQueryBuilder setProjectionIn(String[] projectionIn){
			this.projectionIn = projectionIn;
			return this;
		}

		public SelectQueryBuilder setSelection(String selection){
			this.selection = selection;
			return this;
		}

		public SelectQueryBuilder setSelectionArgs(String args[]){
			this.selectionArgs = args;
			return this;
		}

		public SelectQueryBuilder setSortOrder(String sortOrder){
			this.sortOrder = sortOrder;
			return this;
		}

		public SelectQueryBuilder setStrict(boolean strict){
			this.strict = strict;
			return this;
		}

		public SelectQueryBuilder setTables(String tables){
			this.tables = tables;
			return this;
		}
	}
}