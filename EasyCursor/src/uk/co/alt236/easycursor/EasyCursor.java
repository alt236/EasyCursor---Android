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
	 * @return the value from cursor if the column exists, null otherwise
	 */
	public Boolean optBoolean(final String columnName) {
		return optBoolean(columnName, null);
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
	public Boolean optBoolean(final String columnName, Boolean fallback) {
		final int columnNo = getColumnIndex(columnName);
		
		if (columnNo == COLUMN_NOT_PRESENT) {
			return fallback;
		} else {
			return (getInt(columnNo) == 1) ? Boolean.TRUE : Boolean.FALSE;
		}
	}
	
	/**
	 * Extracts the contents of a cursors Column as a Double.
	 * If the column does not exist, it will return null;
	 *
	 * @param columnName the name of the cursor column that we want to get the value from
	 * @return the value from cursor if the column exists, null otherwise
	 */
	public Double optDouble(final String columnName) {
		return optDouble(columnName, null);
	}
	
	/**
	 * Extracts the contents of a cursors Column as a Double.
	 * If the column does not exist, it will return the fallback value;
	 *
	 * @param columnName the column name
	 * @param fallback the value to return if the cursor does not exist
	 * @return the value from cursor if the column exists, the fallback otherwise
	 */
	public Double optDouble(final String columnName, Double fallback) {
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
	 * @return the value from cursor if the column exists, null otherwise
	 */
	public Float optFloat(final String columnName) {
		return optFloat(columnName, null);
	}
	
	/**
	 * Extracts the contents of a cursors Column as a Float.
	 * If the column does not exist, it will return the fallback value;
	 *
	 * @param columnName the column name
	 * @param fallback the value to return if the cursor does not exist
	 * @return the value from cursor if the column exists, the fallback otherwise
	 */
	public Float optFloat(final String columnName, Float fallback) {
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
	 * @return the value from cursor if the column exists, null otherwise
	 */
	public Integer optInteger(final String columnName) {
		return optInteger(columnName, null);
	}
	
	
	/**
	 * Extracts the contents of a cursors Column as an Integer.
	 * If the column does not exist, it will return the fallback value;
	 *
	 * @param columnName the column name
	 * @param fallback the value to return if the cursor does not exist
	 * @return the value from cursor if the column exists, the fallback otherwise
	 */
	public Integer optInteger(final String columnName, Integer fallback) {
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
	 * @return the value from cursor if the column exists, null otherwise
	 */
	public Long optLong(final String columnName) {
		return optLong(columnName, null);
	}

	/**
	 * Extracts the contents of a cursors Column as a Long.
	 * If the column does not exist, it will return the fallback value;
	 *
	 * @param columnName the column name
	 * @param fallback the value to return if the cursor does not exist
	 * @return the value from cursor if the column exists, the fallback otherwise
	 */
	public Long optLong(final String columnName, Long fallback) {
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
	 * @return the value from cursor if the column exists, null otherwise
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

		if (columnNo == -1) {
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