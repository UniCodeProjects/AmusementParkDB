package org.apdb4j.core.permissions;

import java.io.Serial;

/**
 * An exception caused when an account does not possess
 * the suitable access to execute a query.
 */
public class AccessDeniedException extends Exception {

    @Serial
    private static final long serialVersionUID = 202309112334L;

    /**
     * Constructs a {@code AccessDeniedException} object.
     */
    public AccessDeniedException() {
        // Constructs the plain exception.
    }

    /**
     * Constructs a {@code AccessDeniedException} object
     * with a given {@code message}.
     * @param message the detail message.
     */
    public AccessDeniedException(final String message) {
        super(message);
    }

    /**
     * Constructs a {@code AccessDeniedException} object
     * with a given {@code message} and {@code cause}.
     * @param message the detail message.
     * @param cause the cause.
     */
    public AccessDeniedException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a {@code AccessDeniedException} object
     * with a given {@code cause}.
     * @param cause the cause.
     */
    public AccessDeniedException(final Throwable cause) {
        super(cause);
    }

}
