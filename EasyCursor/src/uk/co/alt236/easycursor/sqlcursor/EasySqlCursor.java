package uk.co.alt236.easycursor.sqlcursor;

import java.util.Arrays;

import uk.co.alt236.easycursor.EasyCursor;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.util.Log;

public class EasySqlCursor extends CursorWrapper implements EasyCursor{
	private static final String TAG = "EasyCursor";
	private final static int COLUMN_NOT_PRESENT = -1;
	public static final String DEFAULT_STRING = null;
	public static final int DEFAULT_INT = 0;
	public static final long DEFAULT_LONG = 0l;
	public static final float DEFAULT_FLOAT = 0.0f;

	public static final double DEFAULT_DOUBLE = 0.0d;
	public static final boolean DEFAULT_BOOLEAN = false;

	private final EasySqlQueryModel mModel;

	private boolean mDebugEnabled;

	/**
	 * Use this constructor to easily convert any other Cursor to an EasyCursor
	 *
	 * @param cursor The EasyCursor
	 */
	public EasySqlCursor(final Cursor cursor){
		this(cursor, null);
	}

	protected EasySqlCursor(final Cursor cursor, final EasySqlQueryModel model) {
		super(cursor);
		mModel = model;
	}

	/**
	 * Use this constructor to easily change EasyCursor implementations
	 *
	 * @param cursor The EasyCursor
	 */
	public EasySqlCursor(final EasySqlCursor cursor){
		//
		// Sadly getWrappedCursor is only available in API 11
		//
		this(cursor, cursor.getQueryModel());
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

	/* (non-Javadoc)
	 * @see uk.co.alt236.easycursor.EasyCursor#getBlob(java.lang.String)
	 */
	@Override
	public byte[] getBlob(final String columnName) {
		return getBlob(getColumnIndexOrThrow(columnName));
	}


	/* (non-Javadoc)
	 * @see uk.co.alt236.easycursor.EasyCursor#getBoolean(java.lang.String)
	 */
	@Override
	public boolean getBoolean(final String columnName) {
		final int columnNumber = getColumnIndexOrThrow(columnName);
		return calcBoolean(columnNumber);
	}

	/* (non-Javadoc)
	 * @see uk.co.alt236.easycursor.EasyCursor#getDouble(java.lang.String)
	 */
	@Override
	public double getDouble(final String columnName) {
		return getDouble(getColumnIndexOrThrow(columnName));
	}

	/* (non-Javadoc)
	 * @see uk.co.alt236.easycursor.EasyCursor#getFloat(java.lang.String)
	 */
	@Override
	public float getFloat(final String columnName) {
		return getFloat(getColumnIndexOrThrow(columnName));
	}

	/* (non-Javadoc)
	 * @see uk.co.alt236.easycursor.EasyCursor#getInt(java.lang.String)
	 */
	@Override
	public int getInt(final String columnName) {
		return getInt(getColumnIndexOrThrow(columnName));
	}

	/* (non-Javadoc)
	 * @see uk.co.alt236.easycursor.EasyCursor#getLong(java.lang.String)
	 */
	@Override
	public long getLong(final String columnName) {
		return getLong(getColumnIndexOrThrow(columnName));
	}

	/**
	 * Gets the {@link EasySqlQueryModel} which produced this cursor (if any).
	 * If this cursor was not the produced via an {@link EasySqlQueryModel},
	 * the result is null;
	 *
	 * @return the query model
	 */
	@Override
	public EasySqlQueryModel getQueryModel() {
		return mModel;
	}

	/* (non-Javadoc)
	 * @see uk.co.alt236.easycursor.EasyCursor#getString(java.lang.String)
	 */
	@Override
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

	/* (non-Javadoc)
	 * @see uk.co.alt236.easycursor.EasyCursor#optBoolean(java.lang.String)
	 */
	@Override
	public boolean optBoolean(final String columnName) {
		return optBoolean(columnName, DEFAULT_BOOLEAN);
	}

	/* (non-Javadoc)
	 * @see uk.co.alt236.easycursor.EasyCursor#optBoolean(java.lang.String, boolean)
	 */
	@Override
	public boolean optBoolean(final String columnName, boolean fallback) {
		final int columnNo = getColumnIndex(columnName);

		if (isColumnPresent(columnName, columnNo)) {
			return calcBoolean(columnNo);
		} else {
			return fallback;
		}
	}

	/* (non-Javadoc)
	 * @see uk.co.alt236.easycursor.EasyCursor#optBooleanAsWrapperType(java.lang.String)
	 */
	@Override
	public Boolean optBooleanAsWrapperType(final String columnName) {
		final int columnNo = getColumnIndex(columnName);

		if (isColumnPresent(columnName, columnNo)) {
			return calcBoolean(columnNo) ? Boolean.TRUE : Boolean.FALSE;
		} else {
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see uk.co.alt236.easycursor.EasyCursor#optDouble(java.lang.String)
	 */
	@Override
	public double optDouble(final String columnName) {
		return optDouble(columnName, DEFAULT_DOUBLE);
	}

	/* (non-Javadoc)
	 * @see uk.co.alt236.easycursor.EasyCursor#optDouble(java.lang.String, double)
	 */
	@Override
	public double optDouble(final String columnName, double fallback) {
		final int columnNo = getColumnIndex(columnName);

		if (isColumnPresent(columnName, columnNo)) {
			return getDouble(columnNo);
		} else {
			return fallback;
		}
	}

	/* (non-Javadoc)
	 * @see uk.co.alt236.easycursor.EasyCursor#optDoubleAsWrapperType(java.lang.String)
	 */
	@Override
	public Double optDoubleAsWrapperType(final String columnName) {
		final int columnNo = getColumnIndex(columnName);

		if (isColumnPresent(columnName, columnNo)) {
			return getDouble(columnNo);
		} else {
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see uk.co.alt236.easycursor.EasyCursor#optFloat(java.lang.String)
	 */
	@Override
	public float optFloat(final String columnName) {
		return optFloat(columnName, DEFAULT_FLOAT);
	}

	/* (non-Javadoc)
	 * @see uk.co.alt236.easycursor.EasyCursor#optFloat(java.lang.String, float)
	 */
	@Override
	public float optFloat(final String columnName, float fallback) {
		final int columnNo = getColumnIndex(columnName);

		if (isColumnPresent(columnName, columnNo)) {
			return getFloat(columnNo);
		} else {
			return fallback;
		}
	}

	/* (non-Javadoc)
	 * @see uk.co.alt236.easycursor.EasyCursor#optFloatAsWrapperType(java.lang.String)
	 */
	@Override
	public Float optFloatAsWrapperType(final String columnName) {
		final int columnNo = getColumnIndex(columnName);

		if (isColumnPresent(columnName, columnNo)) {
			return getFloat(columnNo);
		} else {
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see uk.co.alt236.easycursor.EasyCursor#optInt(java.lang.String)
	 */
	@Override
	public int optInt(final String columnName) {
		return optInt(columnName, DEFAULT_INT);
	}

	/* (non-Javadoc)
	 * @see uk.co.alt236.easycursor.EasyCursor#optInt(java.lang.String, int)
	 */
	@Override
	public int optInt(final String columnName, int fallback) {
		final int columnNo = getColumnIndex(columnName);

		if (isColumnPresent(columnName, columnNo)) {
			return getInt(columnNo);
		} else {
			return fallback;
		}
	}

	/* (non-Javadoc)
	 * @see uk.co.alt236.easycursor.EasyCursor#optIntAsWrapperType(java.lang.String)
	 */
	@Override
	public Integer optIntAsWrapperType(final String columnName) {
		final int columnNo = getColumnIndex(columnName);

		if (isColumnPresent(columnName, columnNo)) {
			return getInt(columnNo);
		} else {
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see uk.co.alt236.easycursor.EasyCursor#optLong(java.lang.String)
	 */
	@Override
	public long optLong(final String columnName) {
		return optLong(columnName, DEFAULT_LONG);
	}

	/* (non-Javadoc)
	 * @see uk.co.alt236.easycursor.EasyCursor#optLong(java.lang.String, long)
	 */
	@Override
	public long optLong(final String columnName, long fallback) {
		final int columnNo = getColumnIndex(columnName);

		if (isColumnPresent(columnName, columnNo)) {
			return getLong(columnNo);
		} else {
			return fallback;
		}
	}

	/* (non-Javadoc)
	 * @see uk.co.alt236.easycursor.EasyCursor#optLongAsWrapperType(java.lang.String)
	 */
	@Override
	public Long optLongAsWrapperType(final String columnName) {
		final int columnNo = getColumnIndex(columnName);

		if (isColumnPresent(columnName, columnNo)) {
			return getLong(columnNo);
		} else {
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see uk.co.alt236.easycursor.EasyCursor#optString(java.lang.String)
	 */
	@Override
	public String optString(final String columnName) {
		return optString(columnName, DEFAULT_STRING);
	}

	/* (non-Javadoc)
	 * @see uk.co.alt236.easycursor.EasyCursor#optString(java.lang.String, java.lang.String)
	 */
	@Override
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