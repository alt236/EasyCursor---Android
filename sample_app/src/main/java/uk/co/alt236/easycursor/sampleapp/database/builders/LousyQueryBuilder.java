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

package uk.co.alt236.easycursor.sampleapp.database.builders;

import uk.co.alt236.easycursor.sqlcursor.querybuilders.interfaces.SqlSelectBuilder;
import uk.co.alt236.easycursor.sqlcursor.querymodels.SelectQueryModel;
import uk.co.alt236.easycursor.sqlcursor.querymodels.SqlQueryModel;

public class LousyQueryBuilder implements SqlSelectBuilder {
    private String mGroupBy;
    private String mHaving;
    private String mLimit;
    private String mOrderBy;
    private String mTables;
    private String mWhere;
    private String[] mSelect;
    private String[] mWhereArgs;
    private boolean mDistinct;
    private boolean mStrict;

    @Override
    public SqlQueryModel build() {
        return new SelectQueryModel(this);
    }

    @Override
    public String getGroupBy() {
        return mGroupBy;
    }

    public LousyQueryBuilder setGroupBy(final String groupBy) {
        mGroupBy = groupBy;
        return this;
    }

    @Override
    public String getHaving() {
        return mHaving;
    }

    public LousyQueryBuilder setHaving(final String having) {
        mHaving = having;
        return this;
    }

    @Override
    public String getLimit() {
        return mLimit;
    }

    public LousyQueryBuilder setLimit(final String limit) {
        mLimit = limit;
        return this;
    }

    @Override
    public String[] getProjectionIn() {
        return mSelect;
    }

    @Override
    public String getSelection() {
        return mWhere;
    }

    @Override
    public String[] getSelectionArgs() {
        return mWhereArgs;
    }

    @Override
    public String getSortOrder() {
        return mOrderBy;
    }

    @Override
    public String getTables() {
        return mTables;
    }

    @Override
    public boolean isDistinct() {
        return mDistinct;
    }

    public LousyQueryBuilder setDistinct(final boolean value) {
        mDistinct = value;
        return this;
    }

    @Override
    public boolean isStrict() {
        return mStrict;
    }

    public LousyQueryBuilder setStrict(final boolean strict) {
        mStrict = strict;
        return this;
    }

    public LousyQueryBuilder setFrom(final String tables) {
        mTables = tables;
        return this;
    }

    public LousyQueryBuilder setOrderBy(final String orderBy) {
        mOrderBy = orderBy;
        return this;
    }

    public LousyQueryBuilder setSelect(final String[] select) {
        mSelect = select;
        return this;
    }

    public LousyQueryBuilder setWhere(final String where) {
        mWhere = where;
        return this;
    }

    public LousyQueryBuilder setWhereArgs(final String[] whereArgs) {
        mWhereArgs = whereArgs;
        return this;
    }
}
