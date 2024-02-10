package org.apdb4j.core.managers;

import lombok.NonNull;
import org.apdb4j.util.QueryBuilder;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.YearMonth;

import static org.apdb4j.db.Tables.COSTS;
import static org.apdb4j.db.Tables.FACILITIES;
import static org.apdb4j.db.Tables.PARK_SERVICES;

/**
 * Contains all the SQL queries that are related to the SHOP entity.
 */
public final class ShopManager {

    private static final QueryBuilder DB = new QueryBuilder();

    private ShopManager() {
    }

    /**
     * Performs the SQL query that adds a new shop without a description.
     * @param shopID the shop identifier.
     * @param name the name of the new shop.
     * @param openingTime the opening time of the new shop.
     * @param closingTime the closing time of the new shop.
     * @param type the type of the new shop.
     * @return {@code true} on successful tuple insertion
     */
    public static boolean addNewShop(final @NonNull String shopID,
                                     final @NonNull String name,
                                     final @NonNull LocalTime openingTime, final @NonNull LocalTime closingTime,
                                     final @NonNull String type) {
        return addNewShopWithDescription(shopID, name, openingTime, closingTime, type, null);
    }

    /**
     * Performs the SQL query that adds a new shop with the provided description.
     * @param shopID the shop identifier.
     * @param name the name of the new shop.
     * @param openingTime the opening time of the new shop.
     * @param closingTime the closing time of the new shop.
     * @param type the type of the new shop.
     * @param description the possible description of the new shop. If the value of this parameter is {@code null}, the
     *                    behaviour of this method will be the same of the method
     *                    {@link #addNewShop(String, String, LocalTime, LocalTime, String)}.
     * @return {@code true} on successful tuple insertion
     */
    public static boolean addNewShopWithDescription(final @NonNull String shopID,
                                                    final @NonNull String name,
                                                    final @NonNull LocalTime openingTime, final @NonNull LocalTime closingTime,
                                                    final @NonNull String type,
                                                    final String description) {
        return DB.createConnection()
                .queryAction(db -> {
                    db.transaction(configuration -> {
                        configuration.dsl()
                                .insertInto(PARK_SERVICES)
                                .values(shopID, name, BigDecimal.ZERO, 0, type, description, false)
                                .execute();
                        configuration.dsl()
                                .insertInto(FACILITIES)
                                .values(shopID, openingTime, closingTime, true)
                                .execute();
                    });
                    return 1;
                })
                .closeConnection()
                .getResultAsInt() == 1;
    }

    /**
     * Performs the SQL query that adds the money info for the provided shop in the provided month.
     * @param shopID the shop identifier. If the value of this parameter is not the identifier of a shop,
     *               the query will not be executed.
     * @param month the month.
     * @param expense the expenses made by the provided shop.
     * @param revenue the revenue made by the provided shop.
     * @return {@code true} on successful tuple insertion
     */
    public static boolean addNewMonthlyCost(final @NonNull String shopID, final YearMonth month,
                                            final double expense, final double revenue) {
        final int insertedTuples = DB.createConnection()
                .queryAction(db -> db.insertInto(COSTS)
                        .values(shopID, revenue, expense, month.getMonthValue(), month.getYear())
                        .execute())
                .closeConnection()
                .getResultAsInt();
        return insertedTuples == 1;
    }

    /**
     * Performs the SQL query that modifies the date of the money info for the given shop.
     * If in the {@link org.apdb4j.db.tables.Costs} table does not exist a tuple whose values of
     * {@link org.apdb4j.db.tables.Costs#MONTH} and {@link org.apdb4j.db.tables.Costs#YEAR} are respectively
     * {@code actualMonth.getMonth()} and {@code actualMonth.getYear()}, the query will not be executed.
     * @param shopID the shop identifier. If the value of this parameter is not the identifier of a shop,
     *               the query will not be executed.
     * @param actualMonth the actual month of the money info.
     * @param newMonth the new month of the money info.
     * @return {@code true} on successful tuple insertion
     */
    public static boolean editCostDate(final @NonNull String shopID,
                                       final YearMonth actualMonth,
                                       final YearMonth newMonth) {
        final int modifiedTuples = DB.createConnection()
                .queryAction(db -> db.update(COSTS)
                        .set(COSTS.MONTH, newMonth.getMonthValue())
                        .set(COSTS.YEAR, newMonth.getYear())
                        .whereExists(db.select()
                                .from(COSTS)
                                .where(COSTS.SHOPID.eq(shopID)
                                .and(COSTS.MONTH.eq(actualMonth.getMonthValue()))
                                .and(COSTS.YEAR.eq(actualMonth.getYear()))))
                        .and(COSTS.SHOPID.eq(shopID))
                        .execute())
                .closeConnection()
                .getResultAsInt();
        return modifiedTuples == 1;
    }

    /**
     * Performs the SQL query that edits the money info of the given shop.
     * If in the {@link org.apdb4j.db.tables.Costs} table does not exist a tuple whose values of
     * {@link org.apdb4j.db.tables.Costs#MONTH} and {@link org.apdb4j.db.tables.Costs#YEAR} are respectively
     * {@code month.getMonth()} and {@code month.getYear()}, the query will not be executed.
     * @param shopID the shop identifier. If the value of this parameter is not the identifier of a shop,
     *               the query will not be executed.
     * @param month the month of the money info.
     * @param newRevenue the new revenue of the money info.
     * @param newExpense the new expense of the money info.
     * @return {@code true} on successful tuple insertion
     */
    public static boolean editCostMoney(final @NonNull String shopID,
                                        final YearMonth month,
                                        final double newRevenue, final double newExpense) {
        final int modifiedTuples = DB.createConnection()
                .queryAction(db -> db.update(COSTS)
                        .set(COSTS.REVENUE, BigDecimal.valueOf(newRevenue))
                        .set(COSTS.EXPENSES, BigDecimal.valueOf(newExpense))
                        .whereExists(db.select()
                                .from(COSTS)
                                .where(COSTS.SHOPID.eq(shopID))
                                .and(COSTS.MONTH.eq(month.getMonthValue()))
                                .and(COSTS.YEAR.eq(month.getYear())))
                        .and(COSTS.SHOPID.eq(shopID))
                        .execute())
                .closeConnection()
                .getResultAsInt();
        return modifiedTuples == 1;
    }

}
