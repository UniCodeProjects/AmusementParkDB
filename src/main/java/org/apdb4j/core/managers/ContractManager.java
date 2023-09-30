package org.apdb4j.core.managers;

import lombok.NonNull;

import java.time.LocalDate;

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
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     */
    public static void signNewContract(final @NonNull String contractID,
                                       final @NonNull String employeeNID, final @NonNull String employerNID,
                                       final @NonNull LocalDate subscriptionDate,
                                       final @NonNull LocalDate beginDate, final LocalDate endDate,
                                       final double salary,
                                       final @NonNull String account) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

}
