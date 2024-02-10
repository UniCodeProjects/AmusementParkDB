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
            session = new SessionInfo(accountUsername,
                    AccountManager.getAccountEmail(accountUsername),
                    AccountManager.getPersonID(accountUsername));
        }
    }

    /**
     * Record that keeps track of the username of the logged account and of the person ID
     * of the owner of said account.
     * @param username the username of the account that is logged in.
     * @param email the email of the account that is logged in.
     * @param personID the identifier of the owner of the logged account.
     */
    public record SessionInfo(String username, String email, String personID) {
        /**
         * Checks if the logged user is an admin.
         * @return {@code true} if an admin
         */
        public boolean isAdmin() {
            return AccountManager.isAdminByUsername(username);
        }

        /**
         * Checks if the logged user is an employee.
         * @return {@code true} if an employee
         */
        public boolean isEmployee() {
            return AccountManager.isEmployeeByUsername(username);
        }

        /**
         * Checks if the logged user is a guest.
         * @return {@code true} if a guest
         */
        public boolean isGuest() {
            return AccountManager.isGuestByUsername(username);
        }
    }
}
