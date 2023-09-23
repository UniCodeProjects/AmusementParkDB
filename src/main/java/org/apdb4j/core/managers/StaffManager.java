package org.apdb4j.core.managers;

import lombok.NonNull;

import java.time.LocalDate;

/**
 * Contains all the SQL queries that are related to the {@link org.apdb4j.db.tables.Staff} table.
 */
public interface StaffManager {

    /**
     * Performs the SQL query that adds a new staff member (an employee or an admin).
     * @param nationalID the national identifier of the new staff member.
     * @param staffID the staff identifier of the new staff member.
     * @param email the email of the new staff member.
     * @param name the name of the new staff member.
     * @param surname the surname of the new staff member.
     * @param dateOfBirth the date of birth of the new staff member.
     * @param birthPlace the place of birth of the new staff member.
     * @param gender the gender of the new staff member.
     * @param role the role of the new staff member. It can be {@code null} only if the provided staff is an admin.
     * @param isAdmin determines whether the provided staff is an admin or not.
     * @param isEmployee determines whether the provided staff is an employee or not.
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     */
    void hireNewStaffMember(@NonNull String nationalID, @NonNull String staffID,
                            @NonNull String email,
                            @NonNull String name, @NonNull String surname,
                            @NonNull LocalDate dateOfBirth, @NonNull String birthPlace,
                            char gender,
                            String role, boolean isAdmin, boolean isEmployee,
                            @NonNull String account);

    /**
     * Performs the SQL query that fires the provided staff member.
     * @param staffNationalID the national identifier of the staff member to fire. If the value of this parameter
     *                        is not the national identifier of a staff member, the query will not be executed.
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     */
    void fireStaffMember(@NonNull String staffNationalID, @NonNull String account);

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
