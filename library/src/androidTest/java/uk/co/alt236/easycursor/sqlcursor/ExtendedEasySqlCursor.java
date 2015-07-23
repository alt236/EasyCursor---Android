package uk.co.alt236.easycursor.sqlcursor;

import android.database.Cursor;

import uk.co.alt236.easycursor.sqlcursor.querymodels.SqlQueryModel;

/**
 *
 */
public class ExtendedEasySqlCursor extends EasySqlCursor {
    public ExtendedEasySqlCursor(final Cursor cursor, final SqlQueryModel model, final BooleanLogic booleanLogic) {
        super(cursor, model, booleanLogic);
    }
}
