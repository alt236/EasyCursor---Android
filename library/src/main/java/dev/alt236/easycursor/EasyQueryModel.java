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

package dev.alt236.easycursor;

import org.json.JSONException;

import dev.alt236.easycursor.sqlcursor.querybuilders.interfaces.QueryModelInfo;

public interface EasyQueryModel extends QueryModelInfo {

    /**
     * Gets the user specified comment of this Model
     *
     * @return the comment
     */
    String getModelComment();

    /**
     * Sets a comment on the model
     *
     * @param comment the comment to save
     */
    void setModelComment(String comment);

    /**
     * Gets the user specified tag of this Model
     *
     * @return the tag
     */
    String getModelTag();

    /**
     * Sets a tag on the model
     *
     * @param tag the tag to save
     */
    void setModelTag(String tag);

    /**
     * Gets the user specified version of this Model
     * The default value is 0
     *
     * @return the version of this model
     */
    int getModelVersion();

    /**
     * Sets the model version
     *
     * @param version the version to save
     */
    void setModelVersion(int version);

    /**
     * Will return the JSON representation of this QueryModel.
     *
     * @return the resulting JSON String
     * @throws JSONException if there was an error creating the JSON
     */
    String toJson() throws JSONException;
}
