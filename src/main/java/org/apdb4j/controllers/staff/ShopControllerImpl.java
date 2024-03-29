package org.apdb4j.controllers.staff;

import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.apdb4j.core.managers.ShopManager;
import org.apdb4j.util.QueryBuilder;
import org.apdb4j.view.staff.tableview.ShopTableItem;
import org.apdb4j.view.staff.tableview.TableItem;
import org.jooq.Condition;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DSL;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.apdb4j.db.Tables.*;

/**
 * Implementation of a shop controller.
 */
public class ShopControllerImpl implements ShopController {

    private String errorMessage;

    /**
     * {@inheritDoc}
     * @throws org.jooq.exception.DataAccessException if query fails
     */
    @Override
    public <T extends TableItem> Collection<T> getData() {
        return extractShopData(searchQuery());
    }

    /**
     * {@inheritDoc}
     * @throws org.jooq.exception.DataAccessException if query fails
     */
    @Override
    public ShopTableItem addMonthlyCost(final ShopTableItem shop) {
        final boolean queryResult = ShopManager.addNewMonthlyCost(shop.getId(),
                shop.getYearMonth(),
                shop.getExpenses(),
                shop.getRevenue()
        );
        if (!queryResult) {
            throw new DataAccessException("Could not add a new monthly cost.");
        }
        return shop;
    }

    /**
     * {@inheritDoc}
     * @throws org.jooq.exception.DataAccessException if query fails
     */
    @Override
    public <T extends TableItem> Collection<T> getData(final String shopID) {
        return extractShopData(searchQuery(FACILITIES.FACILITYID.eq(shopID)));
    }

    /**
     * {@inheritDoc}
     * @throws org.jooq.exception.DataAccessException if query fails
     */
    @Override
    public <T extends TableItem> T addData(final T item) {
        final ShopTableItem shop = (ShopTableItem) item;
        boolean queryResult;
        if (shop.getDescription().isBlank()) {
            queryResult = ShopManager.addNewShop(shop.getId(),
                    shop.getName(),
                    shop.getOpeningTime(),
                    shop.getClosingTime(),
                    shop.getType()
            );
        } else {
            queryResult = ShopManager.addNewShopWithDescription(shop.getId(),
                    shop.getName(),
                    shop.getOpeningTime(),
                    shop.getClosingTime(),
                    shop.getType(),
                    shop.getDescription()
            );
        }
        if (!queryResult) {
            errorMessage = "Something went wrong";
            throw new DataAccessException(errorMessage);
        }
        return item;
    }

    /**
     * {@inheritDoc}
     * @throws org.jooq.exception.DataAccessException if query fails
     */
    @Override
    public <T extends TableItem> T editData(final T item) {
        final ShopTableItem shop = (ShopTableItem) item;
        new QueryBuilder().createConnection()
                .queryAction(db -> {
                    db.transaction(configuration -> {
                        configuration.dsl()
                                .update(PARK_SERVICES)
                                .set(PARK_SERVICES.NAME, shop.getName())
                                .set(PARK_SERVICES.TYPE, shop.getType())
                                .set(PARK_SERVICES.DESCRIPTION, shop.getDescription())
                                .where(PARK_SERVICES.PARKSERVICEID.eq(shop.getId()))
                                .execute();
                        configuration.dsl()
                                .update(FACILITIES)
                                .set(FACILITIES.OPENINGTIME, shop.getOpeningTime())
                                .set(FACILITIES.CLOSINGTIME, shop.getClosingTime())
                                .where(FACILITIES.FACILITYID.eq(shop.getId()))
                                .execute();
                        configuration.dsl()
                                .update(COSTS)
                                .set(COSTS.EXPENSES, BigDecimal.valueOf(shop.getExpenses()))
                                .set(COSTS.REVENUE, BigDecimal.valueOf(shop.getRevenue()))
                                .where(COSTS.SHOPID.eq(shop.getId()))
                                .execute();
                    });
                    return 1;
                })
                .closeConnection();
        return item;
    }

    /**
     * {@inheritDoc}
     * @throws org.jooq.exception.DataAccessException if query fails
     */
    @Override
    public <T extends TableItem> Collection<T> filter(final String shopName) {
        return extractShopData(searchQuery(PARK_SERVICES.NAME.containsIgnoreCase(shopName)));
    }

    /**
     * {@inheritDoc}
     * @throws org.jooq.exception.DataAccessException if query fails
     */
    @Override
    public List<String> getExistingTypes() {
        return Arrays.stream(new QueryBuilder().createConnection()
                        .queryAction(db -> db.selectDistinct(PARK_SERVICES.TYPE)
                                .from(PARK_SERVICES)
                                .fetch())
                        .closeConnection()
                        .getResultAsRecords()
                        .sortAsc(PARK_SERVICES.TYPE)
                        .intoArray(PARK_SERVICES.TYPE))
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull Optional<String> getErrorMessage() {
        return Optional.ofNullable(errorMessage);
    }

    private Result<Record> searchQuery() {
        return searchQuery(DSL.condition(true));
    }

    private Result<Record> searchQuery(final Condition condition) {
        return new QueryBuilder().createConnection()
                .queryAction(db -> db.select(FACILITIES.FACILITYID,
                                PARK_SERVICES.NAME,
                                FACILITIES.OPENINGTIME,
                                FACILITIES.CLOSINGTIME,
                                PARK_SERVICES.TYPE,
                                PARK_SERVICES.DESCRIPTION,
                                COSTS.EXPENSES,
                                COSTS.REVENUE,
                                COSTS.MONTH,
                                COSTS.YEAR)
                        .from(PARK_SERVICES)
                        .join(FACILITIES)
                        .on(FACILITIES.FACILITYID.eq(PARK_SERVICES.PARKSERVICEID))
                        .leftJoin(COSTS)
                        .on(COSTS.SHOPID.eq(PARK_SERVICES.PARKSERVICEID))
                        .where(FACILITIES.ISSHOP.isTrue())
                        .and(condition)
                        .fetch())
                .closeConnection()
                .getResultAsRecords();
    }

    @SuppressWarnings("unchecked")
    private <T extends TableItem> List<T> extractShopData(final Result<Record> result) {
        final List<T> data = new ArrayList<>();
        result.forEach(record -> data.add((T) new ShopTableItem(record.get(FACILITIES.FACILITYID),
                record.get(PARK_SERVICES.NAME),
                record.get(FACILITIES.OPENINGTIME),
                record.get(FACILITIES.CLOSINGTIME),
                record.get(PARK_SERVICES.TYPE),
                StringUtils.defaultIfBlank(record.get(PARK_SERVICES.DESCRIPTION), ""),
                record.get(COSTS.EXPENSES) == null ? null : record.get(COSTS.EXPENSES).doubleValue(),
                record.get(COSTS.REVENUE) == null ? null : record.get(COSTS.REVENUE).doubleValue(),
                record.get(COSTS.YEAR) == null && record.get(COSTS.MONTH) == null
                        ? null
                        : YearMonth.of(record.get(COSTS.YEAR), record.get(COSTS.MONTH)))));
        return data;
    }

}
