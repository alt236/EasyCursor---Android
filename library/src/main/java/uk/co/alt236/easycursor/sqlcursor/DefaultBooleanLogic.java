package uk.co.alt236.easycursor.sqlcursor;

import uk.co.alt236.easycursor.EasyCursor;

/**
 *
 */
public class DefaultBooleanLogic implements BooleanLogic {

    /**
     * This method defines the default boolean resolution of an {@link EasySqlCursor}
     * <p/>
     * It is defined as:
     * <code>
     * final int value = cursor.getInt(columnNumber);
     * return (value == 1);
     * <code/>
     *
     * @param cursor       the cursor we are examining
     * @param columnNumber the column we are examining
     * @return
     */
    @Override
    public boolean isTrue(final EasyCursor cursor, final int columnNumber) {
        final int value = cursor.getInt(columnNumber);
        return (value == 1);
    }
}
