package org.apdb4j.core.managers;

import lombok.NonNull;

import java.time.LocalDate;

/**
 * Contains all the SQL queries that are related to the {@link org.apdb4j.db.tables.Staff} table.
 */
public final class StaffManager {

    private StaffManager() {
    }

    /**
     * Performs the SQL query that adds a new staff member (an employee or an admin).
     * @param nationalID the national identifier of the new staff member.
     * @param staffID the staff identifier of the new staff member.
     * @param email the email of the new staff member.
     * @param name the name of the new staff member.
     * @param surname the surname of the new staff member.
     * @param dateOfBirth the new staff member's date of birth.
     * @param birthPlace the new staff member's birthplace.
     * @param gender the gender of the new staff member.
     * @param role the role of the new staff member. It can be {@code null} only if the provided staff is an admin.
     * @param isAdmin determines whether the provided staff is an admin or not.
     * @param isEmployee determines whether the provided staff is an employee or not.
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     */
    public static void hireNewStaffMember(final @NonNull String nationalID, final @NonNull String staffID,
                                          final @NonNull String email,
                                          final @NonNull String name, final @NonNull String surname,
                                          final @NonNull LocalDate dateOfBirth, final @NonNull String birthPlace,
                                          final char gender,
                                          final String role, final boolean isAdmin, final boolean isEmployee,
                                          final @NonNull String account) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Performs the SQL query that fires the provided staff member.
     * @param staffNationalID the national identifier of the staff member to fire. If the value of this parameter
     *                        is not the national identifier of a staff member, the query will not be executed.
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     */
    public static void fireStaffMember(final @NonNull String staffNationalID, final @NonNull String account) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Performs the SQL query that updates the salary of the provided staff member.
     * @param staffNationalID the staff's national identifier. If the value of this parameter is not the national
     *                        identifier of a staff member, the query will not be executed and the controller will be informed.
     * @param newSalary the new salary for the provided staff member.
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     */
    // void updateStaffSalary(@NonNull String staffNationalID, double newSalary, @NonNull String account);

}
