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

package dev.alt236.easycursor.common.parsing;

import org.junit.Test;

import dev.alt236.easycursor.EasyCursor;
import dev.alt236.easycursor.objectcursor.EasyObjectCursor;

import static org.junit.Assert.assertEquals;

public abstract class BaseBooleanParsingTest extends BaseParsingTests {
    private static final String FIELD_DOES_NOT_EXIST = "FIELD_DOES_NOT_EXIST";
    private static final String FIELD_EXISTENT = "bool";

    private EasyCursor mSut;

    public void setCursor(final EasyCursor easyCursor) {
        mSut = easyCursor;
    }

    @Test
    @Override
    public void parsesFieldsThatExistAndHaveValues() {
        mSut.moveToPosition(0);
        assertEquals(false, mSut.getBoolean(FIELD_EXISTENT));
        assertEquals(EasyObjectCursor.DEFAULT_BOOLEAN, mSut.optBoolean(FIELD_EXISTENT));
        assertEquals(Boolean.FALSE, mSut.optBooleanAsWrapperType(FIELD_EXISTENT));

        mSut.moveToPosition(1);
        assertEquals(true, mSut.getBoolean(FIELD_EXISTENT));
        assertEquals(true, mSut.optBoolean(FIELD_EXISTENT));
        assertEquals(Boolean.TRUE, mSut.optBooleanAsWrapperType(FIELD_EXISTENT));
    }

    @Test
    @Override
    public void parsesFieldsThatExistAndHaveNullValues() {
        mSut.moveToPosition(2); // Field exists but it is null
        assertEquals(EasyObjectCursor.DEFAULT_BOOLEAN, mSut.getBoolean(FIELD_EXISTENT));
        assertEquals(EasyObjectCursor.DEFAULT_BOOLEAN, mSut.optBoolean(FIELD_EXISTENT));
        assertEquals(EasyObjectCursor.DEFAULT_BOOLEAN, mSut.optBoolean(FIELD_EXISTENT, true));
        assertEquals(EasyObjectCursor.DEFAULT_BOOLEAN, mSut.optBooleanAsWrapperType(FIELD_EXISTENT));
    }

    @Test
    @Override
    public void parsesFieldsThatDoNotExist() {
        mSut.moveToPosition(0); // Field does NOT exist
        assertEquals(EasyObjectCursor.DEFAULT_BOOLEAN, mSut.optBoolean(FIELD_DOES_NOT_EXIST));
        assertEquals(true, mSut.optBoolean(FIELD_DOES_NOT_EXIST, true));
        assertEquals(null, mSut.optBooleanAsWrapperType(FIELD_DOES_NOT_EXIST));
    }

    @Test(expected = IllegalArgumentException.class)
    @Override
    public void throwsExceptionOnFieldsWhenGettingANullValue() {
        mSut.moveToPosition(0); // Field does NOT exist
        mSut.getBoolean(FIELD_DOES_NOT_EXIST);
    }
}
