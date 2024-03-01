package org.apdb4j.controllers;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
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
            session = new SessionInfo(AccountManager.getAccountOwnerName(accountUsername),
                    AccountManager.getAccountOwnerSurname(accountUsername),
                    accountUsername,
                    AccountManager.getAccountEmail(accountUsername),
                    AccountManager.getPersonID(accountUsername));
        }
    }

    /**
     * Changes the name of the owner of the currently logged account.
     * @param newName the new name of the owner of the currently logged account.
     * @return {@code true} if the update is successfully, {@code false} otherwise.
     */
    public boolean changeLoggedAccountOwnerName(final @NonNull String newName) {
        if (!AccountManager.changeAccountOwnerName(session.email(), newName)) {
            return false;
        }
        session = new SessionInfo(newName, session.surname(), session.username(), session.email(), session.personID());
        return true;
    }

    /**
     * Changes the surname of the owner of the currently logged account.
     * @param newSurname the new surname of the owner of the currently logged account.
     * @return {@code true} if the update is successfully, {@code false} otherwise.
     */
    public boolean changeLoggedAccountOwnerSurname(final @NonNull String newSurname) {
        if (!AccountManager.changeAccountOwnerSurname(session.email(), newSurname)) {
            return false;
        }
        session = new SessionInfo(session.name(), newSurname, session.username(), session.email(), session.personID());
        return true;
    }

    /**
     * Changes the username of the currently logged account with the provided one.
     * @param newUsername the new username of the currently logged account.
     * @return {@code true} if the update is successfully, {@code false} otherwise (e.g. if another account has
     *         {@code newUsername} as its username).
     */
    public boolean changeLoggedAccountUsername(final @NonNull String newUsername) {
        if (!AccountManager.changeUsername(session.email(), newUsername)) {
            return false;
        }
        session = new SessionInfo(session.name(), session.surname(), newUsername, session.email(), session.personID());
        return true;
    }

    /**
     * Changes the email of the currently logged account with the provided one.
     * @param newEmail the new email of the currently logged account.
     * @return {@code true} if the update is successfully, {@code false} otherwise (e.g. if another account has
     *         {@code newEmail} as its email).
     */
    public boolean changeLoggedAccountEmail(final @NonNull String newEmail) {
        if (!AccountManager.changeEmail(session.email(), newEmail)) {
            return false;
        }
        session = new SessionInfo(session.name(), session.surname(), session.username(), newEmail, session.personID());
        return true;
    }

    /**
     * Record that keeps track of the username of the logged account and of the person ID
     * of the owner of said account.
     * @param name the name of the owner of the account that is logged in.
     * @param surname the surname of the owner of the account that is logged in.
     * @param username the username of the account that is logged in.
     * @param email the email of the account that is logged in.
     * @param personID the identifier of the owner of the logged account.
     */
    public record SessionInfo(String name, String surname, String username, String email, String personID) {
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
