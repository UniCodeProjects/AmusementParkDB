package org.apdb4j.core.managers;

import lombok.NonNull;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Contains all the SQL queries that are related to the SHOP entity.
 */
public class ShopManager {

    /**
     * Performs the SQL query that adds a new shop without a description.
     * @param shopID the shop identifier.
     * @param name the name of the new shop.
     * @param openingTime the opening time of the new shop.
     * @param closingTime the closing time of the new shop.
     * @param type the type of the new shop.
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     */
    static void addNewShop(@NonNull String shopID,
                    @NonNull String name,
                    @NonNull LocalTime openingTime, @NonNull LocalTime closingTime,
                    @NonNull String type,
                    @NonNull String account) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Performs the SQL query that adds a new shop with the provided description.
     * @param shopID the shop identifier.
     * @param name the name of the new shop.
     * @param openingTime the opening time of the new shop.
     * @param closingTime the closing time of the new shop.
     * @param type the type of the new shop.
     * @param description the possible description of the new shop. If the value of this parameter is {@code null}, the
     *                    behavior of this method will be the same of the method
     *                    {@link ShopManager#addNewShop(String, String, LocalTime, LocalTime, String, String)}.
     * @param account the account that is performing this operation. If the account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     */
    static void addNewShopWithDescription(@NonNull String shopID,
                                   @NonNull String name,
                                   @NonNull LocalTime openingTime, @NonNull LocalTime closingTime,
                                   @NonNull String type,
                                   String description,
                                   @NonNull String account) {
        throw new UnsupportedOperationException("Not implemented yet");
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
     */
    static void addNewMonthlyCost(@NonNull String shopID, LocalDate month, double expense, double revenue,
                                  @NonNull String account) {
        throw new UnsupportedOperationException("Not implemented yet");
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
     */
    static void editCostDate(@NonNull String shopID,
                      LocalDate actualMonth,
                      LocalDate newMonth,
                      @NonNull String account) {
        throw new UnsupportedOperationException("Not implemented yet");
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
     */
    static void editCostMoney(@NonNull String shopID,
                       LocalDate month,
                       double newRevenue, double newExpense,
                       @NonNull String account) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

}
