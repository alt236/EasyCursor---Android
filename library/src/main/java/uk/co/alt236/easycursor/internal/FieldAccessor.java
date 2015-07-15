package uk.co.alt236.easycursor.internal;

/**
 *
 */
public interface FieldAccessor {
    int getFieldIndexByName(final String fieldName);

    String getFieldNameByIndex(final int index);

    String[] getFieldNames();
}
