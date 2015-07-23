package uk.co.alt236.easycursor.sqlcursor.querymodels;

import junit.framework.TestCase;

import java.util.Arrays;

import uk.co.alt236.easycursor.EasyQueryModel;

/**
 *
 */
public class RawQueryBuilderTest extends TestCase {

    public void testFields() throws Exception{
        final String sql = "RAW_SQL";
        final String[] args = {"a", "b"};

        final RawQueryModel model = (RawQueryModel) new SqlQueryModel.RawQueryBuilder()
                .setSelectionArgs(args)
                .setRawSql(sql)
                .build();

        assertEquals(args, model.getSelectionArgs());
        assertEquals(sql, model.getRawSql());

        final RawQueryModel model2 = (RawQueryModel) new SqlQueryModel.RawQueryBuilder()
                .build();

        assertEquals(null, model2.getSelectionArgs());
        assertEquals(null, model2.getRawSql());
    }

    public void testFieldsUnset() throws Exception{
        final RawQueryModel model = (RawQueryModel) new SqlQueryModel.RawQueryBuilder()
                .build();

        assertEquals(null, model.getSelectionArgs());
        assertEquals(null, model.getRawSql());
    }

    public void testJson() throws Exception{
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

    public void testModelInfo() throws Exception{
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

    public void testModelInfoUnset() throws Exception{
        final EasyQueryModel model1 = new SqlQueryModel.RawQueryBuilder()
                .build();

        assertEquals(null, model1.getModelComment());
        assertEquals(null, model1.getModelTag());
        assertEquals(0, model1.getModelVersion());
    }
}