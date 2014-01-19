package uk.co.alt236.easycursor.sampleapp.database;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class ExampleDatabase extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "Chinook_Sqlite.db";
    private static final int DATABASE_VERSION = 1;

    public ExampleDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}