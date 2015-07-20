package uk.co.alt236.easycursor.sqlcursor.querymodels;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.Build;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import uk.co.alt236.easycursor.sqlcursor.querybuilders.interfaces.QueryModelInfo;
import uk.co.alt236.easycursor.sqlcursor.querybuilders.interfaces.SqlSelectBuilder;
import uk.co.alt236.easycursor.util.JsonPayloadHelper;

/**
 *
 */
public class SelectQueryModel extends SqlQueryModel {
    private static final int QUERY_TYPE = RawQueryModel.QUERY_TYPE_MANAGED;
    private static final String FIELD_DISTINCT = "distinct";
    private static final String FIELD_GROUP_BY = "groupBy";
    private static final String FIELD_HAVING = "having";
    private static final String FIELD_LIMIT = "limit";

    private static final String FIELD_PROJECTION_IN = "projectionIn";
    private static final String FIELD_SELECTION = "selection";
    private static final String FIELD_SORT_ORDER = "sortOrder";
    private static final String FIELD_STRICT = "strict";
    private static final String FIELD_TABLES = "tables";
    private static final String FIELD_SELECTION_ARGS = "selectionArgs";

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

    public SelectQueryModel(final SqlSelectBuilder builder) {
        super(builder instanceof QueryModelInfo ? (QueryModelInfo) builder : null,
                QUERY_TYPE);
        mDistinct = builder.isDistinct();
        mGroupBy = builder.getGroupBy();
        mHaving = builder.getHaving();
        mLimit = builder.getLimit();
        mProjectionIn = builder.getSelect();
        mSelection = builder.getWhere();
        mSelectionArgs = builder.getWhereArgs();
        mSortOrder = builder.getOrderBy();
        mStrict = builder.isStrict();
        mTables = builder.getTables();
    }

    public SelectQueryModel(final JsonWrapper wrapper) {
        super(wrapper, QUERY_TYPE);
        mDistinct = wrapper.getBoolean(FIELD_DISTINCT);
        mGroupBy = wrapper.getString(FIELD_GROUP_BY);
        mHaving = wrapper.getString(FIELD_HAVING);
        mLimit = wrapper.getString(FIELD_LIMIT);
        mModelComment = wrapper.getString(FIELD_MODEL_COMMENT);
        mModelTag = wrapper.getString(FIELD_MODEL_TAG);
        mModelVersion = wrapper.getInt(FIELD_MODEL_VERSION);
        mProjectionIn = wrapper.getStringArray(FIELD_PROJECTION_IN);
        mSelection = wrapper.getString(FIELD_SELECTION);
        mSortOrder = wrapper.getString(FIELD_SORT_ORDER);
        mStrict = wrapper.getBoolean(FIELD_STRICT);
        mTables = wrapper.getString(FIELD_TABLES);
        mSelectionArgs = wrapper.getStringArray(FIELD_SELECTION_ARGS);
    }

    @Override
    protected Cursor executeQueryInternal(final SQLiteDatabase db) {
        final SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(getTables());
        builder.setDistinct(isDistinct());

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            builder.setStrict(isStrict());
        }

        return builder.query(db,
                getProjectionIn(),
                getSelection(),
                getSelectionArgs(),
                getGroupBy(),
                getHaving(),
                getSortOrder(),
                getLimit());
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
    public String getTables() {
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
    public String toJson() throws JSONException {
        final JSONObject payload = new JSONObject();

        addCommonFields(payload);
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

        return payload.toString();
    }

    @Override
    public String toString() {
        return "SelectQueryModel{" +
                "mDistinct=" + mDistinct +
                ", mStrict=" + mStrict +
                ", mTables='" + mTables + '\'' +
                ", mProjectionIn=" + Arrays.toString(mProjectionIn) +
                ", mSelectionArgs=" + Arrays.toString(mSelectionArgs) +
                ", mSelection='" + mSelection + '\'' +
                ", mGroupBy='" + mGroupBy + '\'' +
                ", mHaving='" + mHaving + '\'' +
                ", mSortOrder='" + mSortOrder + '\'' +
                ", mLimit='" + mLimit + '\'' +
                '}';
    }

}
