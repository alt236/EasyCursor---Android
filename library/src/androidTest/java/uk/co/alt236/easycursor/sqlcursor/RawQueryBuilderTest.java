package uk.co.alt236.easycursor.sqlcursor;

import junit.framework.TestCase;

import uk.co.alt236.easycursor.EasyQueryModel;
import uk.co.alt236.easycursor.sqlcursor.querymodels.SqlQueryModel;

/**
 *
 */
public class RawQueryBuilderTest extends TestCase {

    public void test1() {
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
    }

    public void test3() {
        final String sql = "RAW_SQL";
        final String[] args = {"a", "b"};

        final EasyQueryModel model = new SqlQueryModel.RawQueryBuilder()
                .setRawSql(sql)
                .setSelectionArgs(args)
                .build();

        assertEquals(null, model.getModelComment());
        assertEquals(null, model.getModelTag());
        assertEquals(0, model.getModelVersion());
    }
}