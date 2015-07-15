package uk.co.alt236.easycursor.sqlcursor.querybuilders;

import android.database.sqlite.SQLiteDatabase;

import uk.co.alt236.easycursor.sqlcursor.EasySqlQueryModel;

public class EasyCompatSqlModelBuilder {
    private int mQueryType = EasySqlQueryModel.QUERY_TYPE_UNINITIALISED;

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

    public EasySqlQueryModel build() {
        return new EasySqlQueryModel(this);
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
    public String getTables() {
        return mTables;
    }

    /**
     * Sets the list of tables to query. Multiple tables can be specified to perform a join.
     * For example:
     * setTables("foo, bar")
     * setTables("foo LEFT OUTER JOIN bar ON (foo.id = bar.foo_id)")
     *
     * @param inTables the list of tables to query on
     */
    public void setTables(final String inTables) {
        mTables = inTables;
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
     * Mark the query as DISTINCT.
     *
     * @param distinct true if true the query is DISTINCT, otherwise it isn't
     */
    public void setDistinct(final boolean distinct) {
        mDistinct = distinct;
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
     * <p/>
     * To summarize: To get maximum protection against malicious third party apps (for example
     * content provider consumers), make sure to do the following:
     * <ul>
     * <li>Set this value to true</li>
     * <li>Use a projection map</li>
     * <li>Use one of the query overloads instead of getting the statement as a sql string</li>
     * </ul>
     * By default, this value is false.
     * <p/>
     * This value is ignored if you are on a device running API < 14.
     */
    public void setStrict(final boolean value) {
        mStrict = value;
    }

    /**
     * Sets the query parameters.
     * <p/>
     * Will throw an IllegalStateException if one tries to set
     * the parameters more than once.
     *
     * @param rawSql        the SQL query. The SQL string must not be ; terminated
     * @param selectionArgs You may include ?s in where clause in the query,
     *                      which will be replaced by the values from selectionArgs. The
     *                      values will be bound as Strings.
     */
    public void setQueryParams(final String rawSql, final String[] selectionArgs) {
        if (mQueryType != EasySqlQueryModel.QUERY_TYPE_UNINITIALISED) {
            throw new IllegalStateException("A Model file's query parameters can only be set once!");
        }

        mQueryType = EasySqlQueryModel.QUERY_TYPE_RAW;
        mSelectionArgs = selectionArgs;
        mRawSql = rawSql;
    }

    /**
     * Sets the query parameters.
     * <p/>
     * Will throw an IllegalStateException if one tries to set
     * the parameters more than once.
     *
     * @param projectionIn  A list of which columns to return. Passing
     *                      null will return all columns, which is discouraged to prevent
     *                      reading data from storage that isn't going to be used.
     * @param selection     A filter declaring which rows to return,
     *                      formatted as an SQL WHERE clause (excluding the WHERE
     *                      itself). Passing null will return all rows for the given URL.
     * @param selectionArgs You may include ?s in selection, which
     *                      will be replaced by the values from selectionArgs, in order
     *                      that they appear in the selection. The values will be bound
     *                      as Strings.
     * @param sortOrder     How to order the rows, formatted as an SQL
     *                      ORDER BY clause (excluding the ORDER BY itself). Passing null
     *                      will use the default sort order, which may be unordered.
     */
    public void setQueryParams(final String[] projectionIn, final String selection, final String[] selectionArgs, final String sortOrder) {
        setQueryParams(projectionIn, selection, selectionArgs, null, null, sortOrder, null);
    }

    /**
     * Sets the query parameters.
     * <p/>
     * Will throw an IllegalStateException if one tries to set
     * the parameters more than once.
     *
     * @param projectionIn  A list of which columns to return. Passing
     *                      null will return all columns, which is discouraged to prevent
     *                      reading data from storage that isn't going to be used.
     * @param selection     A filter declaring which rows to return,
     *                      formatted as an SQL WHERE clause (excluding the WHERE
     *                      itself). Passing null will return all rows for the given URL.
     * @param selectionArgs You may include ?s in selection, which
     *                      will be replaced by the values from selectionArgs, in order
     *                      that they appear in the selection. The values will be bound
     *                      as Strings.
     * @param groupBy       A filter declaring how to group rows, formatted
     *                      as an SQL GROUP BY clause (excluding the GROUP BY
     *                      itself). Passing null will cause the rows to not be grouped.
     * @param having        A filter declare which row groups to include in
     *                      the cursor, if row grouping is being used, formatted as an
     *                      SQL HAVING clause (excluding the HAVING itself).  Passing
     *                      null will cause all row groups to be included, and is
     *                      required when row grouping is not being used.
     * @param sortOrder     How to order the rows, formatted as an SQL
     *                      ORDER BY clause (excluding the ORDER BY itself). Passing null
     *                      will use the default sort order, which may be unordered.
     */
    public void setQueryParams(final String[] projectionIn, final String selection, final String[] selectionArgs, final String groupBy, final String having, final String sortOrder) {
        setQueryParams(projectionIn, selection, selectionArgs, groupBy, having, sortOrder, null);
    }

    /**
     * Sets the query parameters.
     *
     * @param projectionIn  A list of which columns to return. Passing
     *                      null will return all columns, which is discouraged to prevent
     *                      reading data from storage that isn't going to be used.
     * @param selection     A filter declaring which rows to return,
     *                      formatted as an SQL WHERE clause (excluding the WHERE
     *                      itself). Passing null will return all rows for the given URL.
     * @param selectionArgs You may include ?s in selection, which
     *                      will be replaced by the values from selectionArgs, in order
     *                      that they appear in the selection. The values will be bound
     *                      as Strings.
     * @param groupBy       A filter declaring how to group rows, formatted
     *                      as an SQL GROUP BY clause (excluding the GROUP BY
     *                      itself). Passing null will cause the rows to not be grouped.
     * @param having        A filter declare which row groups to include in
     *                      the cursor, if row grouping is being used, formatted as an
     *                      SQL HAVING clause (excluding the HAVING itself).  Passing
     *                      null will cause all row groups to be included, and is
     *                      required when row grouping is not being used.
     * @param sortOrder     How to order the rows, formatted as an SQL
     *                      ORDER BY clause (excluding the ORDER BY itself). Passing null
     *                      will use the default sort order, which may be unordered.
     * @param limit         Limits the number of rows returned by the query,
     *                      formatted as LIMIT clause. Passing null denotes no LIMIT clause.
     * @throws IllegalStateException if one tries to set
     *                               the parameters more than once.
     */
    public void setQueryParams(final String[] projectionIn, final String selection, final String[] selectionArgs, final String groupBy, final String having, final String sortOrder, final String limit) {
        if (mQueryType != EasySqlQueryModel.QUERY_TYPE_UNINITIALISED) {
            throw new IllegalStateException("A Model file's query parameters can only be set once!");
        }

        mQueryType = EasySqlQueryModel.QUERY_TYPE_MANAGED;

        mProjectionIn = projectionIn;
        mSelection = selection;
        mSelectionArgs = selectionArgs;
        mGroupBy = groupBy;
        mHaving = having;
        mSortOrder = sortOrder;
        mLimit = limit;
    }
}
