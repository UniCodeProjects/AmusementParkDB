package org.apdb4j.core.managers;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.NonNull;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apdb4j.util.QueryBuilder;
import org.jooq.Result;
import org.jooq.impl.DSL;
import org.jooq.Record;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import static org.apdb4j.db.Tables.*;

/**
 * Contains all the SQL queries that are related to money.
 */
public final class MoneyManager {

    private MoneyManager() {
    }

    /**
     * Performs the SQL query that retrieves a collection of the provided months with the related income.
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     * @param months the months to be examined. If there is no money data for at least one of the provided months,
     *               the query will not be executed.
     * @return a collection of months and years paired with the related income.
     */
    public static @NonNull Collection<Pair<YearMonth, Double>> getIncomes(final @NonNull String account,
                                                                          final @NonNull YearMonth... months) {
        if (months.length == 0) {
            throw new IllegalArgumentException("months cannot be empty");
        }
        final Collection<Pair<YearMonth, Double>> result = new ArrayList<>();
        for (final YearMonth month : months) {
            result.add(executeSelectionForSingleMonth(month));
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
                        .values(getPreviousMonthFirstDay(), result)
                        .execute())
                .closeConnection()
                .getResultAsInt() == 1;
    } // This operation needs to be done automatically at the end of every month.

    // Returns the tuple of MONTHLY_RECAPS with the provided date as primary key
    private static Pair<YearMonth, Double> executeSelectionForSingleMonth(final YearMonth month) {
        final Result<Record> result = new QueryBuilder().createConnection()
                .queryAction(db -> db.select()
                        .from(MONTHLY_RECAPS)
                        .where(MONTHLY_RECAPS.DATE.eq(LocalDate.of(month.getYear(), month.getMonth(), 1)))
                        .fetch())
                .closeConnection()
                .getResultAsRecords();
        if (result.isNotEmpty()) {
            final Record record = result.get(0);
            return new ImmutablePair<>(month, record.get(MONTHLY_RECAPS.REVENUE).doubleValue());
        } else {
            throw new IllegalArgumentException("Money info for " + month + " do not exist in the database");
        }
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

    private static LocalDate getPreviousMonthFirstDay() {
        return LocalDate.of(getPreviousYear(), getPreviousMonth(), 1);
    }

    private static LocalDate getPreviousMonthLastDay() {
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
                                .or(CONTRACTS.ENDDATE.greaterOrEqual(getPreviousMonthLastDay())))
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
                                .and(TICKETS.PURCHASEDATE.between(getPreviousMonthFirstDay(), getPreviousMonthLastDay())))
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
                        .where(MAINTENANCES.DATE.between(getPreviousMonthFirstDay(), getPreviousMonthLastDay()))
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
