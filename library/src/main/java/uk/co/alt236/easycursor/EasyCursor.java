package uk.co.alt236.easycursor;

import android.database.Cursor;

public interface EasyCursor extends Cursor {

    /**
     * Returns the value of the requested column as a byte array or throws
     * IllegalArgumentException if the column doesn't exist.
     *
     * @param columnName the column name
     * @return the value from cursor
     * @throws IllegalArgumentException if the column doesn't exist
     */
    byte[] getBlob(final String columnName);

    /**
     * Returns the value of the requested column as a boolean or throws
     * IllegalArgumentException if the column doesn't exist.
     * <p/>
     * The logic is implementation specific
     *
     * @param columnName the column name
     * @return the value from cursor
     * @throws IllegalArgumentException if the column doesn't exist
     */
    boolean getBoolean(final String columnName);

    /**
     * Returns the value of the requested column as a double or throws
     * IllegalArgumentException if the column doesn't exist.
     *
     * @param columnName the column name
     * @return the value from cursor
     * @throws IllegalArgumentException if the column doesn't exist
     */
    double getDouble(final String columnName);

    /**
     * Returns the value of the requested column as a float or throws
     * IllegalArgumentException if the column doesn't exist.
     *
     * @param columnName the column name
     * @return the value from cursor
     * @throws IllegalArgumentException if the column doesn't exist
     */
    float getFloat(final String columnName);

    /**
     * Returns the value of the requested column as an int or throws
     * IllegalArgumentException if the column doesn't exist.
     *
     * @param columnName the column name
     * @return the value from cursor
     * @throws IllegalArgumentException if the column doesn't exist
     */
    int getInt(final String columnName);

    /**
     * Returns the value of the requested column as a long or throws
     * IllegalArgumentException if the column doesn't exist.
     *
     * @param columnName the column name
     * @return the value from cursor
     * @throws IllegalArgumentException if the column doesn't exist
     */
    long getLong(final String columnName);

    /**
     * Gets the {@link EasyQueryModel} which produced this cursor (if any).
     * If this cursor was not the produced via an {@link EasyQueryModel},
     * the result is null;
     *
     * @return the query model
     */
    EasyQueryModel getQueryModel();

    /**
     * Returns the value of the requested column as a short or throws
     * IllegalArgumentException if the column doesn't exist.
     *
     * @param columnName the column name
     * @return the value from cursor
     * @throws IllegalArgumentException if the column doesn't exist
     */
    short getShort(final String columnName);

    /**
     * Returns the value of the requested column as a String or throws
     * IllegalArgumentException if the column doesn't exist.
     *
     * @param columnName the column name
     * @return the value from cursor
     * @throws IllegalArgumentException if the column doesn't exist
     */
    String getString(final String columnName);

    /**
     * Returns <code>true</code> if the value in the indicated column is null.
     *
     * @param columnName the the name of the column to check.
     * @return whether the column value is null.
     */
    boolean isNull(String columnName);

    /**
     * Extracts the contents of a cursors Column as a Boolean.
     * If the column does not exist, it will return null;
     * <p/>
     * The logic is implementation specific.
     *
     * @param columnName the name of the cursor column that we want to get the value from
     * @return the value from cursor if the column exists, the implementation specific default value otherwise
     */
    boolean optBoolean(final String columnName);

    /**
     * Extracts the contents of a cursors Column as a Boolean.
     * If the column does not exist, it will return the fallback value;
     * <p/>
     * The logic is implementation specific.
     *
     * @param columnName the column name
     * @param fallback   the value to return if the cursor does not exist
     * @return the value from cursor if the column exists, null otherwise
     */
    boolean optBoolean(final String columnName, boolean fallback);

    /**
     * Extracts the contents of a cursors Column as a Boolean.
     * If the column does not exist, it will return null.
     * <p/>
     * Use this if you want to know if the column did not exist.
     * <p/>
     * The logic is implementation specific.
     *
     * @param columnName the column name
     * @return the value from cursor if the column exists, null otherwise
     */
    Boolean optBooleanAsWrapperType(final String columnName);

    /**
     * Extracts the contents of a cursors Column as a double.
     *
     * @param columnName the name of the cursor column that we want to get the value from
     * @return the value from cursor if the column exists, the implementation specific default value otherwise
     */
    double optDouble(final String columnName);

    /**
     * Extracts the contents of a cursors Column as a double.
     * If the column does not exist, it will return the fallback value;
     *
     * @param columnName the column name
     * @param fallback   the value to return if the cursor does not exist
     * @return the value from cursor if the column exists, the fallback otherwise
     */
    double optDouble(final String columnName, double fallback);

