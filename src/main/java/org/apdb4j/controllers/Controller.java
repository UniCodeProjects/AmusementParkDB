package org.apdb4j.controllers;

import lombok.NonNull;

import java.util.Optional;

/**
 * A tag interface that groups all MVC controllers.
 */
public interface Controller {

    /**
     * Retrieves the error message caused by a failed operation.
     * @return {@link Optional}{@code <String>} containing the error message,<br>
     *         {@code empty} if there is no error message to retrieve,
     *         or the controller does not provide any error message forwarding.
     */
    @NonNull Optional<String> getErrorMessage();

}
