/*
 * ***************************************************************************
 * Copyright 2017 Alexandros Schillings
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

package uk.co.alt236.easycursor.sqlcursor;


import org.junit.Before;
import org.junit.Test;
import org.robolectric.RuntimeEnvironment;

import uk.co.alt236.easycursor.EasyCursor;
import uk.co.alt236.easycursor.common.root.BaseRobolectricTest;
import uk.co.alt236.easycursor.sqlcursor.factory.DatabaseHandler;
import uk.co.alt236.easycursor.sqlcursor.factory.ExtendedEasySqlCursor;
import uk.co.alt236.easycursor.sqlcursor.querymodels.SqlQueryModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class ExtendedSqlCursorTest extends BaseRobolectricTest {
    private DatabaseHandler mHandler;
    private SqlQueryModel mModel;

    @Before
    public void setUp() {
        mHandler = new DatabaseHandler(RuntimeEnvironment.application);
        mHandler.getReadableDatabase();
        mModel = new SqlQueryModel.RawQueryBuilder()
                .setRawSql("SELECT * FROM data")
                .build();
    }

    @Test
    public void createADefaultEasySqlCursoe() throws Exception {
        final EasyCursor cursor = mModel.execute(mHandler.getReadableDatabase());
        assertTrue(cursor instanceof EasySqlCursor);
        assertEquals(3, cursor.getCount());
        cursor.close();
    }

    @Test
    public void createACustomEasySqlCursor() throws Exception {
        final EasyCursor cursor = mModel.execute(mHandler.getReadableDatabase(), EasySqlCursor.class, null);
        assertTrue(cursor instanceof EasySqlCursor);
        assertEquals(3, cursor.getCount());
        cursor.close();
    }

    @Test
    public void createACustomExtendedEasySqlCursor() throws Exception {
        final EasyCursor cursor = mModel.execute(mHandler.getReadableDatabase(), ExtendedEasySqlCursor.class, null);
        assertTrue(cursor instanceof ExtendedEasySqlCursor);
        assertEquals(3, cursor.getCount());
        cursor.close();
    }
}