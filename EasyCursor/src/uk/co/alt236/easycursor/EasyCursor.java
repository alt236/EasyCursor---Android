package uk.co.alt236.easycursor;

import android.database.Cursor;

public interface EasyCursor extends Cursor{

	/**
	 * Returns the value of the requested column as a byte array or throws
	 * IllegalArgumentException if the column doesn't exist.
	 *
	 * @param columnName the column name
	 * @return the value from cursor
	 * @throws IllegalArgumentException if the column doesn't exist
	 */
	public abstract byte[] getBlob(final String columnName);

	/**
	 * Returns the value of the requested column as a boolean or throws
	 * IllegalArgumentException if the column doesn't exist.
	 *
	 * The logic is implementation specific
	 *
	 * @param columnName the column name
	 * @return the value from cursor
	 * @throws IllegalArgumentException if the column doesn't exist
	 */
	public abstract boolean getBoolean(final String columnName);

	/**
	 * Returns the value of the requested column as a double or throws
	 * IllegalArgumentException if the column doesn't exist.
	 *
	 * @param columnName the column name
	 * @return the value from cursor
	 * @throws IllegalArgumentException if the column doesn't exist
	 */
	public abstract double getDouble(final String columnName);

	/**
	 * Returns the value of the requested column as a float or throws
	 * IllegalArgumentException if the column doesn't exist.
	 *
	 * @param columnName the column name
	 * @return the value from cursor
	 * @throws IllegalArgumentException if the column doesn't exist
	 */
	public abstract float getFloat(final String columnName);

	/**
	 * Returns the value of the requested column as an int or throws
	 * IllegalArgumentException if the column doesn't exist.
	 *
	 * @param columnName the column name
	 * @return the value from cursor
	 * @throws IllegalArgumentException if the column doesn't exist
	 */
	public abstract int getInt(final String columnName);

	/**
	 * Returns the value of the requested column as a long or throws
	 * IllegalArgumentException if the column doesn't exist.
	 *
	 * @param columnName the column name
	 * @return the value from cursor
	 * @throws IllegalArgumentException if the column doesn't exist
	 */
	public abstract long getLong(final String columnName);

	/**
	 * Gets the {@link EasyQueryModel} which produced this cursor (if any).
	 * If this cursor was not the produced via an {@link EasyQueryModel},
	 * the result is null;
	 *
	 * @return the query model
	 */
	public abstract EasyQueryModel getQueryModel();

	/**
	 * Returns the value of the requested column as a String or throws
	 * IllegalArgumentException if the column doesn't exist.
	 *
	 * @param columnName the column name
	 * @return the value from cursor
	 * @throws IllegalArgumentException if the column doesn't exist
	 */
	public abstract String getString(final String columnName);

	/**
     * Returns <code>true</code> if the value in the indicated column is null.
     *
     * @param columnName the the name of the column to check.
     * @return whether the column value is null.
     */
	public abstract boolean isNull(String columnName);

	/**
	 * Extracts the contents of a cursors Column as a Boolean.
	 * If the column does not exist, it will return null;
	 *
	 * The logic is implementation specific.
	 *
	 * @param columnName the name of the cursor column that we want to get the value from
	 * @return the value from cursor if the column exists, the implementation specific default value otherwise
	 */
	public abstract boolean optBoolean(final String columnName);

	/**
	 * Extracts the contents of a cursors Column as a Boolean.
	 * If the column does not exist, it will return the fallback value;
	 *
	 * The logic is implementation specific.
	 *
	 * @param columnName the column name
	 * @param fallback the value to return if the cursor does not exist
	 * @return the value from cursor if the column exists, null otherwise
	 */
	public abstract boolean optBoolean(final String columnName, boolean fallback);

	/**
	 * Extracts the contents of a cursors Column as a Boolean.
	 * If the column does not exist, it will return null.
	 *
	 * Use this if you want to know if the column did not exist.
	 *
	 * The logic is implementation specific.
	 *
	 * @param columnName the column name
	 * @return the value from cursor if the column exists, null otherwise
	 */
	public abstract Boolean optBooleanAsWrapperType(final String columnName);

	/**
	 * Extracts the contents of a cursors Column as a double.
	 *
	 * @param columnName the name of the cursor column that we want to get the value from
	 * @return the value from cursor if the column exists, the implementation specific default value otherwise
	 *
	 */
	public abstract double optDouble(final String columnName);

