/*
 * ***************************************************************************
 * Copyright 2017 Alexandros Schillings
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

package dev.alt236.easycursor.sqlcursor.factory;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import dev.alt236.easycursor.objectcursor.factory.TestObject;

public class DatabaseHandler extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = null; // IN-MEMORY

    // Contacts table name
    private static final String TABLE_DATA = "data";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PH_NO = "phone_number";
    final List<TestObject> list = new ArrayList<>();

    public DatabaseHandler(final Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(final SQLiteDatabase db) {
        final String CREATE_DATA_TABLE = "CREATE TABLE " + TABLE_DATA + "("
                + "bool TEXT"
                + ", string TEXT"
                + ", byte BLOB"
                + ", double REAL"
                + ", float REAL"
                + ", int INTEGER"
                + ", long INTEGER"
                + ", short INTEGER"
                + ", booleantest TEXT"
                + ")";

        final ContentValues row1 = new ContentValues();
        row1.put("bool", false);
        row1.put("string", "foo");
        row1.put("byte", toByteArray("foo"));
        row1.put("double", Double.MIN_VALUE);
        row1.put("float", Float.MIN_VALUE);
        row1.put("int", Integer.MIN_VALUE);
        row1.put("long", Long.MIN_VALUE);
        row1.put("short", Short.MIN_VALUE);
        row1.put("booleantest", 0);

        final ContentValues row2 = new ContentValues();
        row2.put("bool", true);
        row2.put("string", "bar");
        row2.put("byte", toByteArray("bar"));
        row2.put("double", Double.MAX_VALUE);
        row2.put("float", Float.MAX_VALUE);
        row2.put("int", Integer.MAX_VALUE);
        row2.put("long", Long.MAX_VALUE);
        row2.put("short", Short.MAX_VALUE);
        row2.put("booleantest", 1);

        final ContentValues row3 = new ContentValues();
        row3.put("bool", (Boolean) null);
        row3.put("string", (String) null);
        row3.put("byte", (Byte) null);
        row3.put("double", (Double) null);
        row3.put("float", (Float) null);
        row3.put("int", (Integer) null);
        row3.put("long", (Long) null);
        row3.put("short", (Short) null);
        row3.put("booleantest", (Boolean) null);

        db.execSQL(CREATE_DATA_TABLE);
        db.insert(TABLE_DATA, null, row1);
        db.insert(TABLE_DATA, null, row2);
        db.insert(TABLE_DATA, null, row3);
    }

    // Upgrading database
    @Override
    public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATA);

        // Create tables again
        onCreate(db);
    }

    private static byte[] toByteArray(final String string) {
        try {
            return string.getBytes("UTF-8");
        } catch (final Exception e) {
            // will never be thrown
            throw new IllegalStateException(e);
        }
    }
}