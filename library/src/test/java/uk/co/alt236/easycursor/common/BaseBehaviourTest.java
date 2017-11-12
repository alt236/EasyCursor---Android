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

package uk.co.alt236.easycursor.common;

import org.junit.Test;

import uk.co.alt236.easycursor.EasyCursor;
import uk.co.alt236.easycursor.common.root.BaseRobolectricTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public abstract class BaseBehaviourTest extends BaseRobolectricTest {
    private EasyCursor mSut;

    public void setCursor(final EasyCursor easyCursor) {
        mSut = easyCursor;
    }

    @Test
    public void testFieldIndexes() {
        mSut.moveToFirst();
        final String[] cols = mSut.getColumnNames();

        int index = 0;
        for (final String col : cols) {
            final int cursorFieldIndex = mSut.getColumnIndex(col);
            assertEquals(index, cursorFieldIndex);
            final String cursorFieldName = mSut.getColumnName(cursorFieldIndex);
            assertEquals(col, cursorFieldName);
            index++;
        }

        mSut.close();
    }

    @Test
    public void testNullCheck() {
        final String[] cols = mSut.getColumnNames();

        mSut.moveToFirst();

        mSut.moveToPosition(0);
        for (final String col : cols) {
            if ("null".equals(col)) {
                assertTrue("Should have been null: " + col, mSut.isNull(col));
            } else {
                assertFalse("Should NOT have been null: " + col, mSut.isNull(col));
            }
        }

        mSut.moveToPosition(1);
        for (final String col : cols) {
            if ("null".equals(col)) {
                assertTrue("Should have been null: " + col, mSut.isNull(col));
            } else {
                assertFalse("Should NOT have been null: " + col, mSut.isNull(col));
            }
        }

        mSut.moveToPosition(2);
        for (final String col : cols) {
            if (!"class".equals(col)) { // this is the base Object class field
                assertTrue("Should have been null: " + col, mSut.isNull(col));
            }
        }

        mSut.close();
    }

    @Test
    public void testIndexFetching() {
        assertTrue(mSut.getColumnIndex("int") != -1);
        assertTrue(mSut.getColumnIndex("bool") != -1);
        assertTrue(mSut.getColumnIndex("float") != -1);
        assertTrue(mSut.getColumnIndex("double") != -1);
        assertTrue(mSut.getColumnIndex("_id") == -1);
        assertTrue(mSut.getColumnIndex("not_exists") == -1);

        assertTrue(mSut.getColumnIndexOrThrow("int") != -1);
        assertTrue(mSut.getColumnIndexOrThrow("bool") != -1);
        assertTrue(mSut.getColumnIndexOrThrow("float") != -1);
        assertTrue(mSut.getColumnIndexOrThrow("double") != -1);

        try {
            assertTrue(mSut.getColumnIndexOrThrow("_id") == -1);
            fail("this should have blown");
        } catch (final IllegalArgumentException e) {
            //
        }

        try {
            assertTrue(mSut.getColumnIndexOrThrow("not_exists") == -1);
            fail("this should have blown");
        } catch (final IllegalArgumentException e) {
            //
        }
    }
}
