package org.apdb4j.controllers;

import lombok.AccessLevel;
import lombok.Getter;

import java.util.Objects;

/**
 * Class that keeps track of the account that is logged in.
 */
public final class SessionManager {
    private static final SessionManager INSTANCE = new SessionManager();
    @Getter(AccessLevel.PUBLIC)
    private String loggedAccount;

    private SessionManager() {
    }

    /**
     * Returns the singleton instance of this class.
     * @return the unique instance of this class.
     */
    public static SessionManager getSession() {
        return INSTANCE;
    }

    /**
     * If no account is logged in, keeps track of the provided {@code accountUsername}.
     * Otherwise, if an account is already logged in, this method does nothing.
     * @param accountUsername the username of the account that is logged in.
     */
    public void login(final String accountUsername) {
        if (Objects.isNull(loggedAccount)) {
            loggedAccount = accountUsername;
        }
    }
}
