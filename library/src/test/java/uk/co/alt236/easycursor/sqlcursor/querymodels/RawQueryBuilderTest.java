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

package uk.co.alt236.easycursor.sqlcursor.querymodels;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Arrays;

import uk.co.alt236.easycursor.EasyQueryModel;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class RawQueryBuilderTest {

    @Test
    public void testFields() throws Exception {
        final String sql = "RAW_SQL";
        final String[] args = {"a", "b"};

        final RawQueryModel model = (RawQueryModel) new SqlQueryModel.RawQueryBuilder()
                .setSelectionArgs(args)
                .setRawSql(sql)
                .build();

        assertArrayEquals(args, model.getSelectionArgs());
        assertEquals(sql, model.getRawSql());

        final RawQueryModel model2 = (RawQueryModel) new SqlQueryModel.RawQueryBuilder()
                .build();

        assertArrayEquals(null, model2.getSelectionArgs());
        assertEquals(null, model2.getRawSql());
    }

    @Test
    public void testFieldsUnset() throws Exception {
        final RawQueryModel model = (RawQueryModel) new SqlQueryModel.RawQueryBuilder()
                .build();

        assertEquals(null, model.getSelectionArgs());
        assertEquals(null, model.getRawSql());
    }

    @Test
    public void testJson() throws Exception {
        final String comment = "comment";
        final String tag = "tag";
        final int version = 22;
        final String sql = "RAW_SQL";
        final String[] args = {"a", "b"};

        final RawQueryModel model = (RawQueryModel) new SqlQueryModel.RawQueryBuilder()
                .setModelComment(comment)
                .setModelTag(tag)
                .setModelVersion(version)
                .setSelectionArgs(args)
                .setRawSql(sql)
                .build();

        final String json = model.toJson();

        final RawQueryModel model2 = (RawQueryModel) SqlJsonModelConverter.convert(json);

        assertEquals(model.getModelComment(), model2.getModelComment());
        assertEquals(model.getModelTag(), model2.getModelTag());
        assertEquals(model.getModelVersion(), model2.getModelVersion());
        assertTrue(Arrays.equals(model.getSelectionArgs(), model2.getSelectionArgs()));
        assertEquals(model.getRawSql(), model2.getRawSql());
    }

    @Test
    public void testModelInfo() throws Exception {
        final String comment = "comment";
        final String tag = "tag";
        final int version = 22;
        final String sql = "RAW_SQL";
        final String[] args = {"a", "b"};

        final EasyQueryModel model = new SqlQueryModel.RawQueryBuilder()
                .setModelComment(comment)
                .setModelTag(tag)
                .setModelVersion(version)
                .setRawSql(sql)
                .setSelectionArgs(args)
                .build();

        assertEquals(comment, model.getModelComment());
        assertEquals(tag, model.getModelTag());
        assertEquals(version, model.getModelVersion());

        final EasyQueryModel model2 = new SqlQueryModel.RawQueryBuilder()
                .setRawSql(sql)
                .setSelectionArgs(args)
                .build();

        assertEquals(null, model2.getModelComment());
        assertEquals(null, model2.getModelTag());
        assertEquals(0, model2.getModelVersion());
    }

    @Test
    public void testModelInfoUnset() throws Exception {
        final EasyQueryModel model1 = new SqlQueryModel.RawQueryBuilder()
                .build();

        assertEquals(null, model1.getModelComment());
        assertEquals(null, model1.getModelTag());
        assertEquals(0, model1.getModelVersion());
    }
}