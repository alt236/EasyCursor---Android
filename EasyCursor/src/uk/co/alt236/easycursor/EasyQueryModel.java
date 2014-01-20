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
	
	public void setModelComment(String modelComment);
	
	public void setModelTag(String modelTag);
	
	public void setModelVersion(int modelVersion);
	
	/**
	 * Will return the JSON representation of this QueryModel.
	 *
	 * @return the resulting JSON String
	 * @throws JSONException if there was an error creating the JSON
	 */
	public String toJson() throws JSONException;
}
