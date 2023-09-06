package org.apdb4j.core.permissions;

import java.io.Serial;

/**
 * An exception caused when an account does not possess
 * the suitable permissions to execute a query.
 */
public class PermissionDeniedException extends Exception {

    @Serial
    private static final long serialVersionUID = 202309062314L;

    /**
     * Constructs a {@code PermissionDeniedException} object.
     */
    public PermissionDeniedException() {
        // Constructs the plain exception.
    }

    /**
     * Constructs a {@code PermissionDeniedException} object
     * with a given {@code message}.
     * @param message the detail message.
     */
    public PermissionDeniedException(final String message) {
        super(message);
    }

    /**
     * Constructs a {@code PermissionDeniedException} object
     * with a given {@code message} and {@code cause}.
     * @param message the detail message.
     * @param cause the cause.
     */
    public PermissionDeniedException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a {@code PermissionDeniedException} object
     * with a given {@code cause}.
     * @param cause the cause.
     */
    public PermissionDeniedException(final Throwable cause) {
        super(cause);
    }

}
