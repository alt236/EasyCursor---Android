package uk.co.alt236.easycursor.sampleapp.database;

import android.content.Context;

public class DbSingleton {
    private final static Object mLock = new Object();
    private static ExampleDatabase sInstance;

    private DbSingleton() {
    }

    public static ExampleDatabase getInstance(final Context context) {
        if (sInstance == null) {
            synchronized (mLock) {
                if (sInstance == null) {
                    sInstance = new ExampleDatabase(context);
                }
            }
        }
        return sInstance;
    }
}
