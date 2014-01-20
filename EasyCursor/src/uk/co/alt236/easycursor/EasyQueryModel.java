package uk.co.alt236.easycursor;

import org.json.JSONException;

public interface EasyQueryModel {
	
	/**
	 * Gets the user specified comment of this Model
	 *
	 * @return the comment
	 */
	public String getModelComment();
	
	/**
	 * Gets the user specified tag of this Model
	 *
	 * @return the tag
	 */
	public String getModelTag();
	
	/**
	 * Gets the user specified version of this Model
	 * The default value is 0
	 *
	 * @return the version of this model
	 */
	public int getModelVersion();
	
	/**
	 * Sets a comment on the model
	 *
	 * @param comment the comment to save
	 */
	public void setModelComment(String comment);
	
	/**
	 * Sets a tag on the model
	 *
	 * @param tag the tag to save
	 */
	public void setModelTag(String tag);
	
	/**
	 * Sets the model version
	 *
	 * @param version the version to save
	 */
	public void setModelVersion(int version);
	
	/**
	 * Will return the JSON representation of this QueryModel.
	 *
	 * @return the resulting JSON String
	 * @throws JSONException if there was an error creating the JSON
	 */
	public String toJson() throws JSONException;
}
