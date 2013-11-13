package uk.co.alt236.easycursor;

import java.util.Arrays;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.util.Log;

public class EasyCursor extends CursorWrapper{
	private static final String TAG = "EasyCursor";
	private final static int COLUMN_NOT_PRESENT = -1;
	
	public static final String DEFAULT_STRING = null;
	public static final int DEFAULT_INT = 0;
	public static final long DEFAULT_LONG = 0l;
	public static final float DEFAULT_FLOAT = 0.0f;
	public static final double DEFAULT_DOUBLE = 0.0d;
	public static final boolean DEFAULT_BOOLEAN = false;

	private final EasyQueryModel mModel;
	private boolean mDebugEnabled;

	public EasyCursor(final Cursor cursor){
		super(cursor);
		mModel = null;
	}

	/**
	 * Use this constructor to easily change EasyCursor implementations
	 * 
	 * @param cursor The EasyCursor
	 */
	public EasyCursor(final EasyCursor cursor){
		super(cursor);
		mModel = cursor.getQueryModel();
	}
	
	public EasyCursor(final Cursor cursor, final EasyQueryModel model) {
		super(cursor);
		mModel = model;
	}

	/**
	 * Performs the necessary calculations to assess the value of a boolean
	 * 
	 * The default logic used to calculate the boolean is the following:
	 * if (value_as_int == 1) ? true : false;
	 * 
	 * @param columnNumber the number of the column containing the value to assess
	 * @return true if the value of the boolean is true, false otherwise
	 */
	protected boolean calcBoolean(int columnNumber){
		final int value = getInt(columnNumber);
		return (value == 1) ? true : false;
	}

	/**
	 * Returns the value of the requested column as a byte array or throws 
	 * IllegalArgumentException if the column doesn't exist. 
	 *
	 * @param columnName the column name
	 * @return the value from cursor
	 * @throws IllegalArgumentException if the column doesn't exist
	 */
	public byte[] getBlob(final String columnName) {
		return getBlob(getColumnIndexOrThrow(columnName));
	}

	
	/**
	 * Returns the value of the requested column as a boolean or throws 
	 * IllegalArgumentException if the column doesn't exist. 
	 *
	 * The logic is defined in {@link #calcBoolean(int)}
	 * 
	 * @param columnName the column name
	 * @return the value from cursor
	 * @throws IllegalArgumentException if the column doesn't exist
	 */
	public boolean getBoolean(final String columnName) {
		final int columnNumber = getColumnIndexOrThrow(columnName);
		return calcBoolean(columnNumber);
	}
	
	
	/**
	 * Returns the value of the requested column as a double or throws 
	 * IllegalArgumentException if the column doesn't exist. 
	 *
	 * @param columnName the column name
	 * @return the value from cursor
	 * @throws IllegalArgumentException if the column doesn't exist
	 */
	public double getDouble(final String columnName) {
		return getDouble(getColumnIndexOrThrow(columnName));
	}

	/**
	 * Returns the value of the requested column as a float or throws 
	 * IllegalArgumentException if the column doesn't exist. 
	 *
	 * @param columnName the column name
	 * @return the value from cursor
	 * @throws IllegalArgumentException if the column doesn't exist
	 */
	public float getFloat(final String columnName) {
		return getFloat(getColumnIndexOrThrow(columnName));
	}

	/**
	 * Returns the value of the requested column as an int or throws 
	 * IllegalArgumentException if the column doesn't exist. 
	 *
	 * @param columnName the column name
	 * @return the value from cursor
	 * @throws IllegalArgumentException if the column doesn't exist
	 */
	public int getInt(final String columnName) {
		return getInt(getColumnIndexOrThrow(columnName));
	}

	/**
	 * Returns the value of the requested column as a long or throws 
	 * IllegalArgumentException if the column doesn't exist. 
	 *
	 * @param columnName the column name
	 * @return the value from cursor
	 * @throws IllegalArgumentException if the column doesn't exist
	 */
	public long getLong(final String columnName) {
		return getLong(getColumnIndexOrThrow(columnName));
	}

	/**
	 * Gets the {@link EasyQueryModel} which produced this cursor (if any).
	 * If this cursor was not the produced via an {@link EasyQueryModel},
	 * the result is null;
	 *
	 * @return the query model
	 */
	public EasyQueryModel getQueryModel() {
		return mModel;
	}

	/**
	 * Returns the value of the requested column as a String or throws 
	 * IllegalArgumentException if the column doesn't exist. 
	 *
	 * @param columnName the column name
	 * @return the value from cursor
	 * @throws IllegalArgumentException if the column doesn't exist
	 */
	public String getString(final String columnName) {
		return getString(getColumnIndexOrThrow(columnName));
	}

