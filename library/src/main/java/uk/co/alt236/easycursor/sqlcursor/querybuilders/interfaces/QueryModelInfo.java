package uk.co.alt236.easycursor.sqlcursor.querybuilders.interfaces;

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
