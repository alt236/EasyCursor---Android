package uk.co.alt236.easycursor.sqlcursor.querymodels;

import junit.framework.TestCase;

import java.util.Arrays;

import uk.co.alt236.easycursor.EasyQueryModel;

/**
 *
 */
public class SelectQueryBuilderTest extends TestCase {

    public void testFields() throws Exception {
        final String[] projectionIn = {"a", "b"};
        final String[] selectionArgs = {"a", "b", "c"};

        final SelectQueryModel model = (SelectQueryModel) new SqlQueryModel.SelectQueryBuilder()
                .setDistinct(true)
                .setGroupBy("groupby")
                .setHaving("having")
                .setLimit("limit")
                .setProjectionIn(projectionIn)
                .setSortOrder("sortorder")
                .setStrict(true)
                .setTables("tables")
                .setSelection("selection")
                .setSelectionArgs(selectionArgs)
                .build();

        assertEquals(true, model.isDistinct());
        assertEquals("groupby", model.getGroupBy());
        assertEquals("having", model.getHaving());
        assertEquals("limit", model.getLimit());
        assertTrue(Arrays.equals(projectionIn, model.getProjectionIn()));
        assertEquals("sortorder", model.getSortOrder());
        assertEquals(true, model.isStrict());
        assertEquals("tables", model.getTables());
        assertEquals("selection", model.getSelection());
        assertTrue(Arrays.equals(selectionArgs, model.getSelectionArgs()));

        final SelectQueryModel model2 = (SelectQueryModel) new SqlQueryModel.SelectQueryBuilder()
                .setDistinct(false)
                .setGroupBy(null)
                .setHaving(null)
                .setLimit(null)
                .setProjectionIn(null)
                .setSortOrder(null)
                .setStrict(false)
                .setTables(null)
                .setSelection(null)
                .setSelectionArgs(null)
                .build();


        assertEquals(false, model2.isDistinct());
        assertEquals(null, model2.getGroupBy());
        assertEquals(null, model2.getHaving());
        assertEquals(null, model2.getLimit());
        assertTrue(Arrays.equals(null, model2.getProjectionIn()));
        assertEquals(null, model2.getSortOrder());
        assertEquals(false, model2.isStrict());
        assertEquals(null, model2.getTables());
        assertEquals(null, model2.getSelection());
        assertTrue(Arrays.equals(null, model2.getSelectionArgs()));
    }

    public void testFieldsUnset() {
        final SelectQueryModel model1 = (SelectQueryModel) new SqlQueryModel.SelectQueryBuilder()
                .build();


        assertEquals(false, model1.isDistinct());
        assertEquals(null, model1.getGroupBy());
        assertEquals(null, model1.getHaving());
        assertEquals(null, model1.getLimit());
        assertTrue(Arrays.equals(null, model1.getProjectionIn()));
        assertEquals(null, model1.getSortOrder());
        assertEquals(false, model1.isStrict());
        assertEquals(null, model1.getTables());
        assertEquals(null, model1.getSelection());
        assertTrue(Arrays.equals(null, model1.getSelectionArgs()));
    }

    public void testJson() throws Exception {
        final String[] projectionIn = {"a", "b"};
        final String[] selectionArgs = {"a", "b", "c"};

        final SelectQueryModel model = (SelectQueryModel) new SqlQueryModel.SelectQueryBuilder()
                .setDistinct(true)
                .setGroupBy("groupby")
                .setHaving("having")
                .setLimit("limit")
                .setProjectionIn(projectionIn)
                .setSortOrder("sortorder")
                .setStrict(true)
                .setTables("tables")
                .setSelection("selection")
                .setSelectionArgs(selectionArgs)
                .build();

        final String json = model.toJson();
        final SelectQueryModel model2 = (SelectQueryModel) JsonModelConverter.convert(json);

        assertEquals(model.isDistinct(), model2.isDistinct());
        assertEquals(model.getGroupBy(), model2.getGroupBy());
        assertEquals(model.getHaving(), model2.getHaving());
        assertEquals(model.getLimit(), model2.getLimit());
        assertTrue(Arrays.equals(model.getProjectionIn(), model2.getProjectionIn()));
        assertEquals(model.getSortOrder(), model2.getSortOrder());
        assertEquals(model.isStrict(), model2.isStrict());
        assertEquals(model.getTables(), model2.getTables());
        assertEquals(model.getSelection(), model2.getSelection());
        assertTrue(Arrays.equals(model.getSelectionArgs(), model2.getSelectionArgs()));
    }

    public void testModelInfo() {
        final String comment = "comment";
        final String tag = "tag";
        final int version = 22;
        final String[] args = {"a", "b"};

        final EasyQueryModel model = new SqlQueryModel.SelectQueryBuilder()
                .setModelComment(comment)
                .setModelTag(tag)
                .setModelVersion(version)
                .setSelectionArgs(args)
                .build();

        assertEquals(comment, model.getModelComment());
        assertEquals(tag, model.getModelTag());
        assertEquals(version, model.getModelVersion());

        final EasyQueryModel model2 = new SqlQueryModel.SelectQueryBuilder()
                .setSelectionArgs(args)
                .build();

        assertEquals(null, model2.getModelComment());
        assertEquals(null, model2.getModelTag());
        assertEquals(0, model2.getModelVersion());
    }

    public void testModelInfoUnset() throws Exception {
        final EasyQueryModel model1 = new SqlQueryModel.SelectQueryBuilder()
                .build();

        assertEquals(null, model1.getModelComment());
        assertEquals(null, model1.getModelTag());
        assertEquals(0, model1.getModelVersion());
    }
}