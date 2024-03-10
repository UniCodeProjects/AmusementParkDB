package org.apdb4j.core.managers;

import lombok.NonNull;
import org.apdb4j.util.QueryBuilder;
import org.jooq.Record;
import org.jooq.impl.DSL;

import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;

import static org.apdb4j.db.Tables.ACCOUNTS;
import static org.apdb4j.db.Tables.CONTRACTS;
import static org.apdb4j.db.Tables.STAFF;

/**
 * Contains all the SQL queries that are related to the {@link org.apdb4j.db.tables.Staff} table.
 */
public final class StaffManager {

    private static final QueryBuilder DB = new QueryBuilder();
    private static final String ADMIN_PERMISSION = "Admin";
    private static final String STAFF_PERMISSION = "Staff";

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
     * @return {@code true} on successful tuple insertion
     */
    public static boolean hireNewStaffMember(final @NonNull String nationalID, final @NonNull String staffID,
                                             final @NonNull String email,
                                             final @NonNull String name, final @NonNull String surname,
                                             final @NonNull LocalDate dateOfBirth, final @NonNull String birthPlace,
                                             final char gender,
                                             final String role, final boolean isAdmin, final boolean isEmployee) {
        if (AccountManager.isGuest(email)) {
            return false;
        }
        DB.createConnection()
                .queryAction(db -> {
                    db.transaction(configuration -> {
                        AccountManager.addNewAccount(email, isAdmin ? ADMIN_PERMISSION : STAFF_PERMISSION);
                        configuration.dsl()
                                .insertInto(STAFF)
                                .values(staffID,
                                        nationalID,
                                        email,
                                        name,
                                        surname,
                                        dateOfBirth,
                                        birthPlace,
                                        gender,
                                        role,
                                        isAdmin,
                                        isEmployee)
                                .execute();
                    });
                    return 1;
                })
                .closeConnection();
        return true;
    }

    /**
     * Performs the SQL query that fires the provided staff member.
     * @param staffNationalID the national identifier of the staff member to fire. If the value of this parameter
     *                        is not the national identifier of a staff member, the query will not be executed.
     * @return {@code true} on successful tuple update
     */
    public static boolean fireStaffMember(final @NonNull String staffNationalID) {
        final LocalDate currentDate = LocalDate.now();
        final int lastDayOfMonth = currentDate.withDayOfMonth(currentDate.lengthOfMonth()).getDayOfMonth();
        DB.createConnection()
                .queryAction(db -> {
                    db.transaction(configuration -> {
                        configuration.dsl()
                                .update(CONTRACTS)
                                .set(CONTRACTS.ENDDATE,
                                        LocalDate.of(Year.now().getValue(), YearMonth.now().getMonth(), lastDayOfMonth))
                                .where(CONTRACTS.EMPLOYEENID.eq(staffNationalID))
                                .execute();
                        configuration.dsl()
                                .deleteFrom(ACCOUNTS)
                                .whereExists(DSL.selectOne()
                                        .from(STAFF)
                                        .where(STAFF.EMAIL.eq(ACCOUNTS.EMAIL))
                                        .and(STAFF.NATIONALID.eq(staffNationalID)))
                                .execute();
                    });
                    return 1;
                })
                .closeConnection();
        return true;
    }

    /**
     * Performs the SQL query that updates the salary of the provided staff member.
     * @param staffNationalID the staff's national identifier. If the value of this parameter is not the national
     *                        identifier of a staff member, the query will not be executed and the controller will be informed.
     * @param newContractID the new ID used for the updated contract.
     * @param newSalary the new salary for the provided staff member.
     * @return {@code true} on successful tuple update
     */
    @SuppressWarnings("PMD.PrematureDeclaration")   // Not a premature declaration, changes happen in DB.
     public static boolean updateStaffSalary(final @NonNull String staffNationalID,
                                             final @NonNull String newContractID,
                                             final double newSalary) {
         final Record oldContract = DB.createConnection()
                 .queryAction(db -> db.select()
                         .from(CONTRACTS)
                         .where(CONTRACTS.EMPLOYEENID.eq(staffNationalID))
                         .fetch())
                 .closeConnection()
                 .getResultAsRecords()
                 .get(0);
         final boolean updatedOldContract = fireStaffMember(staffNationalID);
         if (!updatedOldContract) {
             return false;
         }
         final boolean updatedNewContract = ContractManager.signNewContract(newContractID,
                 oldContract.get(CONTRACTS.EMPLOYEENID),
                 oldContract.get(CONTRACTS.EMPLOYERNID),
                 LocalDate.now(),
                 LocalDate.now(),
                 oldContract.get(CONTRACTS.ENDDATE),
                 newSalary
         );
         // Rollback changes to old contract.
         if (!updatedNewContract) {
             return DB.createConnection()
                     .queryAction(db -> db.update(CONTRACTS)
                             .set(CONTRACTS.ENDDATE, oldContract.get(CONTRACTS.ENDDATE))
                             .where(CONTRACTS.CONTRACTID.eq(oldContract.get(CONTRACTS.CONTRACTID)))
                             .execute())
                     .closeConnection()
                     .getResultAsInt() == 1;
         }
         return true;
     }

}
