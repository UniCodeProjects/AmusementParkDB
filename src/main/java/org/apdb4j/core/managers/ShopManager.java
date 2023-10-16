package org.apdb4j.core.managers;

import lombok.NonNull;
import org.apdb4j.util.QueryBuilder;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.YearMonth;

import static org.apdb4j.db.Tables.COSTS;
import static org.apdb4j.db.Tables.FACILITIES;

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
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     * @return {@code true} on successful tuple insertion
     */
    public static boolean addNewShop(final @NonNull String shopID,
                                     final @NonNull String name,
                                     final @NonNull LocalTime openingTime, final @NonNull LocalTime closingTime,
                                     final @NonNull String type,
                                     final @NonNull String account) {
        return addNewShopWithDescription(shopID, name, openingTime, closingTime, type, null, account);
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
     *                    {@link ShopManager#addNewShop(String, String, LocalTime, LocalTime, String, String)}.
     * @param account the account that is performing this operation. If the account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     * @return {@code true} on successful tuple insertion
     */
    public static boolean addNewShopWithDescription(final @NonNull String shopID,
                                                    final @NonNull String name,
                                                    final @NonNull LocalTime openingTime, final @NonNull LocalTime closingTime,
                                                    final @NonNull String type,
                                                    final String description,
                                                    final @NonNull String account) {
        final int insertedTuples = DB.createConnection()
                .queryAction(db -> db.insertInto(FACILITIES)
                        .values(shopID, name, openingTime, closingTime, type, description, true)
                        .execute())
                .closeConnection()
                .getResultAsInt();
        return insertedTuples == 1;
    }

    /**
     * Performs the SQL query that adds the money info for the provided shop in the provided month.
     * @param shopID the shop identifier. If the value of this parameter is not the identifier of a shop,
     *               the query will not be executed.
     * @param month the month.
     * @param expense the expenses made by the provided shop.
     * @param revenue the revenue made by the provided shop.
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     * @return {@code true} on successful tuple insertion
     */
    public static boolean addNewMonthlyCost(final @NonNull String shopID, final YearMonth month,
                                            final double expense, final double revenue,
                                            final @NonNull String account) {
        final int insertedTuples = DB.createConnection()
                .queryAction(db -> db.insertInto(COSTS)
                        .values(shopID, month.getMonthValue(), month.getYear(), expense, revenue)
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
     * @param account the account that is performing this operation.
     *                If this account has not the permissions to accomplish the operation, the query will not be executed.
     * @return {@code true} on successful tuple insertion 
     */
    public static boolean editCostDate(final @NonNull String shopID,
                                       final YearMonth actualMonth,
                                       final YearMonth newMonth,
                                       final @NonNull String account) {
        // TODO: use where not exists?
        if (costTableNotPresent(actualMonth)) {
            return false;
        }
        final int modifiedTuples = DB.createConnection()
                .queryAction(db -> db.update(COSTS)
                        .set(COSTS.MONTH, newMonth.getMonthValue())
                        .set(COSTS.YEAR, newMonth.getYear())
                        .where(COSTS.SHOPID.eq(shopID))
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
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     * @return {@code true} on successful tuple insertion 
     */
    public static boolean editCostMoney(final @NonNull String shopID,
                                        final YearMonth month,
                                        final double newRevenue, final double newExpense,
                                        final @NonNull String account) {
        if (costTableNotPresent(month)) {
            return false;
        }
        final int modifiedTuples = DB.createConnection()
                .queryAction(db -> db.update(COSTS)
                        .set(COSTS.REVENUE, BigDecimal.valueOf(newRevenue))
                        .set(COSTS.EXPENSES, BigDecimal.valueOf(newExpense))
                        .where(COSTS.SHOPID.eq(shopID))
                        .execute())
                .closeConnection()
                .getResultAsInt();
        return modifiedTuples == 1;
    }

    private static boolean costTableNotPresent(YearMonth currentDate) {
        final int countedTuples = DB.createConnection()
                .queryAction(db -> db.selectCount()
                        .from(COSTS)
                        .where(COSTS.MONTH.eq(currentDate.getMonthValue()))
                        .and(COSTS.YEAR.eq(currentDate.getYear()))
                        .fetchOne(0, int.class))
                .closeConnection()
                .getResultAsInt();
        return countedTuples == 0;
    }

}