	/**
	 * Extracts the contents of a cursors Column as a double.
	 * If the column does not exist, it will return the fallback value;
	 *
	 * @param columnName the column name
	 * @param fallback the value to return if the cursor does not exist
	 * @return the value from cursor if the column exists, the fallback otherwise
	 */
	public abstract double optDouble(final String columnName, double fallback);

	/**
	 * Extracts the contents of a cursors Column as a Double.
	 * If the column does not exist, it will return null;
	 *
	 * Use this if you want to know if the column did not exist.
	 *
	 * @param columnName the column name
	 * @return the value from cursor if the column exists, null otherwise
	 */
	public abstract Double optDoubleAsWrapperType(final String columnName);

	/**
	 * Extracts the contents of a cursors Column as a float.
	 *
	 * @param columnName the name of the cursor column that we want to get the value from
	 * @return the value from cursor if the column exists, the implementation specific default value otherwise
	 */
	public abstract float optFloat(final String columnName);

	/**
	 * Extracts the contents of a cursors Column as a float.
	 * If the column does not exist, it will return the fallback value;
	 *
	 * @param columnName the column name
	 * @param fallback the value to return if the cursor does not exist
	 * @return the value from cursor if the column exists, the fallback otherwise
	 */
	public abstract float optFloat(final String columnName, float fallback);

	/**
	 * Extracts the contents of a cursors Column as a Float.
	 * If the column does not exist, it will return null;
	 *
	 * Use this if you want to know if the column did not exist.
	 *
	 * @param columnName the column name
	 * @return the value from cursor if the column exists, null otherwise
	 */
	public abstract Float optFloatAsWrapperType(final String columnName);

	/**
	 * Extracts the contents of a cursors Column as an int.
	 *
	 * @param columnName the name of the cursor column that we want to get the value from
	 * @return the value from cursor if the column exists, the implementation specific default value otherwise
	 */
	public abstract int optInt(final String columnName);

	/**
	 * Extracts the contents of a cursors Column as an int.
	 * If the column does not exist, it will return the fallback value;
	 *
	 * @param columnName the column name
	 * @param fallback the value to return if the cursor does not exist
	 * @return the value from cursor if the column exists, the fallback otherwise
	 */
	public abstract int optInt(final String columnName, int fallback);

	/**
	 * Extracts the contents of a cursors Column as an Integer.
	 * If the column does not exist, it will return null;
	 *
	 * Use this if you want to know if the column did not exist.
	 *
	 * @param columnName the column name
	 * @return the value from cursor if the column exists, null otherwise
	 */
	public abstract Integer optIntAsWrapperType(final String columnName);

	/**
	 * Extracts the contents of a cursors Column as a long.
	 *
	 * @param columnName the name of the cursor column that we want to get the value from
	 * @return the value from cursor if the column exists, the implementation specific default value otherwise
	 */
	public abstract long optLong(final String columnName);

	/**
	 * Extracts the contents of a cursors Column as a long.
	 * If the column does not exist, it will return the fallback value;
	 *
	 * @param columnName the column name
	 * @param fallback the value to return if the cursor does not exist
	 * @return the value from cursor if the column exists, the fallback otherwise
	 */
	public abstract long optLong(final String columnName, long fallback);

	/**
	 * Extracts the contents of a cursors Column as a Long.
	 * If the column does not exist, it will return null;
	 *
	 * Use this if you want to know if the column did not exist.
	 *
	 * @param columnName the column name
	 * @return the value from cursor if the column exists, null otherwise
	 */
	public abstract Long optLongAsWrapperType(final String columnName);

	/**
	 * Extracts the contents of a cursors Column as a String.
	 * If the column does not exist, it will return the implementation specific default value
	 *
	 * @param columnName the name of the cursor column that we want to get the value from
	 * @return the value from cursor if the column exists, the implementation specific default value
	 */
	public abstract String optString(final String columnName);

    /**
	 * Extracts the contents of a cursors Column as a String.
	 * If the column does not exist, it will return the fallback value;
	 *
	 * Note that this only checks if the column exists. If your fallback is not null,
	 * you can still get a null back if the column content is null.
	 *
	 * @param columnName the column name
	 * @param fallback the value to return if the cursor does not exist
	 * @return the value from cursor if the column exists, the fallback otherwise
	 */
	public abstract String optString(final String columnName, final String fallback);

}