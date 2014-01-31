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
	private final String mRawSql;

	//
	// Managed Query
	//
	private final boolean mDistinct;
	private final boolean mStrict;
	private final String mTables;
	private final String[] mProjectionIn;
	private final String[] mSelectionArgs;
	private final String mSelection;
	private final String mGroupBy;
	private final String mHaving;
	private final String mSortOrder;
	private final String mLimit;

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
		mTables = builder.from;
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

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EasySqlQueryModel other = (EasySqlQueryModel) obj;
		if (mDistinct != other.mDistinct)
			return false;
		if (mGroupBy == null) {
			if (other.mGroupBy != null)
				return false;
		} else if (!mGroupBy.equals(other.mGroupBy))
			return false;
		if (mHaving == null) {
			if (other.mHaving != null)
				return false;
		} else if (!mHaving.equals(other.mHaving))
			return false;
		if (mLimit == null) {
			if (other.mLimit != null)
				return false;
		} else if (!mLimit.equals(other.mLimit))
			return false;
		if (mModelComment == null) {
			if (other.mModelComment != null)
				return false;
		} else if (!mModelComment.equals(other.mModelComment))
			return false;
		if (mModelTag == null) {
			if (other.mModelTag != null)
				return false;
		} else if (!mModelTag.equals(other.mModelTag))
			return false;
		if (mModelVersion != other.mModelVersion)
			return false;
		if (!Arrays.equals(mProjectionIn, other.mProjectionIn))
			return false;
		if (mQueryType != other.mQueryType)
			return false;
		if (mRawSql == null) {
			if (other.mRawSql != null)
				return false;
		} else if (!mRawSql.equals(other.mRawSql))
			return false;
		if (mSelection == null) {
			if (other.mSelection != null)
				return false;
		} else if (!mSelection.equals(other.mSelection))
			return false;
		if (!Arrays.equals(mSelectionArgs, other.mSelectionArgs))
			return false;
		if (mSortOrder == null) {
			if (other.mSortOrder != null)
				return false;
		} else if (!mSortOrder.equals(other.mSortOrder))
			return false;
		if (mStrict != other.mStrict)
			return false;
		if (mTables == null) {
			if (other.mTables != null)
				return false;
		} else if (!mTables.equals(other.mTables))
			return false;
		return true;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (mDistinct ? 1231 : 1237);
		result = prime * result
				+ ((mGroupBy == null) ? 0 : mGroupBy.hashCode());
		result = prime * result + ((mHaving == null) ? 0 : mHaving.hashCode());
		result = prime * result + ((mLimit == null) ? 0 : mLimit.hashCode());
		result = prime * result
				+ ((mModelComment == null) ? 0 : mModelComment.hashCode());
		result = prime * result
				+ ((mModelTag == null) ? 0 : mModelTag.hashCode());
		result = prime * result + mModelVersion;
		result = prime * result + Arrays.hashCode(mProjectionIn);
		result = prime * result + mQueryType;
		result = prime * result + ((mRawSql == null) ? 0 : mRawSql.hashCode());
		result = prime * result
				+ ((mSelection == null) ? 0 : mSelection.hashCode());
		result = prime * result + Arrays.hashCode(mSelectionArgs);
		result = prime * result
				+ ((mSortOrder == null) ? 0 : mSortOrder.hashCode());
		result = prime * result + (mStrict ? 1231 : 1237);
		result = prime * result + ((mTables == null) ? 0 : mTables.hashCode());
		return result;
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

		/**
		 * Gets the user specified comment of this Model
		 *
		 * @return the comment
		 */
		public RawQueryBuilder setModelComment(String comment){
			this.modelComment = comment;
			return this;
		}

		/**
		 * Gets the user specified tag of this Model
		 *
		 * @return the tag
		 */
		public RawQueryBuilder setModelTag(String tag){
			this.modelTag = tag;
			return this;
		}

		/**
		 * Gets the user specified version of this Model
		 * The default value is 0
		 *
		 * @return the version of this model
		 */
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
		private String from;
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

		/**
		 * Mark the query as DISTINCT.
		 *
		 * @param distinct true if true the query is DISTINCT, otherwise it isn't
		 */
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

		/**
		 * Gets the user specified comment of this Model
		 *
		 * @return the comment
		 */
		public SelectQueryBuilder setModelComment(String comment){
			this.modelComment = comment;
			return this;
		}

		/**
		 * Gets the user specified tag of this Model
		 *
		 * @return the tag
		 */
		public SelectQueryBuilder setModelTag(String tag){
			this.modelTag = tag;
			return this;
		}

		/**
		 * Gets the user specified version of this Model
		 * The default value is 0
		 *
		 * @return the version of this model
		 */
		public SelectQueryBuilder setModelVersion(int version){
			this.modelVersion = version;
			return this;
		}

		public SelectQueryBuilder setSelect(String[] projectionIn){
			this.projectionIn = projectionIn;
			return this;
		}

		public SelectQueryBuilder setWhere(String selection){
			this.selection = selection;
			return this;
		}

		public SelectQueryBuilder setWhereArgs(String args[]){
			this.selectionArgs = args;
			return this;
		}

		public SelectQueryBuilder setSortOrder(String sortOrder){
			this.sortOrder = sortOrder;
			return this;
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
		 *
		 * This value is ignored if you are on a device running API < 14.
		 */
		public SelectQueryBuilder setStrict(boolean strict){
			this.strict = strict;
			return this;
		}

		/**
		 * Sets the list of tables to query. Multiple tables can be specified to perform a join.
		 * For example:
		 *   setFrom("foo, bar")
		 *   setFrom("foo LEFT OUTER JOIN bar ON (foo.id = bar.foo_id)")
		 *
		 * @param inTables the list of tables to query on
		 */
		public SelectQueryBuilder setFrom(String from){
			this.from = from;
			return this;
		}
	}
}