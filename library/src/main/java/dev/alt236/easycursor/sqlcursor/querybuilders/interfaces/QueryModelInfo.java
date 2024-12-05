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

package dev.alt236.easycursor.sqlcursor.querybuilders.interfaces;

/**
 *
 */
public interface QueryModelInfo {
    /**
     * Gets the user specified comment of this Model
     *
     * @return the comment
     */
    String getModelComment();

    /**
     * Gets the user specified tag of this Model
     *
     * @return the tag
     */
    String getModelTag();

    /**
     * Gets the user specified version of this Model
     * The default value is 0
     *
     * @return the version of this model
     */
    int getModelVersion();
}
