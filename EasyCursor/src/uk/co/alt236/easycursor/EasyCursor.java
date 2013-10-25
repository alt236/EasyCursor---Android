package uk.co.alt236.easycursor;

import java.util.Arrays;

import android.database.Cursor;
import android.database.CursorWrapper;

public class EasyCursor extends CursorWrapper{
	private final static int COLUMN_NOT_PRESENT = -1;
	private final EasyQueryModel mModel;
	
	public EasyCursor(final Cursor cursor){
		super(cursor);
		mModel = null;
	}
	
	public EasyCursor(final Cursor cursor, final EasyQueryModel model) {
		super(cursor);
		mModel = model;
	}
	
	/**
	 * Returns the value of the requested column as a byte array or throws 
	 * IllegalArgumentException if the column doesn't exist. 
	 *
	 * @param columnName the column name
	 * @return the value from cursor
	 */
	public byte[] getBlob(final String columnName) {
		return getBlob(getColumnIndexOrThrow(columnName));
	}
	
	/**
	 * Returns the value of the requested column as a boolean or throws 
	 * IllegalArgumentException if the column doesn't exist. 
	 *
	 * The logic used to calculate the boolean is the following:
	 * if (value_as_int == 1) ? true : false;
	 *
	 * @param columnName the column name
	 * @return the value from cursor
	 */
	public boolean getBoolean(final String columnName) {
		final int value =  getInt(getColumnIndexOrThrow(columnName));
		return (value == 1) ? true : false;
	}
	
	/**
	 * Returns the value of the requested column as a double or throws 
	 * IllegalArgumentException if the column doesn't exist. 
	 *
	 * @param columnName the column name
	 * @return the value from cursor
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
	 */
	public String getString(final String columnName) {
		return getString(getColumnIndexOrThrow(columnName));
	}
	
	/**
	 * Extracts the contents of a cursors Column as a Boolean.
	 * If the column does not exist, it will return null;
	 *
	 * The logic used to calculate the boolean is the following:
	 * if (value_as_int == 1) ? true : false;
	 *
	 * @param columnName the name of the cursor column that we want to get the value from
	 * @return the value from cursor if the column exists, false otherwise
	 */
	public boolean optBoolean(final String columnName) {
		return optBoolean(columnName, false);
	}

	/**
	 * Extracts the contents of a cursors Column as a Boolean.
	 * If the column does not exist, it will return the fallback value;
	 *
	 * The logic used to calculate the boolean is the following:
	 * if (value_as_int == 1) ? true : false;
	 *
	 * @param columnName the column name
	 * @param fallback the value to return if the cursor does not exist
	 * @return the value from cursor if the column exists, null otherwise
	 */
	public boolean optBoolean(final String columnName, boolean fallback) {
		final int columnNo = getColumnIndex(columnName);
		
		if (columnNo == COLUMN_NOT_PRESENT) {
			return fallback;
		} else {
			return (getInt(columnNo) == 1) ? true : false;
		}
	}
	
	/**
	 * Extracts the contents of a cursors Column as a Double.
	 * If the column does not exist, it will return null;
	 *
	 * @param columnName the name of the cursor column that we want to get the value from
	 * @return the value from cursor if the column exists, 0.0 otherwise as per 
	 * http://www.sqlite.org/c3ref/column_blob.html
	 */
	public double optDouble(final String columnName) {
		return optDouble(columnName, 0.0);
	}
	
	/**
	 * Extracts the contents of a cursors Column as a Double.
	 * If the column does not exist, it will return the fallback value;
	 *
	 * @param columnName the column name
	 * @param fallback the value to return if the cursor does not exist
	 * @return the value from cursor if the column exists, the fallback otherwise
	 */
	public double optDouble(final String columnName, double fallback) {
		final int columnNo = getColumnIndex(columnName);

		if (columnNo == COLUMN_NOT_PRESENT) {
			return fallback;
		} else {
			return getDouble(columnNo);
		}
	}
	
