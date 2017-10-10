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

package uk.co.alt236.easycursor.objectcursor;

import android.util.Log;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.List;

import uk.co.alt236.easycursor.CommonCursorTests;
import uk.co.alt236.easycursor.EasyCursor;

import static org.junit.Assert.assertEquals;

public class EasyObjectCursorTest extends CommonCursorTests {
    private final String TAG = getClass().getName();

    private EasyCursor mSut;

    @Before
    public void setUp() {
        mSut = TestObjectCursorBuilder.getCursor();
        setCursor(mSut);
    }

    @Test
    public void testAliasing() {
        final EasyCursor cursor = TestObjectCursorBuilder.getCursor("int");

        final int intIndex = cursor.getColumnIndex("int");
        final int idIndex = cursor.getColumnIndex("_id");
        assertEquals(intIndex, idIndex);
    }



    @Test
    public void testMethodSet() {
        final List<Method> methods = ((EasyObjectCursor<?>) mSut).getMethods();

        for (final Method method : methods) {
            Log.v(TAG, "Has method: " + method.getName());
            assertEquals(0, method.getParameterTypes().length);
        }

        final String[] cols = mSut.getColumnNames();
        for (final String col : cols) {
            Log.v(TAG, "Column name: " + col);
        }

        mSut.close();
    }
}