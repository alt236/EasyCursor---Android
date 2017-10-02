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
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import uk.co.alt236.easycursor.EasyCursor;
import uk.co.alt236.easycursor.sqlcursor.querymodels.SqlQueryModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class EasySqlCursorTest2 {
    private DatabaseHandler mHandler;

    @Before
    public void setUp() {
        mHandler = new DatabaseHandler(RuntimeEnvironment.application);
        mHandler.getWritableDatabase();
    }

    @Test
    public void test1() throws Exception {
        final SqlQueryModel model = new SqlQueryModel.RawQueryBuilder()
                .setRawSql("SELECT * FROM data")
                .build();

        final EasyCursor cursor = model.execute(mHandler.getReadableDatabase());
        assertEquals(3, cursor.getCount());

        cursor.close();
    }

    @Test
    public void test2() throws Exception {
        final SqlQueryModel model = new SqlQueryModel.RawQueryBuilder()
                .setRawSql("SELECT * FROM data")
                .build();

        final EasyCursor cursor = model.execute(mHandler.getReadableDatabase(), EasySqlCursor.class, null);
        assertEquals(3, cursor.getCount());

        cursor.close();
    }

    @Test
    public void test3() throws Exception {
        final SqlQueryModel model = new SqlQueryModel.RawQueryBuilder()
                .setRawSql("SELECT * FROM data")
                .build();

        final EasyCursor cursor = model.execute(mHandler.getReadableDatabase(), ExtendedEasySqlCursor.class, null);
        assertTrue(cursor instanceof ExtendedEasySqlCursor);
        assertEquals(3, cursor.getCount());

        cursor.close();
    }

    public void test4() throws Exception {
        final String fieldName = "booleantest";

        final SqlQueryModel model = new SqlQueryModel.RawQueryBuilder()
                .setRawSql("SELECT * FROM data")
                .build();

        // DEFAULT BOOLEAN LOGIC
        final EasyCursor cursor1 = model.execute(mHandler.getReadableDatabase());
        assertEquals(3, cursor1.getCount());

        cursor1.moveToPosition(0);
        assertFalse("Failed for value: " + cursor1.getInt(fieldName), cursor1.getBoolean(fieldName));

        cursor1.moveToPosition(1);
        assertTrue("Failed for value: " + cursor1.getInt(fieldName), cursor1.getBoolean(fieldName));

        cursor1.moveToPosition(2);
        assertFalse("Failed for value: " + cursor1.getInt(fieldName), cursor1.getBoolean(fieldName));

        cursor1.close();


        // REVERSED BOOLEAN LOGIC
        final BooleanLogic logic = new BooleanLogic() {
            private final BooleanLogic DEFAULT_LOGIC = new DefaultBooleanLogic();

            @Override
            public boolean isTrue(final EasyCursor cur, final int columnNumber) {
                return !DEFAULT_LOGIC.isTrue(cur, columnNumber);
            }
        };

        final EasyCursor cursor2 = model.execute(mHandler.getReadableDatabase(), logic);
        assertEquals(3, cursor2.getCount());

        cursor2.moveToPosition(0);
        assertTrue("Failed for value: " + cursor2.getInt(fieldName), cursor2.getBoolean(fieldName));

        cursor2.moveToPosition(1);
        assertFalse("Failed for value: " + cursor2.getInt(fieldName), cursor2.getBoolean(fieldName));

        cursor2.moveToPosition(2);
        assertTrue("Failed for value: " + cursor2.getInt(fieldName), cursor2.getBoolean(fieldName));

        cursor2.close();
    }
}