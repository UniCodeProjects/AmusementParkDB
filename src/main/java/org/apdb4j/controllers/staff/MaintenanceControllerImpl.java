package org.apdb4j.controllers.staff;

import lombok.NonNull;
import org.apdb4j.core.managers.MaintenanceManager;
import org.apdb4j.util.QueryBuilder;
import org.apdb4j.view.staff.tableview.MaintenanceTableItem;
import org.apdb4j.view.staff.tableview.TableItem;
import org.jooq.Condition;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DSL;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.apdb4j.db.Tables.*;

/**
 * An implementation of a maintenance controller.
 */
public class MaintenanceControllerImpl implements MaintenanceController {

    private String errorMessage;

    /**
     * {@inheritDoc}
     * @throws org.jooq.exception.DataAccessException if query fails
     */
    @Override
    public <T extends TableItem> Collection<T> getData() {
        return extractMaintenanceData(searchQuery(DSL.condition(true)));
    }

    /**
     * {@inheritDoc}
     * @throws org.jooq.exception.DataAccessException if query fails
     */
    @Override
    public <T extends TableItem> T addData(final T item) {
        final MaintenanceTableItem maintenance = (MaintenanceTableItem) item;
        final boolean successfulQuery = MaintenanceManager.addNewMaintenance(maintenance.getFacilityID(),
                maintenance.getPrice(),
                maintenance.getDescription(),
                maintenance.getDate(),
                "",
                maintenance.getEmployeeIDs());
        if (!successfulQuery) {
            errorMessage = "Something went wrong.";
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
        final MaintenanceTableItem maintenance = (MaintenanceTableItem) item;
        new QueryBuilder().createConnection()
                .queryAction(db -> db.update(MAINTENANCES
                                .join(RESPONSIBILITIES)
                                .on(MAINTENANCES.FACILITYID.eq(RESPONSIBILITIES.FACILITYID)))
                        .set(MAINTENANCES.PRICE, BigDecimal.valueOf(maintenance.getPrice()))
                        .set(MAINTENANCES.DESCRIPTION, maintenance.getDescription())
                        .where(MAINTENANCES.FACILITYID.eq(maintenance.getFacilityID()))
                        .and(MAINTENANCES.DATE.eq(maintenance.getDate()))
                        .and(RESPONSIBILITIES.EMPLOYEENID.eq(maintenance.getEmployeeIDs()))
                        .execute())
                .closeConnection();
        return item;
    }

    /**
     * {@inheritDoc}
     * @throws org.jooq.exception.DataAccessException if query fails
     */
    @Override
    public <T extends TableItem> Collection<T> filter(final String facilityID) {
        return extractMaintenanceData(searchQuery(MAINTENANCES.FACILITYID.containsIgnoreCase(facilityID)));
    }

    /**
     * {@inheritDoc}
     * @throws org.jooq.exception.DataAccessException if query fails
     */
    @Override
    public <T extends MaintenanceTableItem> Collection<T> filterByDate(final LocalDate date) {
        return extractMaintenanceData(searchQuery(MAINTENANCES.DATE.eq(date)));
    }

    /**
     * {@inheritDoc}
     * @throws org.jooq.exception.DataAccessException if query fails
     */
    @Override
    public <T extends MaintenanceTableItem> Collection<T> filterByRides() {
        final Result<Record> result = new QueryBuilder().createConnection()
                .queryAction(db -> db.select(MAINTENANCES.asterisk(),
                                RESPONSIBILITIES.asterisk().except(RESPONSIBILITIES.FACILITYID, RESPONSIBILITIES.DATE))
                        .from(MAINTENANCES)
                        .join(RESPONSIBILITIES)
                        .onKey()
                        .join(RIDES)
                        .on(RIDES.RIDEID.eq(MAINTENANCES.FACILITYID))
                        .where(RIDES.RIDEID.eq(MAINTENANCES.FACILITYID))
                        .fetch())
                .closeConnection()
                .getResultAsRecords();
        return extractMaintenanceData(result);
    }

    /**
     * {@inheritDoc}
     * @throws org.jooq.exception.DataAccessException if query fails
     */
    @Override
    public <T extends MaintenanceTableItem> Collection<T> filterByShops() {
        final Result<Record> result = new QueryBuilder().createConnection()
                .queryAction(db -> db.select(MAINTENANCES.asterisk(),
                                RESPONSIBILITIES.asterisk().except(RESPONSIBILITIES.FACILITYID, RESPONSIBILITIES.DATE))
                        .from(MAINTENANCES)
                        .join(RESPONSIBILITIES)
                        .onKey()
                        .join(FACILITIES)
                        .on(FACILITIES.FACILITYID.eq(MAINTENANCES.FACILITYID))
                        .where(FACILITIES.FACILITYID.eq(MAINTENANCES.FACILITYID))
                        .and(FACILITIES.ISSHOP.isTrue())
                        .fetch())
                .closeConnection()
                .getResultAsRecords();
        return extractMaintenanceData(result);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull Optional<String> getErrorMessage() {
        return Optional.ofNullable(errorMessage);
    }

    private Result<Record> searchQuery(final Condition condition) {
        return new QueryBuilder().createConnection()
                .queryAction(db -> db.select(MAINTENANCES.asterisk(),
                                RESPONSIBILITIES.asterisk().except(RESPONSIBILITIES.FACILITYID, RESPONSIBILITIES.DATE))
                        .from(MAINTENANCES)
                        .join(RESPONSIBILITIES)
                        .onKey()
                        .where(condition)
                        .fetch())
                .closeConnection()
                .getResultAsRecords();
    }

    @SuppressWarnings("unchecked")
    private <T extends TableItem> Collection<T> extractMaintenanceData(final Result<Record> result) {
        final List<T> data = new ArrayList<>();
        result.forEach(record -> data.add((T) new MaintenanceTableItem(record.get(MAINTENANCES.FACILITYID),
                record.get(MAINTENANCES.PRICE).doubleValue(),
                record.get(MAINTENANCES.DESCRIPTION),
                record.get(MAINTENANCES.DATE),
                record.get(RESPONSIBILITIES.EMPLOYEENID))));
        return data;
    }

}
