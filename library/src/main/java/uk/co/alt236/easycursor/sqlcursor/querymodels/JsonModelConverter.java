package uk.co.alt236.easycursor.sqlcursor.querymodels;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 */
public final class JsonModelConverter {

    private JsonModelConverter() {
        // no instances
    }

    public static SqlQueryModel convert(final String json) throws JSONException {
        final JSONObject payload = new JSONObject(json);

        final int modelType = payload.optInt(SqlQueryModel.FIELD_QUERY_TYPE, SqlQueryModel.QUERY_TYPE_UNINITIALISED);
        switch (modelType) {
            case SqlQueryModel.QUERY_TYPE_MANAGED:
                return new SelectQueryModel(new JsonWrapper(payload));
            case SqlQueryModel.QUERY_TYPE_RAW:
                return new RawQueryModel(new JsonWrapper(payload));
            default:
                throw new IllegalStateException("JSON cannot be converted to a valid " + SqlQueryModel.class.getSimpleName());
        }
    }
}
