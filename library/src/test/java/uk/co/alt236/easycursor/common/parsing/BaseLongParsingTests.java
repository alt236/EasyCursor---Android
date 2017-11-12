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

package uk.co.alt236.easycursor.common.parsing;

import org.junit.Test;

import uk.co.alt236.easycursor.EasyCursor;
import uk.co.alt236.easycursor.objectcursor.EasyObjectCursor;

import static org.junit.Assert.assertEquals;

public abstract class BaseLongParsingTests extends BaseParsingTests {
    private static final String FIELD_DOES_NOT_EXIST = "FIELD_DOES_NOT_EXIST";
    private static final String FIELD_EXISTENT = "long";

    private EasyCursor mSut;

    public void setCursor(final EasyCursor easyCursor) {
        mSut = easyCursor;
    }

    @Test
    @Override
    public void parsesFieldsThatExistAndHaveValues() {
        mSut.moveToPosition(0);
        assertEquals(Long.MIN_VALUE, mSut.getLong(FIELD_EXISTENT), 0);
        assertEquals(Long.MIN_VALUE, mSut.optLong(FIELD_EXISTENT), 0);
        assertEquals(Long.MIN_VALUE, mSut.optLongAsWrapperType(FIELD_EXISTENT), 0);

        mSut.moveToPosition(1);
        assertEquals(Long.MAX_VALUE, mSut.getLong(FIELD_EXISTENT), 0);
        assertEquals(Long.MAX_VALUE, mSut.optLong(FIELD_EXISTENT), 0);
        assertEquals(Long.MAX_VALUE, mSut.optLongAsWrapperType(FIELD_EXISTENT), 0);
    }

    @Test
    @Override
    public void parsesFieldsThatExistAndHaveNullValues() {
        mSut.moveToPosition(2);
        assertEquals(EasyObjectCursor.DEFAULT_LONG, mSut.getLong(FIELD_EXISTENT), 0);
        assertEquals(EasyObjectCursor.DEFAULT_LONG, mSut.optLong(FIELD_EXISTENT), 0);
        assertEquals(EasyObjectCursor.DEFAULT_LONG, mSut.optLong(FIELD_EXISTENT, 2), 0);
        assertEquals(EasyObjectCursor.DEFAULT_LONG, mSut.optLongAsWrapperType(FIELD_EXISTENT), 0);
    }

    @Test
    @Override
    public void parsesFieldsThatDoNotExist() {
        mSut.moveToPosition(0);
        assertEquals(EasyObjectCursor.DEFAULT_LONG, mSut.optLong(FIELD_DOES_NOT_EXIST), 0);
        assertEquals(2, mSut.optLong(FIELD_DOES_NOT_EXIST, 2), 0);
        assertEquals(null, mSut.optLongAsWrapperType(FIELD_DOES_NOT_EXIST));
    }

    @Test(expected = IllegalArgumentException.class)
    @Override
    public void throwsExceptionOnFieldsWhenGettingANullValue() {
        mSut.moveToPosition(0);
        mSut.getLong(FIELD_DOES_NOT_EXIST);
    }
}
