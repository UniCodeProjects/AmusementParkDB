package org.apdb4j.core.managers;

import java.io.Serial;

/**
 * Exception caused when the input parameter of a method that performs a query is not valid.
 */
public class InputValueNotValidException extends Exception {

    @Serial
    private static final long serialVersionUID = 202309131321L;

    /**
     * Default constructor.
     */
    public InputValueNotValidException() {
        // Constructs the plain exception.
    }

    /**
     * Constructs a {@code InputValueNotValidException} object
     * with the given {@code message}.
     * @param message the detail message.
     */
    public InputValueNotValidException(final String message) {
        super(message);
    }

    /**
     * Constructs a {@code InputValueNotValidException} object
     * with the given {@code message} and {@code cause}.
     * @param message the detail message.
     * @param cause the cause.
     */
    public InputValueNotValidException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a {@code InputValueNotValidException} object
     * with the given {@code cause}.
     * @param cause the cause.
     */
    public InputValueNotValidException(final Throwable cause) {
        super(cause);
    }

}
