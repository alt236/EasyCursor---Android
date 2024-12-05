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

package dev.alt236.easycursor.jsoncursor;

import org.junit.Before;
import org.junit.Test;

import dev.alt236.easycursor.EasyCursor;
import dev.alt236.easycursor.common.root.BaseRobolectricTest;
import dev.alt236.easycursor.jsoncursor.factory.TestJsonCursorBuilder;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class EasyJsonCursorTest extends BaseRobolectricTest {

    private EasyCursor mSut;

    @Before
    public void setUp() {
        mSut = TestJsonCursorBuilder.getCursor();
    }

    @Test
    public void testAliasing() {
        final EasyCursor cursor = TestJsonCursorBuilder.getCursor("int");

        final int intIndex = cursor.getColumnIndex("int");
        final int idIndex = cursor.getColumnIndex("_id");
        assertEquals(intIndex, idIndex);
    }

}