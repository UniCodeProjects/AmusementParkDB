package org.apdb4j.core.managers;

import lombok.NonNull;
import org.apdb4j.util.QueryBuilder;
import org.jooq.Record;
import org.jooq.Result;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.apdb4j.db.Tables.CONTRACTS;
import static org.apdb4j.db.Tables.STAFF;

/**
 * Contains all the SQL queries that are related to the {@link org.apdb4j.db.tables.Contracts} table.
 */
public final class ContractManager {

    private ContractManager() {
    }

    /**
     * Performs the SQL query that creates a new contract.
     * @param contractID the identifier of the new contract.
     * @param employeeNID the employee's national ID. If the value of this parameter is not the national identifier
     *                    of an employee, the query will not be executed.
     * @param employerNID the employer's national ID. If the value of this parameter is not the national identifier
     *                    of an employer, the query will not be executed.
     * @param subscriptionDate the date on which the contract has been signed.
     * @param beginDate the date from which the contract is legally valid.
     * @param endDate the date on which the contract expires. If it is {@code null} then the
     *                contract is a permanent contract. It has to be in the future compared to {@code beginDate},
     *                otherwise the query will not be executed.
     * @param salary the salary of the provided employee.
     * @return {@code true} on successful tuple insertion
     */
    public static boolean signNewContract(final @NonNull String contractID,
                                          final @NonNull String employeeNID, final @NonNull String employerNID,
                                          final @NonNull LocalDate subscriptionDate,
                                          final @NonNull LocalDate beginDate, final LocalDate endDate,
                                          final double salary) {
        final Result<Record> existingBeginDate = new QueryBuilder()
                .createConnection()
                .queryAction(db -> db.select(CONTRACTS.BEGINDATE)
                        .from(CONTRACTS)
                        .where(STAFF.NATIONALID.as(CONTRACTS.EMPLOYEENID).eq(employeeNID))
                        .fetch())
                .closeConnection()
                .getResultAsRecords();
        final Result<Record> existingEndDate = new QueryBuilder()
                .createConnection()
                .queryAction(db -> db.select(CONTRACTS.ENDDATE)
                        .from(CONTRACTS)
                        .where(STAFF.NATIONALID.as(CONTRACTS.EMPLOYEENID).eq(employeeNID))
                        .fetch())
                .closeConnection()
                .getResultAsRecords();
        if (existingBeginDate.isNotEmpty() && existingEndDate.isNotEmpty()
                && areOverlapping(existingBeginDate.getValue(0, CONTRACTS.BEGINDATE),
                existingEndDate.getValue(0, CONTRACTS.ENDDATE),
                beginDate,
                endDate)) {
            return false;
        }
        final int insertedTuples = new QueryBuilder()
                .createConnection()
                .queryAction(db -> db.insertInto(CONTRACTS)
                        .values(contractID,
                                subscriptionDate,
                                beginDate,
                                endDate,
                                BigDecimal.valueOf(salary),
                                employerNID,
                                employeeNID)
                        .execute())
                .closeConnection()
                .getResultAsInt();
        return insertedTuples == 1;
    }

    private static boolean areOverlapping(final LocalDate contract1Begin, final LocalDate contract1End,
                                   final LocalDate contract2Begin, final LocalDate contract2End) {
        final int begin1 = contract1Begin.getDayOfYear();
        final int begin2 = contract2Begin.getDayOfYear();
        final int end1 = contract1End.getDayOfYear();
        final int end2 = contract2End.getDayOfYear();
        return Math.max(begin1, begin2) < Math.min(end1, end2);
    }

}
