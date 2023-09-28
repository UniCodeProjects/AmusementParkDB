package org.apdb4j.controllers;

import lombok.NonNull;

/**
 * The controller that checks the validity of a sign-in or sign-up.
 */
public interface LoginController extends Controller {

    /**
     * Checks the validity of an account sign-in.
     * @param username the provided username
     * @param password the provided password
     * @return {@code true} if the account is present in the database
     */
    boolean checkSignIn(@NonNull String username, @NonNull String password);

    /**
     * Checks the validity of an account sign-up.
     * @param email the provided email
     * @param username the provided username
     * @param password the provided password
     * @return {@code true} if the account is successfully inserted in the database
     */
    boolean checkSignUp(@NonNull String email, @NonNull String username, @NonNull String password);

    /**
     * Checks whether the given user is a staff account.
     * @param username the account's username
     * @return {@code true} if it is a staff account
     */
    boolean isStaff(@NonNull String username);

    /**
     * Checks whether the given user is a guest account.
     * @param username the account's username
     * @return {@code true} if it is a guest account
     */
    boolean isGuest(@NonNull String username);

}