	protected boolean isColumnPresent(final String columnName, final int columnNo){
		if(columnNo == COLUMN_NOT_PRESENT){
			if(mDebugEnabled){
				Log.w(TAG, "Column '" + columnName + "' is not present in Cursor - " + getPosition() + "/" + getCount());
			}
			return false;
		} else {
			return true;
		}
	}

	public boolean isDebugEnabled(){
		return mDebugEnabled;
	}

	/**
	 * Extracts the contents of a cursors Column as a Boolean.
	 * If the column does not exist, it will return null;
	 *
	 * The logic is defined in {@link #calcBoolean(int)}
	 *
	 * @param columnName the name of the cursor column that we want to get the value from
	 * @return the value from cursor if the column exists, {@value #DEFAULT_BOOLEAN} otherwise
	 */
	public boolean optBoolean(final String columnName) {
		return optBoolean(columnName, DEFAULT_BOOLEAN);
	}

	/**
	 * Extracts the contents of a cursors Column as a Boolean.
	 * If the column does not exist, it will return the fallback value;
	 *
	 * The logic is defined in {@link #calcBoolean(int)}
	 * 
	 * @param columnName the column name
	 * @param fallback the value to return if the cursor does not exist
	 * @return the value from cursor if the column exists, null otherwise
	 */
	public boolean optBoolean(final String columnName, boolean fallback) {
		final int columnNo = getColumnIndex(columnName);

		if (isColumnPresent(columnName, columnNo)) {
			return calcBoolean(columnNo);
		} else {
			return fallback;
		}
	}

	/**
	 * Extracts the contents of a cursors Column as a Boolean.
	 * If the column does not exist, it will return null.
	 * 
	 * Use this if you want to know if the column did not exist.
	 *
	 * The logic is defined in {@link #calcBoolean(int)}
	 * 
	 * @param columnName the column name
	 * @return the value from cursor if the column exists, null otherwise
	 */
	public Boolean optBooleanAsWrapperType(final String columnName) {
		final int columnNo = getColumnIndex(columnName);

		if (isColumnPresent(columnName, columnNo)) {
			return calcBoolean(columnNo) ? Boolean.TRUE : Boolean.FALSE;
		} else {
			return null;
		}
	}
	
	/**
	 * Extracts the contents of a cursors Column as a double.
	 *
	 * @param columnName the name of the cursor column that we want to get the value from
	 * @return the value from cursor if the column exists, {@value #DEFAULT_DOUBLE} otherwise as per 
	 * http://www.sqlite.org/c3ref/column_blob.html
	 * 
	 */
	public double optDouble(final String columnName) {
		return optDouble(columnName, DEFAULT_DOUBLE);
	}

	/**
	 * Extracts the contents of a cursors Column as a double.
	 * If the column does not exist, it will return the fallback value;
	 *
	 * @param columnName the column name
	 * @param fallback the value to return if the cursor does not exist
	 * @return the value from cursor if the column exists, the fallback otherwise
	 */
	public double optDouble(final String columnName, double fallback) {
		final int columnNo = getColumnIndex(columnName);

		if (isColumnPresent(columnName, columnNo)) {
			return getDouble(columnNo);
		} else {
			return fallback;
		}
	}

	/**
	 * Extracts the contents of a cursors Column as a Double.
	 * If the column does not exist, it will return null;
	 * 
	 * Use this if you want to know if the column did not exist.
	 *
	 * @param columnName the column name
	 * @return the value from cursor if the column exists, null otherwise
	 */
	public Double optDoubleAsWrapperType(final String columnName) {
		final int columnNo = getColumnIndex(columnName);

		if (isColumnPresent(columnName, columnNo)) {
			return getDouble(columnNo);
		} else {
			return null;
		}
	}
	
	/**
	 * Extracts the contents of a cursors Column as a float.
	 *
	 * @param columnName the name of the cursor column that we want to get the value from
	 * @return the value from cursor if the column exists, {@value #DEFAULT_FLOAT} otherwise.
	 */
	public float optFloat(final String columnName) {
		return optFloat(columnName, DEFAULT_FLOAT);
	}

	/**
	 * Extracts the contents of a cursors Column as a float.
	 * If the column does not exist, it will return the fallback value;
	 *
	 * @param columnName the column name
	 * @param fallback the value to return if the cursor does not exist
	 * @return the value from cursor if the column exists, the fallback otherwise
	 */
	public float optFloat(final String columnName, float fallback) {
		final int columnNo = getColumnIndex(columnName);

		if (isColumnPresent(columnName, columnNo)) {
			return getFloat(columnNo);
		} else {
			return fallback;
		}
	}