    /**
     * Extracts the contents of a cursors Column as a Double.
     * If the column does not exist, it will return null;
     * <p/>
     * Use this if you want to know if the column did not exist.
     *
     * @param columnName the column name
     * @return the value from cursor if the column exists, null otherwise
     */
    Double optDoubleAsWrapperType(final String columnName);

    /**
     * Extracts the contents of a cursors Column as a float.
     *
     * @param columnName the name of the cursor column that we want to get the value from
     * @return the value from cursor if the column exists, the implementation specific default value otherwise
     */
    float optFloat(final String columnName);

    /**
     * Extracts the contents of a cursors Column as a float.
     * If the column does not exist, it will return the fallback value;
     *
     * @param columnName the column name
     * @param fallback   the value to return if the cursor does not exist
     * @return the value from cursor if the column exists, the fallback otherwise
     */
    float optFloat(final String columnName, float fallback);

    /**
     * Extracts the contents of a cursors Column as a Float.
     * If the column does not exist, it will return null;
     * <p/>
     * Use this if you want to know if the column did not exist.
     *
     * @param columnName the column name
     * @return the value from cursor if the column exists, null otherwise
     */
    Float optFloatAsWrapperType(final String columnName);

    /**
     * Extracts the contents of a cursors Column as an int.
     *
     * @param columnName the name of the cursor column that we want to get the value from
     * @return the value from cursor if the column exists, the implementation specific default value otherwise
     */
    int optInt(final String columnName);

    /**
     * Extracts the contents of a cursors Column as an int.
     * If the column does not exist, it will return the fallback value;
     *
     * @param columnName the column name
     * @param fallback   the value to return if the cursor does not exist
     * @return the value from cursor if the column exists, the fallback otherwise
     */
    int optInt(final String columnName, int fallback);

    /**
     * Extracts the contents of a cursors Column as an Integer.
     * If the column does not exist, it will return null;
     * <p/>
     * Use this if you want to know if the column did not exist.
     *
     * @param columnName the column name
     * @return the value from cursor if the column exists, null otherwise
     */
    Integer optIntAsWrapperType(final String columnName);

    /**
     * Extracts the contents of a cursors Column as a long.
     *
     * @param columnName the name of the cursor column that we want to get the value from
     * @return the value from cursor if the column exists, the implementation specific default value otherwise
     */
    long optLong(final String columnName);

    /**
     * Extracts the contents of a cursors Column as a long.
     * If the column does not exist, it will return the fallback value;
     *
     * @param columnName the column name
     * @param fallback   the value to return if the cursor does not exist
     * @return the value from cursor if the column exists, the fallback otherwise
     */
    long optLong(final String columnName, long fallback);

    /**
     * Extracts the contents of a cursors Column as a Long.
     * If the column does not exist, it will return null;
     * <p/>
     * Use this if you want to know if the column did not exist.
     *
     * @param columnName the column name
     * @return the value from cursor if the column exists, null otherwise
     */
    Long optLongAsWrapperType(final String columnName);

    /**
     * Extracts the contents of a cursors Column as a short.
     *
     * @param columnName the name of the cursor column that we want to get the value from
     * @return the value from cursor if the column exists, the implementation specific default value otherwise
     */
    short optShort(final String columnName);

    /**
     * Extracts the contents of a cursors Column as a short.
     * If the column does not exist, it will return the fallback value;
     *
     * @param columnName the column name
     * @param fallback   the value to return if the cursor does not exist
     * @return the value from cursor if the column exists, the fallback otherwise
     */
    short optShort(final String columnName, short fallback);

    /**
     * Extracts the contents of a cursors Column as a Short.
     * If the column does not exist, it will return null;
     * <p/>
     * Use this if you want to know if the column did not exist.
     *
     * @param columnName the column name
     * @return the value from cursor if the column exists, null otherwise
     */
    Short optShortAsWrapperType(final String columnName);

    /**
     * Extracts the contents of a cursors Column as a String.
     * If the column does not exist, it will return the implementation specific default value
     *
     * @param columnName the name of the cursor column that we want to get the value from
     * @return the value from cursor if the column exists, the implementation specific default value
     */
    String optString(final String columnName);

    /**
     * Extracts the contents of a cursors Column as a String.
     * If the column does not exist, it will return the fallback value;
     * <p/>
     * Note that this only checks if the column exists. If your fallback is not null,
     * you can still get a null back if the column content is null.
     *
     * @param columnName the column name
     * @param fallback   the value to return if the cursor does not exist
     * @return the value from cursor if the column exists, the fallback otherwise
     */
    String optString(final String columnName, final String fallback);

}