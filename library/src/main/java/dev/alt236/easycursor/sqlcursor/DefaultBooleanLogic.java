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

package dev.alt236.easycursor.sqlcursor;

import dev.alt236.easycursor.EasyCursor;

/**
 *
 */
public class DefaultBooleanLogic implements BooleanLogic {

    /**
     * This method defines the default boolean resolution of an {@link EasySqlCursor}
     * <p>
     * It is defined as:
     * <pre>
     * {@code
     * final int value = cursor.getInt(columnNumber);
     * return (value == 1);
     * }
     * </pre>
     *
     * @param cursor       the cursor we are examining
     * @param columnNumber the column we are examining
     * @return whether the contents of the specified column of the cursor represent TRUE
     */
    @Override
    public boolean isTrue(final EasyCursor cursor, final int columnNumber) {
        final int value = cursor.getInt(columnNumber);
        return (value == 1);
    }
}
