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

package dev.alt236.easycursor.sqlcursor.querymodels;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import dev.alt236.easycursor.EasyCursor;
import dev.alt236.easycursor.EasyQueryModel;
import dev.alt236.easycursor.sqlcursor.BooleanLogic;
import dev.alt236.easycursor.sqlcursor.EasySqlCursor;
import dev.alt236.easycursor.sqlcursor.querybuilders.interfaces.QueryModelInfo;
import dev.alt236.easycursor.sqlcursor.querybuilders.interfaces.SqlRawQueryBuilder;
import dev.alt236.easycursor.sqlcursor.querybuilders.interfaces.SqlSelectBuilder;
import dev.alt236.easycursor.util.JsonPayloadHelper;

public abstract class SqlQueryModel implements EasyQueryModel {
    public static final int QUERY_TYPE_UNINITIALISED = 0;
    public static final int QUERY_TYPE_MANAGED = 1;
    public static final int QUERY_TYPE_RAW = 2;

    protected static final String FIELD_MODEL_COMMENT = "modelComment";
    protected static final String FIELD_MODEL_TAG = "modelTag";
    protected static final String FIELD_MODEL_VERSION = "modelVersion";
    protected static final String FIELD_QUERY_TYPE = "queryType";

    private final int mQueryType;

    //
    // Metadata
    //
    protected int mModelVersion;
    protected String mModelTag;
    protected String mModelComment;

    public SqlQueryModel(final QueryModelInfo info, final int queryType) {
        mQueryType = queryType;
        if (info != null) {
            mModelVersion = info.getModelVersion();
            mModelTag = info.getModelTag();
            mModelComment = info.getModelComment();
        }
    }

    protected void addCommonFields(final JSONObject payload) throws JSONException {
        // Common Fields
        JsonPayloadHelper.add(payload, FIELD_MODEL_COMMENT, mModelComment);
        JsonPayloadHelper.add(payload, FIELD_MODEL_TAG, mModelTag);
        JsonPayloadHelper.add(payload, FIELD_MODEL_VERSION, mModelVersion);
        JsonPayloadHelper.add(payload, FIELD_QUERY_TYPE, mQueryType);
    }

    /**
     * Execute the query described by this model.
     * <p>
     * If the model is initialised, or if the query model is of an unsupported type,
     * this method will throw an IllegalStateException.
     *
     * @param db the database to run the query against
     * @return the {@link EasySqlCursor} containing the result of the query.
     */
    public EasyCursor execute(final SQLiteDatabase db) {
        return execute(db, null);
    }

    /**
     * Execute the query described by this model.
     * <p>
     * If the model is initialised, or if the query model is of an unsupported type,
     * this method will throw an IllegalStateException.
     *
     * @param db           the database to run the query against
     * @param booleanLogic an Object describing the {@link BooleanLogic} for this cursor. Pass null for the {@link dev.alt236.easycursor.sqlcursor.DefaultBooleanLogic}
     * @return the {@link EasySqlCursor} containing the result of the query.
     */
    public EasyCursor execute(final SQLiteDatabase db, final BooleanLogic booleanLogic) {
        try {
            return execute(db, null, booleanLogic);
        } catch (final InstantiationException e) {
            throw new IllegalStateException("We should never get here...", e);
        } catch (final NoSuchMethodException e) {
            throw new IllegalStateException("We should never get here...", e);
        } catch (final IllegalAccessException e) {
            throw new IllegalStateException("We should never get here...", e);
        } catch (final InvocationTargetException e) {
            throw new IllegalStateException("We should never get here...", e);
        }
    }