	/**
	 * Extracts the contents of a cursors Column as a Float.
	 * If the column does not exist, it will return null;
	 *
	 * @param columnName the name of the cursor column that we want to get the value from
	 * @return the value from cursor if the column exists, 0.0 otherwise as per 
	 * http://www.sqlite.org/c3ref/column_blob.html
	 */
	public float optFloat(final String columnName) {
		return optFloat(columnName, 0.0f);
	}
	
	/**
	 * Extracts the contents of a cursors Column as a Float.
	 * If the column does not exist, it will return the fallback value;
	 *
	 * @param columnName the column name
	 * @param fallback the value to return if the cursor does not exist
	 * @return the value from cursor if the column exists, the fallback otherwise
	 */
	public float optFloat(final String columnName, float fallback) {
		final int columnNo = getColumnIndex(columnName);

		if (columnNo == COLUMN_NOT_PRESENT) {
			return fallback;
		} else {
			return getFloat(columnNo);
		}
	}
	
	/**
	 * Extracts the contents of a cursors Column as an Integer.
	 * If the column does not exist, it will return null;
	 *
	 * @param columnName the name of the cursor column that we want to get the value from
	 * @return the value from cursor if the column exists, 0 otherwise as per 
	 * http://www.sqlite.org/c3ref/column_blob.html
	 */
	public int optInteger(final String columnName) {
		return optInteger(columnName, 0);
	}
	
	
	/**
	 * Extracts the contents of a cursors Column as an Integer.
	 * If the column does not exist, it will return the fallback value;
	 *
	 * @param columnName the column name
	 * @param fallback the value to return if the cursor does not exist
	 * @return the value from cursor if the column exists, the fallback otherwise
	 */
	public int optInteger(final String columnName, int fallback) {
		final int columnNo = getColumnIndex(columnName);

		if (columnNo == COLUMN_NOT_PRESENT) {
			return fallback;
		} else {
			return getInt(columnNo);
		}
	}

	/**
	 * Extracts the contents of a cursors Column as a Long.
	 * If the column does not exist, it will return null;
	 *
	 * @param columnName the name of the cursor column that we want to get the value from
	 * @return the value from cursor if the column exists, 0 otherwise as per 
	 * http://www.sqlite.org/c3ref/column_blob.html
	 */
	public long optLong(final String columnName) {
		return optLong(columnName, 0);
	}

	/**
	 * Extracts the contents of a cursors Column as a Long.
	 * If the column does not exist, it will return the fallback value;
	 *
	 * @param columnName the column name
	 * @param fallback the value to return if the cursor does not exist
	 * @return the value from cursor if the column exists, the fallback otherwise
	 */
	public long optLong(final String columnName, long fallback) {
		final int columnNo = getColumnIndex(columnName);

		if (columnNo == COLUMN_NOT_PRESENT) {
			return fallback;
		} else {
			return getLong(columnNo);
		}
	}
	
	/**
	 * Extracts the contents of a cursors Column as a String.
	 * If the column does not exist, it will return null;
	 *
	 * @param columnName the name of the cursor column that we want to get the value from
	 * @return the value from cursor if the column exists, null otherwise as per 
	 * http://www.sqlite.org/c3ref/column_blob.html
	 */
	public String optString(final String columnName) {
		return optString(columnName, null);
	}

	/**
	 * Extracts the contents of a cursors Column as a String.
	 * If the column does not exist, it will return the fallback value;
	 *
	 * @param columnName the column name
	 * @param fallback the value to return if the cursor does not exist
	 * @return the value from cursor if the column exists, the fallback otherwise
	 */
	public String optString(final String columnName, String fallback) {
		final int columnNo = getColumnIndex(columnName);

		if (columnNo == COLUMN_NOT_PRESENT) {
			return fallback;
		} else {
			return getString(columnNo);
		}
	}
	
	@Override
	public String toString() {
		return "EasyCursor [mModel=" + mModel + ", isClosed()=" + isClosed()
				+ ", getCount()=" + getCount() + ", getColumnCount()="
				+ getColumnCount() + ", getColumnNames()="
				+ Arrays.toString(getColumnNames()) + ", getPosition()="
				+ getPosition() + "]";
	}
}