/*
 * ***************************************************************************
 * Copyright 2015 Alexandros Schillings
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ***************************************************************************
 *
 */

package uk.co.alt236.easycursor.sqlcursor;

import android.annotation.TargetApi;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.util.Log;

import java.util.Arrays;

import uk.co.alt236.easycursor.EasyCursor;
import uk.co.alt236.easycursor.sqlcursor.querymodels.SqlQueryModel;

public class EasySqlCursor extends CursorWrapper implements EasyCursor {
    public static final String DEFAULT_STRING = null;
    public static final int DEFAULT_INT = 0;
    public static final long DEFAULT_LONG = 0l;
    public static final float DEFAULT_FLOAT = 0.0f;
    public static final double DEFAULT_DOUBLE = 0.0d;
    public static final short DEFAULT_SHORT = 0;
    public static final boolean DEFAULT_BOOLEAN = false;

    private static final BooleanLogic DEFAULT_BOOLEAN_LOGIC = new DefaultBooleanLogic();
    private static final String TAG = EasySqlCursor.class.getSimpleName();
    private final static int COLUMN_NOT_PRESENT = -1;
    private final BooleanLogic mBooleanLogic;
    private final SqlQueryModel mModel;

    private boolean mDebugEnabled;

    /**
     * Use this constructor to easily convert any other Cursor to an EasyCursor
     *
     * @param cursor The EasyCursor
     */
    public EasySqlCursor(final Cursor cursor) {
        this(cursor, null, null);
    }

    /**
     * Use this constructor to easily convert any other Cursor to an EasyCursor while using a custom Boolean logic resolution
     *
     * @param cursor       The EasyCursor
     * @param booleanLogic The logic to use to resolve booleans. Passing null will use {@link DefaultBooleanLogic}
     */
    public EasySqlCursor(final Cursor cursor, final BooleanLogic booleanLogic) {
        this(cursor, null, booleanLogic);
    }

    /**
     * Construct an {@link EasySqlCursor}.
     * This constructor is internally called by {@link SqlQueryModel#execute(SQLiteDatabase, BooleanLogic)}
     * and is required to exist in any Subclasses of {@link EasySqlCursor} if you are planning to use {@link SqlQueryModel#execute(SQLiteDatabase, Class, BooleanLogic)}
     * to execute the queries.
     *
     * @param cursor       The EasyCursor
     * @param model        The {@link SqlQueryModel} used to produce this cursor
     * @param booleanLogic The logic to use to resolve booleans. Passing null will use {@link DefaultBooleanLogic}
     */
    public EasySqlCursor(final Cursor cursor, final SqlQueryModel model, final BooleanLogic booleanLogic) {
        super(cursor);
        mModel = model;
        if (booleanLogic == null) {
            mBooleanLogic = DEFAULT_BOOLEAN_LOGIC;
        } else {
            mBooleanLogic = booleanLogic;
        }
    }

    /**
     * Performs the necessary calculations to assess the value of a boolean
     * <p>
     * The default logic used to calculate the boolean is defined in {@link DefaultBooleanLogic}
     *
     * @param columnNumber the number of the column containing the value to assess
     * @return true if the value of the boolean is true, false otherwise
     */
    private boolean calcBoolean(final int columnNumber) {
        return mBooleanLogic.isTrue(this, columnNumber);
    }

    /* (non-Javadoc)
     * @see uk.co.alt236.easycursor.EasyCursor#getBlob(java.lang.String)
     */
    @Override
    public byte[] getBlob(final String columnName) {
        return getBlob(getColumnIndexOrThrow(columnName));
    }

    /**
     * Returns the value of the requested column as a boolean or throws
     * IllegalArgumentException if the column doesn't exist.
     * <p>
     * The default logic is defined in {@link DefaultBooleanLogic}
     *
     * @param columnName the column name
     * @return the value from cursor
     * @throws IllegalArgumentException if the column doesn't exist
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
     * Gets the {@link SqlQueryModel} which produced this cursor (if any).
     * If this cursor was not the produced via an {@link SqlQueryModel},
     * the result is null;
     *
     * @return the query model
     */
    @Override
    public SqlQueryModel getQueryModel() {
        return mModel;
    }

    @Override
    public short getShort(final String columnName) {
        return getShort(getColumnIndexOrThrow(columnName));
    }

    /* (non-Javadoc)
     * @see uk.co.alt236.easycursor.EasyCursor#getString(java.lang.String)
     */
    @Override
    public String getString(final String columnName) {
        return getString(getColumnIndexOrThrow(columnName));
    }

    protected boolean isColumnPresent(final String columnName, final int columnNo) {
        if (columnNo == COLUMN_NOT_PRESENT) {
            if (mDebugEnabled) {
                Log.w(TAG, "Column '" + columnName + "' is not present in Cursor - " + getPosition() + "/" + getCount());
            }
            return false;
        } else {
            return true;
        }
    }

    public boolean isDebugEnabled() {
        return mDebugEnabled;
    }

    public void setDebugEnabled(final boolean enabled) {
        mDebugEnabled = enabled;
    }

    @Override
    public boolean isNull(final String columnName) {
        return isNull(getColumnIndex(columnName));
    }

