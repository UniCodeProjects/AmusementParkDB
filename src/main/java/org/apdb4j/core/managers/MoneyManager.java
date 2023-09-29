package org.apdb4j.core.managers;

import lombok.NonNull;
import org.apache.commons.lang3.tuple.Pair;
import java.time.LocalDate;
import java.util.List;

/**
 * Contains all the SQL queries that are related to money.
 */
public class MoneyManager {

    /**
     * Performs the SQL query that retrieves the list of the provided months with the related income.
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     * @param months the months to be examined. If there is no money data for at least one of the provided months,
     *               the query will not be executed.
     * @return a list of months and years paired with the related income.
     */
    static @NonNull List<Pair<LocalDate, Double>> getIncomes(@NonNull String account, @NonNull LocalDate... months) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Performs the SQL query that adds in the {@link org.apdb4j.db.tables.MonthlyRecaps} table the tuple
     * with the economic data of the previous month.
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     */
    static void addRecapForPreviousMonth(@NonNull String account) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
    // This operation needs to be done automatically at the end of every month.

}
