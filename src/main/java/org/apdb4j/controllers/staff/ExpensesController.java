package org.apdb4j.controllers.staff;

import org.apache.commons.lang3.tuple.Pair;

import java.time.YearMonth;

/**
 * A staff controller that manages the expenses.
 */
public interface ExpensesController {

    /**
     * Returns the income for each of the two given months.
     * @param m1 first month
     * @param m2 second month
     * @return the income for both months as a pair
     * @throws IllegalArgumentException when no income is found for the given parameters, or {@code m1} is after {@code m2}
     * @throws IllegalStateException if {@link #getIncomes(YearMonth...)}
     *                               returns more or less than two values
     */
    Pair<Double, Double> getIncome(YearMonth m1, YearMonth m2);

    /**
     * Adds a monthly recap for the previous month.
     * @throws IllegalStateException on fail
     */
    void addRecapForPreviousMonth();

}
