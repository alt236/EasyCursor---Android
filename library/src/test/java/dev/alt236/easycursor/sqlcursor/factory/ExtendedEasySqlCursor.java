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

package dev.alt236.easycursor.sqlcursor.factory;

import android.database.Cursor;

import dev.alt236.easycursor.sqlcursor.BooleanLogic;
import dev.alt236.easycursor.sqlcursor.EasySqlCursor;
import dev.alt236.easycursor.sqlcursor.querymodels.SqlQueryModel;

/**
 *
 */
public class ExtendedEasySqlCursor extends EasySqlCursor {
    public ExtendedEasySqlCursor(final Cursor cursor, final SqlQueryModel model, final BooleanLogic booleanLogic) {
        super(cursor, model, booleanLogic);
    }
}