    /**
     * Execute the query described by this model.
     * <p>
     * If the model is initialised, or if the query model is of an unsupported type,
     * this method will throw an IllegalStateException.
     *
     * @param db              the database to run the query against
     * @param easyCursorClass the Class of an EasyCursor implementation. Will use {@link EasySqlCursor} if null.
     * @param booleanLogic    an Object describing the {@link BooleanLogic} for this cursor. Pass null for the {@link dev.alt236.easycursor.sqlcursor.DefaultBooleanLogic}
     * @return the {@link EasySqlCursor} containing the result of the query.
     * @throws NoSuchMethodException
     * @throws InvocationTargetException if an exception was thrown by the invoked constructor
     * @throws IllegalAccessException    if this constructor is not accessible
     * @throws InstantiationException    if the class cannot be instantiated
     * @throws NoSuchMethodException     if a method with the requested parameters cannot be found
     * @throws IllegalArgumentException  if an incorrect number of arguments are passed, or an argument could not be converted by a widening conversion
     */
    public EasyCursor execute(final SQLiteDatabase db, final Class<? extends EasySqlCursor> easyCursorClass, final BooleanLogic booleanLogic) throws IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        final Cursor c = executeQuery(db);
        if (easyCursorClass == null) {
            return new EasySqlCursor(c, this, booleanLogic);
        } else {
            final Constructor<? extends EasySqlCursor> constructor = easyCursorClass.getDeclaredConstructor(Cursor.class, SqlQueryModel.class, BooleanLogic.class);
            return constructor.newInstance(c, this, booleanLogic);
        }
    }

    @SuppressLint("NewApi")
    private Cursor executeQuery(final SQLiteDatabase db) {
        final Cursor cursor = executeQueryInternal(db);

        cursor.moveToFirst();
        return cursor;
    }

    protected abstract Cursor executeQueryInternal(final SQLiteDatabase db);

    @Override
    public String getModelComment() {
        return mModelComment;
    }

    @Override
    public void setModelComment(final String modelComment) {
        mModelComment = modelComment;
    }

    @Override
    public String getModelTag() {
        return mModelTag;
    }

    @Override
    public void setModelTag(final String modelTag) {
        mModelTag = modelTag;
    }

    @Override
    public int getModelVersion() {
        return mModelVersion;
    }

    @Override
    public void setModelVersion(final int modelVersion) {
        mModelVersion = modelVersion;
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

    /**
     * Will return the JSON representation of this QueryModel.
     * It can be converted back into a QueryModel object using the
     * {@link SqlJsonModelConverter#convert(String)} method.
     *
     * @return the resulting JSON String
     * @throws IllegalStateException if one tries to create
     *                               a JSON representation when the model is uninitialised.
     * @throws JSONException         if there was an error creating the JSON
     */
    @Override
    public abstract String toJson() throws JSONException;

    @Override
    public String toString() {
        return "EasySqlQueryModel{" +
                "mQueryType=" + mQueryType +
                ", mModelVersion=" + mModelVersion +
                ", mModelTag='" + mModelTag + '\'' +
                ", mModelComment='" + mModelComment + '\'' +
                '}';
    }

    public static class RawQueryBuilder implements SqlRawQueryBuilder, QueryModelInfo {
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

        public SqlQueryModel build() {
            return new RawQueryModel(this);
        }

        @Override
        public String getModelComment() {
            return modelComment;
        }

        /**
         * Gets the user specified comment of this Model
         *
         * @return the comment
         */
        public RawQueryBuilder setModelComment(final String comment) {
            this.modelComment = comment;
            return this;
        }

        @Override
        public String getModelTag() {
            return modelTag;
        }

        /**
         * Gets the user specified tag of this Model
         *
         * @return the tag
         */
        public RawQueryBuilder setModelTag(final String tag) {
            this.modelTag = tag;
            return this;
        }

        @Override
        public int getModelVersion() {
            return modelVersion;
        }

        /**
         * Gets the user specified version of this Model
         * The default value is 0
         *
         * @return the version of this model
         */
        public RawQueryBuilder setModelVersion(final int version) {
            this.modelVersion = version;
            return this;
        }

        @Override
        public String getRawSql() {
            return rawSql;
        }

        public RawQueryBuilder setRawSql(final String sql) {
            this.rawSql = sql;
            return this;
        }

        @Override
        public String[] getSelectionArgs() {
            return selectionArgs;
        }

        public RawQueryBuilder setSelectionArgs(final String[] args) {
            this.selectionArgs = args;
            return this;
        }
    }


    public static class SelectQueryBuilder implements SqlSelectBuilder, QueryModelInfo {
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

        public SqlQueryModel build() {
            return new SelectQueryModel(this);
        }

        @Override
        public String getGroupBy() {
            return groupBy;
        }

        public SelectQueryBuilder setGroupBy(final String groupBy) {
            this.groupBy = groupBy;
            return this;
        }

        @Override
        public String getHaving() {
            return having;
        }

        public SelectQueryBuilder setHaving(final String having) {
            this.having = having;
            return this;
        }

        @Override
        public String getLimit() {
            return limit;
        }

        public SelectQueryBuilder setLimit(final String limit) {
            this.limit = limit;
            return this;
        }

        @Override
        public String getModelComment() {
            return modelComment;
        }

        /**
         * Gets the user specified comment of this Model
         *
         * @return the comment
         */
        public SelectQueryBuilder setModelComment(final String comment) {
            this.modelComment = comment;
            return this;
        }

        @Override
        public String getModelTag() {
            return modelTag;
        }

        /**
         * Gets the user specified tag of this Model
         *
         * @return the tag
         */
        public SelectQueryBuilder setModelTag(final String tag) {
            this.modelTag = tag;
            return this;
        }

        @Override
        public int getModelVersion() {
            return modelVersion;
        }

        /**
         * Gets the user specified version of this Model
         * The default value is 0
         *
         * @return the version of this model
         */
        public SelectQueryBuilder setModelVersion(final int version) {
            this.modelVersion = version;
            return this;
        }

        @Override
        public String[] getProjectionIn() {
            return projectionIn;
        }

        public SelectQueryBuilder setProjectionIn(final String[] projectionIn) {
            this.projectionIn = projectionIn;
            return this;
        }

        @Override
        public String getSelection() {
            return selection;
        }

        public SelectQueryBuilder setSelection(final String selection) {
            this.selection = selection;
            return this;
        }

        @Override
        public String[] getSelectionArgs() {
            return selectionArgs;
        }

        public SelectQueryBuilder setSelectionArgs(final String[] args) {
            this.selectionArgs = args;
            return this;
        }

        @Override
        public String getSortOrder() {
            return sortOrder;
        }

        public SelectQueryBuilder setSortOrder(final String sortOrder) {
            this.sortOrder = sortOrder;
            return this;
        }

        @Override
        public String getTables() {
            return tables;
        }

        /**
         * Sets the list of tables to query. Multiple tables can be specified to perform a join.
         * For example:
         * setFrom("foo, bar")
         * setFrom("foo LEFT OUTER JOIN bar ON (foo.id = bar.foo_id)")
         *
         * @param tables the list of tables to query on
         */
        public SelectQueryBuilder setTables(final String tables) {
            this.tables = tables;
            return this;
        }

        @Override
        public boolean isDistinct() {
            return distinct;
        }

        /**
         * Mark the query as DISTINCT.
         *
         * @param distinct true if true the query is DISTINCT, otherwise it isn't
         */
        public SelectQueryBuilder setDistinct(final boolean distinct) {
            this.distinct = distinct;
            return this;
        }

        @Override
        public boolean isStrict() {
            return strict;
        }

        /**
         * When set, the selection is verified against malicious arguments.
         * To summarize: To get maximum protection against malicious third party apps (for example
         * content provider consumers), make sure to do the following:
         * <ul>
         * <li>Set this value to true</li>
         * <li>Use a projection map</li>
         * <li>Use one of the query overloads instead of getting the statement as a sql string</li>
         * </ul>
         * By default, this value is false.
         * <p>
         * This value is ignored if you are on a device running API lower than 14.
         */
        public SelectQueryBuilder setStrict(final boolean strict) {
            this.strict = strict;
            return this;
        }
    }
}