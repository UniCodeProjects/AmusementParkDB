package org.apdb4j.controllers.staff;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apdb4j.core.managers.MoneyManager;

import java.time.YearMonth;
import java.util.List;

/**
 * Implementation of an expenses controller.
 */
public class ExpensesControllerImpl implements ExpensesController {

    /**
     * {@inheritDoc}
     * @throws IllegalArgumentException when no income is found for the given parameters, or {@code m1} is after {@code m2}
     * @throws IllegalStateException if {@link org.apdb4j.core.managers.MoneyManager#getIncomes(String, YearMonth...)}
     *                               returns more or less than two values
     */
    @Override
    public Pair<Double, Double> getIncome(final YearMonth m1, final YearMonth m2) {
        if (m1.isAfter(m2)) {
            throw new IllegalArgumentException("Wrong month order: " + m1 + " is after " + m2);
        }
        final List<Pair<YearMonth, Double>> incomes = MoneyManager.getIncomes("", m1, m2).stream().toList();
        if (incomes.size() != 2) {
            throw new IllegalStateException("MoneyManager returned " + incomes.size() + " values instead of 2");
        }
        return new ImmutablePair<>(incomes.get(0).getRight(), incomes.get(1).getRight());
    }

    /**
     * {@inheritDoc}
     * @throws IllegalStateException on fail
     */
    @Override
    public void addRecapForPreviousMonth() {
        final boolean success = MoneyManager.addRecapForPreviousMonth("");
        if (!success) {
            throw new IllegalStateException("Monthly recap could not get added.");
        }
    }

}
