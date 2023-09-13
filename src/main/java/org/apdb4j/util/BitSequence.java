package org.apdb4j.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Accessors;

import java.util.SortedMap;

/**
 * A sequence of bits, represented by a type (an access interface)
 * and a map containing the binding between every method and its setting.
 */
public interface BitSequence {

    /**
     * Returns an unmodifiable sorted map of the
     * method-setting binding.
     * @return an unmodifiable sorted map
     */
    SortedMap<String, Bit> getAccessSettings();

    /**
     * Sets the setting for a specific method.
     * @param methodName the method's name
     * @param value the setting's bit value
     */
    void setAccessSetting(@NonNull String methodName, @NonNull Bit value);

    /**
     * Returns the bit sequence as a string of binary values.
     * @return the string of binary values
     */
    String getSequenceAsString();

    /**
     * The enum that represents the bit values of zero and one.
     */
    @Getter
    @Accessors(fluent = true)
    @AllArgsConstructor
    enum Bit {
        /**
         * The bit value of 1.
         */
        ONE(true),
        /**
         * The bit value of 0.
         */
        ZERO(false);

        private final boolean bit;

        /**
         * Returns the corresponding bit value to its boolean counterpart.
         * @param value the boolean value
         * @return the bit value
         */
        public static Bit fromBoolean(final boolean value) {
            return value ? ONE : ZERO;
        }

        /**
         * @return
         */
        @Override
        public String toString() {
            return bit ? "1" : "0";
        }

    }

}