	/**
	 * Extracts the contents of a cursors Column as a Float.
	 * If the column does not exist, it will return null;
	 * 
	 * Use this if you want to know if the column did not exist.
	 *
	 * @param columnName the column name
	 * @return the value from cursor if the column exists, null otherwise
	 */
	public Float optFloatAsWrapperType(final String columnName) {
		final int columnNo = getColumnIndex(columnName);

		if (isColumnPresent(columnName, columnNo)) {
			return getFloat(columnNo);
		} else {
			return null;
		}
	}
	
	/**
	 * Extracts the contents of a cursors Column as an int.
	 *
	 * @param columnName the name of the cursor column that we want to get the value from
	 * @return the value from cursor if the column exists, {@value #DEFAULT_INT} otherwise.
	 */
	public int optInt(final String columnName) {
		return optInt(columnName, DEFAULT_INT);
	}

	/**
	 * Extracts the contents of a cursors Column as an int.
	 * If the column does not exist, it will return the fallback value;
	 *
	 * @param columnName the column name
	 * @param fallback the value to return if the cursor does not exist
	 * @return the value from cursor if the column exists, the fallback otherwise
	 */
	public int optInt(final String columnName, int fallback) {
		final int columnNo = getColumnIndex(columnName);

		if (isColumnPresent(columnName, columnNo)) {
			return getInt(columnNo);
		} else {
			return fallback;
		}
	}

	/**
	 * Extracts the contents of a cursors Column as an Integer.
	 * If the column does not exist, it will return null;
	 * 
	 * Use this if you want to know if the column did not exist.
	 *
	 * @param columnName the column name
	 * @return the value from cursor if the column exists, null otherwise
	 */
	public Integer optIntAsWrapperType(final String columnName) {
		final int columnNo = getColumnIndex(columnName);

		if (isColumnPresent(columnName, columnNo)) {
			return getInt(columnNo);
		} else {
			return null;
		}
	}
	
	/**
	 * Extracts the contents of a cursors Column as a long.
	 *
	 * @param columnName the name of the cursor column that we want to get the value from
	 * @return the value from cursor if the column exists, {@value #DEFAULT_LONG} otherwise.
	 */
	public long optLong(final String columnName) {
		return optLong(columnName, DEFAULT_LONG);
	}

	/**
	 * Extracts the contents of a cursors Column as a long.
	 * If the column does not exist, it will return the fallback value;
	 *
	 * @param columnName the column name
	 * @param fallback the value to return if the cursor does not exist
	 * @return the value from cursor if the column exists, the fallback otherwise
	 */
	public long optLong(final String columnName, long fallback) {
		final int columnNo = getColumnIndex(columnName);

		if (isColumnPresent(columnName, columnNo)) {
			return getLong(columnNo);
		} else {
			return fallback;
		}
	}

	/**
	 * Extracts the contents of a cursors Column as a Long.
	 * If the column does not exist, it will return null;
	 * 
	 * Use this if you want to know if the column did not exist.
	 *
	 * @param columnName the column name
	 * @return the value from cursor if the column exists, null otherwise
	 */
	public Long optLongAsWrapperType(final String columnName) {
		final int columnNo = getColumnIndex(columnName);

		if (isColumnPresent(columnName, columnNo)) {
			return getLong(columnNo);
		} else {
			return null;
		}
	}
	
	/**
	 * Extracts the contents of a cursors Column as a String.
	 * If the column does not exist, it will return {@value #DEFAULT_STRING};
	 *
	 * @param columnName the name of the cursor column that we want to get the value from
	 * @return the value from cursor if the column exists, {@value #DEFAULT_STRING} otherwise.
	 */
	public String optString(final String columnName) {
		return optString(columnName, DEFAULT_STRING);
	}

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
	public String optString(final String columnName, final String fallback) {
		final int columnNo = getColumnIndex(columnName);

		if (isColumnPresent(columnName, columnNo)) {
			return getString(columnNo);
		} else {
			return fallback;
		}
	}

	public void setDebugEnabled(boolean enabled){
		mDebugEnabled = enabled;
	}

	@Override
	public String toString() {
		return "EasyCursor [mModel=" + mModel + ", mDebugEnabled="
				+ mDebugEnabled + ", isClosed()=" + isClosed()
				+ ", getCount()=" + getCount() + ", getColumnCount()="
				+ getColumnCount() + ", getColumnNames()="
				+ Arrays.toString(getColumnNames()) + ", getPosition()="
				+ getPosition() + "]";
	}
}