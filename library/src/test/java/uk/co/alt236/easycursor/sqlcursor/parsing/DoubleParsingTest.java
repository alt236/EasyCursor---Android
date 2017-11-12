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

package uk.co.alt236.easycursor.sqlcursor.parsing;

import org.junit.After;
import org.junit.Before;

import uk.co.alt236.easycursor.EasyCursor;
import uk.co.alt236.easycursor.common.parsing.BaseDoubleParsingTests;
import uk.co.alt236.easycursor.sqlcursor.factory.EasySqlCursorBuilder;

public class DoubleParsingTest extends BaseDoubleParsingTests {

    private EasyCursor mSut;

    @Before
    public void setUp() {
        mSut = EasySqlCursorBuilder.getCursor();
        setCursor(mSut);
    }

    @After
    public void tearDown() {
        mSut.close();
    }
}
