/*
 * ***************************************************************************
 * Copyright 2024 Alexandros Schillings
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

package dev.alt236.easycursor.sampleapp.database;

import android.content.Context;

public final class DbSingleton {
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