    /**
     * Extracts the contents of a cursors Column as a Boolean.
     * If the column does not exist, it will return null;
     * <p>
     * The logic is defined in {@link #calcBoolean(int)}
     *
     * @param columnName the name of the cursor column that we want to get the value from
     * @return the value from cursor if the column exists, {@link #DEFAULT_BOOLEAN} otherwise
     */
    @Override
    public boolean optBoolean(final String columnName) {
        return optBoolean(columnName, DEFAULT_BOOLEAN);
    }

    /**
     * Extracts the contents of a cursors Column as a Boolean.
     * If the column does not exist, it will return the fallback value;
     * <p>
     * The logic is defined in {@link #calcBoolean(int)}
     *
     * @param columnName the column name
     * @param fallback   the value to return if the cursor does not exist
     * @return the value from cursor if the column exists, null otherwise
     */
    @Override
    public boolean optBoolean(final String columnName, final boolean fallback) {
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
     * <p>
     * Use this if you want to know if the column did not exist.
     * <p>
     * The logic is defined in {@link #calcBoolean(int)}
     *
     * @param columnName the column name
     * @return the value from cursor if the column exists, null otherwise
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

    /**
     * Extracts the contents of a cursors Column as a double.
     *
     * @param columnName the name of the cursor column that we want to get the value from
     * @return the value from cursor if the column exists, {@link #DEFAULT_DOUBLE} otherwise.
     */
    @Override
    public double optDouble(final String columnName) {
        return optDouble(columnName, DEFAULT_DOUBLE);
    }

    /* (non-Javadoc)
     * @see uk.co.alt236.easycursor.EasyCursor#optDouble(java.lang.String, double)
     */
    @Override
    public double optDouble(final String columnName, final double fallback) {
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

    /**
     * Extracts the contents of a cursors Column as a float.
     *
     * @param columnName the name of the cursor column that we want to get the value from
     * @return the value from cursor if the column exists, {@link #DEFAULT_FLOAT} otherwise.
     */
    @Override
    public float optFloat(final String columnName) {
        return optFloat(columnName, DEFAULT_FLOAT);
    }

    /* (non-Javadoc)
     * @see uk.co.alt236.easycursor.EasyCursor#optFloat(java.lang.String, float)
     */
    @Override
    public float optFloat(final String columnName, final float fallback) {
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

    /**
     * Extracts the contents of a cursors Column as an int.
     *
     * @param columnName the name of the cursor column that we want to get the value from
     * @return the value from cursor if the column exists, {@link #DEFAULT_INT} otherwise.
     */
    @Override
    public int optInt(final String columnName) {
        return optInt(columnName, DEFAULT_INT);
    }

    /* (non-Javadoc)
     * @see uk.co.alt236.easycursor.EasyCursor#optInt(java.lang.String, int)
     */
    @Override
    public int optInt(final String columnName, final int fallback) {
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

    /**
     * Extracts the contents of a cursors Column as a long.
     *
     * @param columnName the name of the cursor column that we want to get the value from
     * @return the value from cursor if the column exists, {@link #DEFAULT_LONG} otherwise.
     */
    @Override
    public long optLong(final String columnName) {
        return optLong(columnName, DEFAULT_LONG);
    }

    /* (non-Javadoc)
     * @see uk.co.alt236.easycursor.EasyCursor#optLong(java.lang.String, long)
     */
    @Override
    public long optLong(final String columnName, final long fallback) {
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

    @Override
    public short optShort(final String columnName) {
        return optShort(columnName, DEFAULT_SHORT);
    }

    @Override
    public short optShort(final String columnName, final short fallback) {
        final int columnNo = getColumnIndex(columnName);

        if (isColumnPresent(columnName, columnNo)) {
            return getShort(columnNo);
        } else {
            return fallback;
        }
    }

    @Override
    public Short optShortAsWrapperType(final String columnName) {
        final int columnNo = getColumnIndex(columnName);

        if (isColumnPresent(columnName, columnNo)) {
            return getShort(columnNo);
        } else {
            return null;
        }
    }

    /**
     * Extracts the contents of a cursors Column as a String.
     * If the column does not exist, it will return {@link #DEFAULT_STRING};
     *
     * @param columnName the name of the cursor column that we want to get the value from
     * @return the value from cursor if the column exists, {@link #DEFAULT_STRING} otherwise.
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

    @Override
    public String toString() {
        return "EasyCursor [mModel=" + mModel + ", mDebugEnabled="
                + mDebugEnabled + ", isClosed()=" + isClosed()
                + ", getCount()=" + getCount() + ", getColumnCount()="
                + getColumnCount() + ", getColumnNames()="
                + Arrays.toString(getColumnNames()) + ", getPosition()="
                + getPosition() + "]";
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static Cursor getAppropriateCursor(final EasySqlCursor cursor) {
        //
        // Sadly getWrappedCursor is only available in API 11+
        //

        final int currentApi = android.os.Build.VERSION.SDK_INT;
        if (currentApi >= Build.VERSION_CODES.HONEYCOMB) {
            return cursor.getWrappedCursor();
        } else {
            return cursor;
        }
    }
}