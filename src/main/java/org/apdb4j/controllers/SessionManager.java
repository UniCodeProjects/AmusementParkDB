package org.apdb4j.controllers;

import lombok.AccessLevel;
import lombok.Getter;
import org.apdb4j.core.managers.AccountManager;

import java.util.Objects;

/**
 * Class that keeps track of the account that is logged in.
 */
public final class SessionManager {
    private static final SessionManager INSTANCE = new SessionManager();
    @Getter(AccessLevel.PUBLIC)
    private SessionInfo session;

    private SessionManager() {
    }

    /**
     * Returns the singleton instance of this class.
     * @return the unique instance of this class.
     */
    public static SessionManager getSessionManager() {
        return INSTANCE;
    }

    /**
     * If no account is logged in, creates a new session for the provided {@code accountUsername}.
     * Otherwise, if an account is already logged in, this method does nothing.
     * @param accountUsername the username of the account that is logged in.
     */
    public void login(final String accountUsername) {
        if (Objects.isNull(session)) {
            session = new SessionInfo(accountUsername, AccountManager.getPersonID(accountUsername));
        }
    }

    private record SessionInfo(String username, String personID) {
    }
}
