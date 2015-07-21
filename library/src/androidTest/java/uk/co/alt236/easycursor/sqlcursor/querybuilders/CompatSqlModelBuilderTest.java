package uk.co.alt236.easycursor.sqlcursor.querybuilders;

import junit.framework.TestCase;

import java.util.Arrays;

import uk.co.alt236.easycursor.EasyQueryModel;
import uk.co.alt236.easycursor.sqlcursor.querymodels.JsonModelConverter;
import uk.co.alt236.easycursor.sqlcursor.querymodels.SelectQueryModel;

/**
 *
 */
public class CompatSqlModelBuilderTest extends TestCase {
    public void testFields() throws Exception {
        final String[] projectionIn = {"a", "b"};
        final String[] selectArgs = {"a", "b", "c"};

        final CompatSqlModelBuilder builder1 = new CompatSqlModelBuilder();
        builder1.setDistinct(true);
        builder1.setStrict(true);
        builder1.setTables("tables");
        builder1.setQueryParams(projectionIn, "selection", selectArgs, "groupby", "having", "sortorder", "limit");
        final SelectQueryModel model1 = (SelectQueryModel) builder1.build();

        assertEquals(true, model1.isDistinct());
        assertEquals("groupby", model1.getGroupBy());
        assertEquals("having", model1.getHaving());
        assertEquals("limit", model1.getLimit());
        assertTrue(Arrays.equals(projectionIn, model1.getProjectionIn()));
        assertEquals("sortorder", model1.getSortOrder());
        assertEquals(true, model1.isStrict());
        assertEquals("tables", model1.getTables());
        assertEquals("selection", model1.getSelection());
        assertTrue(Arrays.equals(selectArgs, model1.getSelectionArgs()));

        final CompatSqlModelBuilder builder2 = new CompatSqlModelBuilder();
        builder2.setDistinct(false);
        builder2.setStrict(false);
        builder2.setTables(null);
        builder2.setQueryParams(null, null, null, null, null, null, null);
        final SelectQueryModel model2 = (SelectQueryModel) builder2.build();

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

        try {
            new CompatSqlModelBuilder().build();
            fail("This should have thrown an exception");
        } catch (final IllegalStateException e) {
            // Expected
        }
    }

    public void testJson() throws Exception {
        final String[] projectionIn = {"a", "b"};
        final String[] selectArgs = {"a", "b", "c"};

        final CompatSqlModelBuilder builder1 = new CompatSqlModelBuilder();
        builder1.setDistinct(true);
        builder1.setStrict(true);
        builder1.setTables("tables");
        builder1.setQueryParams(projectionIn, "selection", selectArgs, "groupby", "having", "sortorder", "limit");
        final SelectQueryModel model1 = (SelectQueryModel) builder1.build();

        final String json = model1.toJson();
        final SelectQueryModel model2 = (SelectQueryModel) JsonModelConverter.convert(json);

        assertEquals(model1.isDistinct(), model2.isDistinct());
        assertEquals(model1.getGroupBy(), model2.getGroupBy());
        assertEquals(model1.getHaving(), model2.getHaving());
        assertEquals(model1.getLimit(), model2.getLimit());
        assertTrue(Arrays.equals(model1.getProjectionIn(), model2.getProjectionIn()));
        assertEquals(model1.getSortOrder(), model2.getSortOrder());
        assertEquals(model1.isStrict(), model2.isStrict());
        assertEquals(model1.getTables(), model2.getTables());
        assertEquals(model1.getSelection(), model2.getSelection());
        assertTrue(Arrays.equals(model1.getSelectionArgs(), model2.getSelectionArgs()));
    }

    public void testModelInfo() {
        final String comment = "comment";
        final String tag = "tag";
        final int version = 22;

        final CompatSqlModelBuilder builder = new CompatSqlModelBuilder();
        builder.setQueryParams("blah", null); // to avoid exception
        builder.setModelComment(comment);
        builder.setModelTag(tag);
        builder.setModelVersion(version);

        final EasyQueryModel model = builder.build();

        assertEquals(comment, model.getModelComment());
        assertEquals(tag, model.getModelTag());
        assertEquals(version, model.getModelVersion());

        final CompatSqlModelBuilder builder2 = new CompatSqlModelBuilder();
        builder2.setQueryParams("blah", null); // to avoid exception
        final EasyQueryModel model2 = builder2.build();

        assertEquals(null, model2.getModelComment());
        assertEquals(null, model2.getModelTag());
        assertEquals(0, model2.getModelVersion());
    }
}