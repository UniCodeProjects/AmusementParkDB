package org.apdb4j.core.managers;

import lombok.NonNull;
import org.apdb4j.core.permissions.AdminPermission;
import org.apdb4j.core.permissions.StaffPermission;
import org.apdb4j.util.QueryBuilder;

import java.time.LocalDate;

import static org.apdb4j.db.Tables.*;

/**
 * Contains all the SQL queries that are related to the {@link org.apdb4j.db.tables.Staff} table.
 */
public final class StaffManager {

    private static final QueryBuilder DB = new QueryBuilder();
    private static final String ADMIN_PERMISSION = AdminPermission.class.getSimpleName().replace("Permission", "");
    private static final String STAFF_PERMISSION = StaffPermission.class.getSimpleName().replace("Permission", "");

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
     * @return {@code true} on successful tuple insertion
     */
    public static boolean hireNewStaffMember(final @NonNull String nationalID, final @NonNull String staffID,
                                             final @NonNull String email,
                                             final @NonNull String name, final @NonNull String surname,
                                             final @NonNull LocalDate dateOfBirth, final @NonNull String birthPlace,
                                             final char gender,
                                             final String role, final boolean isAdmin, final boolean isEmployee,
                                             final @NonNull String account) {
        if (isGuest(email)) {
            return false;
        }
        final var insertedAccount = AccountManager.addNewAccount(email, isAdmin ? ADMIN_PERMISSION : STAFF_PERMISSION, account);
        if (!insertedAccount) {
            return false;
        }
        final int insertedStaffTuples = DB.createConnection()
                .queryAction(db -> db.insertInto(STAFF)
                        .values(nationalID,
                                staffID,
                                email,
                                name,
                                surname,
                                dateOfBirth,
                                birthPlace,
                                gender,
                                role,
                                isAdmin,
                                isEmployee)
                        .execute())
                .closeConnection()
                .getResultAsInt();
        if (insertedStaffTuples == 0) {
            return DB.createConnection()
                    .queryAction(db -> db.deleteFrom(ACCOUNTS)
                            .where(ACCOUNTS.EMAIL.eq(email))
                            .execute())
                    .closeConnection()
                    .getResultAsInt() == 1;
        }
        return insertedStaffTuples == 1;
    }

    /**
     * Performs the SQL query that fires the provided staff member.
     * @param staffNationalID the national identifier of the staff member to fire. If the value of this parameter
     *                        is not the national identifier of a staff member, the query will not be executed.
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     * @return {@code true} on successful tuple update
     */
    public static boolean fireStaffMember(final @NonNull String staffNationalID, final @NonNull String account) {
        final int updatedTuples = DB.createConnection()
                .queryAction(db -> db.update(CONTRACTS)
                        .set(CONTRACTS.ENDDATE, LocalDate.now())
                        .where(CONTRACTS.EMPLOYEENID.eq(staffNationalID)))
                .closeConnection()
                .getResultAsInt();
        return updatedTuples == 1;
    }

    /**
     * Performs the SQL query that updates the salary of the provided staff member.
     * @param staffNationalID the staff's national identifier. If the value of this parameter is not the national
     *                        identifier of a staff member, the query will not be executed and the controller will be informed.
     * @param newSalary the new salary for the provided staff member.
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     */
     public static void updateStaffSalary(final @NonNull String staffNationalID,
                                          final double newSalary,
                                          final @NonNull String account) {
         // TODO: end date = LocalDate.now() for old contract. Copies all the info and creates a new contract with new salary.
     }

    private static boolean isGuest(final String email) {
        return DB.createConnection()
                .queryAction(db -> db.selectCount()
                        .from(GUESTS)
                        .where(GUESTS.EMAIL.eq(email))
                        .fetchOne(0, int.class))
                .closeConnection()
                .getResultAsInt() == 1;
    }

}
