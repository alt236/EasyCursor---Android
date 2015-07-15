package uk.co.alt236.easycursor;

import org.json.JSONException;

public interface EasyQueryModel {

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
