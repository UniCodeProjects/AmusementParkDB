package org.apdb4j.util;

import lombok.NonNull;
import org.apdb4j.core.permissions.AccessType;

import java.util.SortedMap;

/**
 * A sequence of chars, represented by a type (an access interface)
 * and a map containing the binding between every method and its setting.
 */
public interface CharSequence {

    /**
     * Returns an unmodifiable sorted map of the
     * method-setting binding.
     * @return an unmodifiable sorted map
     */
    SortedMap<String, AccessType> getAccessSettings();

    /**
     * Sets the setting for a specific method.
     * @param methodName the method's name
     * @param value the setting's char value
     */
    void setAccessSetting(@NonNull String methodName, @NonNull AccessType value);

    /**
     * Returns the char sequence.
     * @return the string of char values
     */
    String getSequenceAsString();

}
