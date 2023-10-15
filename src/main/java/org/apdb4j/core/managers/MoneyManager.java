package org.apdb4j.core.managers;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.NonNull;
import org.apdb4j.util.QueryBuilder;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.impl.DSL;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.Objects;

import static org.apdb4j.db.Tables.*;

/**
 * Contains all the SQL queries that are related to money.
 */
public final class MoneyManager {

    private MoneyManager() {
    }

    /**
     * Performs the SQL query that retrieves the list of the provided months with the related income.
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     * @param months the months to be examined. If there is no money data for at least one of the provided months,
     *               the query will not be executed.
     * @return a list of months and years paired with the related income.
     */
    public static @NonNull Result<Record> getIncomes(final @NonNull String account,
                                                     final @NonNull LocalDate... months) {
        if (months.length == 0) {
            throw new IllegalArgumentException("months cannot be empty");
        }
        final var result = executeSelectionForSingleMonth(months[0]);
        for (int i = 1; i < months.length; i++) {
            result.add(executeSelectionForSingleMonth(months[i]).get(0));
        }
        return result;
    }

    /**
     * Performs the SQL query that adds in the {@link org.apdb4j.db.tables.MonthlyRecaps} table the tuple
     * with the economic data of the previous month.
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     * @return {@code true} if the tuple is inserted, {@code false} otherwise.
     */
    public static boolean addRecapForPreviousMonth(final @NonNull String account) {
        final BigDecimal revenue = previousMonthTicketsIncome().add(previousMonthShopIncome());
        final BigDecimal expenses = previousMonthSalaries().add(previousMonthMaintenancesCosts());
        final BigDecimal result = revenue.subtract(expenses);
        return new QueryBuilder().createConnection()
                .queryAction(db -> db.insertInto(MONTHLY_RECAPS)
                        .values(getPreviousMonthFirstDate(), result)
                        .execute())
                .closeConnection()
                .getResultAsInt() == 1;
    }
    // This operation needs to be done automatically at the end of every month.

    private static Result<Record> executeSelectionForSingleMonth(final LocalDate month) {
        final QueryBuilder queryBuilder = new QueryBuilder();
        return queryBuilder.createConnection()
                .queryAction(db -> db.select()
                        .from(MONTHLY_RECAPS)
                        .where(MONTHLY_RECAPS.DATE.eq(month))
                        .fetch())
                .closeConnection()
                .getResultAsRecords();
    }

    private static int getPreviousMonth() {
        final var currentDate = LocalDate.now();
        final var currentMonth = currentDate.getMonth();
        return currentMonth == Month.JANUARY ? Month.DECEMBER.getValue() : currentMonth.getValue() - 1;
    }

    private static int getPreviousYear() {
        final var currentDate = LocalDate.now();
        return getPreviousMonth() == Month.DECEMBER.getValue() ? currentDate.getYear() - 1 : currentDate.getYear();
    }

    private static LocalDate getPreviousMonthFirstDate() {
        return LocalDate.of(getPreviousYear(), getPreviousMonth(), 1);
    }

    private static LocalDate getPreviousMonthLastDate() {
        final var previousYear = getPreviousYear();
        final var previousMonth = getPreviousMonth();
        return LocalDate.of(previousYear, previousMonth,
                YearMonth.of(previousYear, previousMonth).lengthOfMonth());
    }

    private static BigDecimal previousMonthSalaries() {
        return (BigDecimal) new QueryBuilder().createConnection()
                .queryAction(db -> db.select(DSL.sum(CONTRACTS.SALARY))
                        .from(CONTRACTS)
                        .where(CONTRACTS.ENDDATE.isNull()
                                .or(CONTRACTS.ENDDATE.greaterOrEqual(getPreviousMonthLastDate())))
                        .fetch())
                .closeConnection()
                .getResultAsRecords()
                .get(0)
                .get(0);
    }

    private static BigDecimal previousMonthTicketsIncome() {
        return (BigDecimal) new QueryBuilder().createConnection()
                .queryAction(db -> db.select(DSL.sum(TICKET_TYPES.PRICE))
                        .from(TICKETS, ATTRIBUTIONS, TICKET_TYPES)
                        .where(TICKETS.TICKETID.eq(ATTRIBUTIONS.TICKETID)
                                .and(ATTRIBUTIONS.YEAR.eq(TICKET_TYPES.YEAR))
                                .and(ATTRIBUTIONS.TYPE.eq(TICKET_TYPES.TYPE))
                                .and(ATTRIBUTIONS.CATEGORY.eq(TICKET_TYPES.CATEGORY))
                                .and(TICKETS.PURCHASEDATE.between(getPreviousMonthFirstDate(), getPreviousMonthLastDate())))
                        .fetch())
                .closeConnection()
                .getResultAsRecords()
                .get(0)
                .get(0);
    }

    private static BigDecimal previousMonthMaintenancesCosts() {
        return (BigDecimal) new QueryBuilder().createConnection()
                .queryAction(db -> db.select(DSL.sum(MAINTENANCES.PRICE))
                        .from(MAINTENANCES)
                        .where(MAINTENANCES.DATE.between(getPreviousMonthFirstDate(), getPreviousMonthLastDate()))
                        .fetch())
                .closeConnection()
                .getResultAsRecords()
                .get(0)
                .get(0);
    }

    @SuppressFBWarnings(value = "NP_NULL_ON_SOME_PATH",
            justification = "False positive. Check done with Objects.requireNonNull()")
    private static BigDecimal previousMonthShopIncome() {
        final var previousMonthCosts = new QueryBuilder().createConnection()
                .queryAction(db -> db.select(DSL.sum(COSTS.REVENUE).as("revenues"), DSL.sum(COSTS.EXPENSES).as("expenses"))
                        .from(COSTS)
                        .where(COSTS.MONTH.eq(getPreviousMonth())
                                .and(COSTS.YEAR.eq(getPreviousYear())))
                        .fetch())
                .closeConnection()
                .getResultAsRecords()
                .get(0);
        return ((BigDecimal) Objects.requireNonNull(previousMonthCosts.get("revenues")))
                .subtract((BigDecimal) Objects.requireNonNull(previousMonthCosts.get("expenses")));
    }
}
