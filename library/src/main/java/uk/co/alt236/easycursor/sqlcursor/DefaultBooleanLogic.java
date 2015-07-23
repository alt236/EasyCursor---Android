package uk.co.alt236.easycursor.sqlcursor;

import uk.co.alt236.easycursor.EasyCursor;

/**
 *
 */
public class DefaultBooleanLogic implements BooleanLogic {

    /**
     * This method defines the default boolean resolution of an {@link EasySqlCursor}
     * <p>
     * It is defined as:
     * <pre>
     * {@code
     * final int value = cursor.getInt(columnNumber);
     * return (value == 1);
     * }
     * </pre>
     *
     * @param cursor       the cursor we are examining
     * @param columnNumber the column we are examining
     * @return whether the contents of the specified column of the cursor represent TRUE
     */
    @Override
    public boolean isTrue(final EasyCursor cursor, final int columnNumber) {
        final int value = cursor.getInt(columnNumber);
        return (value == 1);
    }
}
