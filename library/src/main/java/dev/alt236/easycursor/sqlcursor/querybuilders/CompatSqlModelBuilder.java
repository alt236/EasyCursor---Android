/*
 * ***************************************************************************
 * Copyright 2015 Alexandros Schillings
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ***************************************************************************
 *
 */

package dev.alt236.easycursor.sqlcursor.querybuilders;

import dev.alt236.easycursor.sqlcursor.querybuilders.interfaces.QueryModelInfo;
import dev.alt236.easycursor.sqlcursor.querybuilders.interfaces.SqlRawQueryBuilder;
import dev.alt236.easycursor.sqlcursor.querybuilders.interfaces.SqlSelectBuilder;
import dev.alt236.easycursor.sqlcursor.querymodels.RawQueryModel;
import dev.alt236.easycursor.sqlcursor.querymodels.SelectQueryModel;
import dev.alt236.easycursor.sqlcursor.querymodels.SqlQueryModel;

public class CompatSqlModelBuilder implements SqlRawQueryBuilder, SqlSelectBuilder, QueryModelInfo {
    // Model
    protected int mModelVersion;
    protected String mModelTag;
    protected String mModelComment;
    private int mQueryType = SqlQueryModel.QUERY_TYPE_UNINITIALISED;
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

    public SqlQueryModel build() {
        if (mQueryType == SqlQueryModel.QUERY_TYPE_RAW) {
            return new RawQueryModel(this);
        } else if (mQueryType == SqlQueryModel.QUERY_TYPE_MANAGED) {
            return new SelectQueryModel(this);
        } else if (mQueryType == SqlQueryModel.QUERY_TYPE_UNINITIALISED) {
            throw new IllegalStateException("You need to set some query parameters before calling build()!");
        } else {
            throw new IllegalStateException("Unknown query type: " + mQueryType);
        }
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

    /**
     * Gets the user specified comment of this Model
     */
    public void setModelComment(final String comment) {
        mModelComment = comment;
    }

    @Override
    public String getModelTag() {
        return mModelTag;
    }

    /**
     * Gets the user specified tag of this Model
     */
    public void setModelTag(final String tag) {
        mModelTag = tag;
    }

    @Override
    public int getModelVersion() {
        return mModelVersion;
    }

    public void setModelVersion(final int modelVersion) {
        mModelVersion = modelVersion;
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
     * This value is ignored if you are on a device running API lower than 14.
     *
     * @return the Strict setting
     */
    public boolean isStrict() {
        return mStrict;
    }

    /**
     * When set, the selection is verified against malicious arguments.
     * <ul>
     * <li>Set this value to true</li>
     * <li>Use a projection map</li>
     * <li>Use one of the query overloads instead of getting the statement as a sql string</li>
     * </ul>
     * By default, this value is false.
     * <p>
     * This value is ignored if you are on a device running API lower than 14.
     */
    public void setStrict(final boolean value) {
        mStrict = value;
    }

    /**
     * Sets the query parameters.
     * <p>
     * Will throw an IllegalStateException if one tries to set
     * the parameters more than once.
     *
     * @param rawSql        the SQL query. The SQL string must not be ; terminated
     * @param selectionArgs You may include ?s in where clause in the query,
     *                      which will be replaced by the values from selectionArgs. The
     *                      values will be bound as Strings.
     */
    public void setQueryParams(final String rawSql, final String[] selectionArgs) {
        if (mQueryType != SqlQueryModel.QUERY_TYPE_UNINITIALISED) {
            throw new IllegalStateException("A Model file's query parameters can only be set once!");
        }

        mQueryType = SqlQueryModel.QUERY_TYPE_RAW;
        mSelectionArgs = selectionArgs;
        mRawSql = rawSql;
    }

    /**
     * Sets the query parameters.
     * <p>
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
     * <p>
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
        if (mQueryType != SqlQueryModel.QUERY_TYPE_UNINITIALISED) {
            throw new IllegalStateException("A Model file's query parameters can only be set once!");
        }

        mQueryType = SqlQueryModel.QUERY_TYPE_MANAGED;

        mProjectionIn = projectionIn;
        mSelection = selection;
        mSelectionArgs = selectionArgs;
        mGroupBy = groupBy;
        mHaving = having;
        mSortOrder = sortOrder;
        mLimit = limit;
    }
}
