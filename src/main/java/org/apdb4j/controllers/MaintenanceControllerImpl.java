package org.apdb4j.controllers;

import lombok.NonNull;
import org.apdb4j.util.QueryBuilder;
import org.apdb4j.view.staff.tableview.MaintenanceTableItem;
import org.apdb4j.view.staff.tableview.TableItem;
import org.jooq.Condition;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.impl.DSL;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.apdb4j.db.Tables.MAINTENANCES;
import static org.apdb4j.db.Tables.RESPONSIBILITIES;

/**
 * An implementation of a maintenance controller.
 */
public class MaintenanceControllerImpl implements MaintenanceController {

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends TableItem> Collection<T> getData() {
        return extractMaintenanceData(searchQuery(DSL.condition(true)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends TableItem> T addData(final T item) {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends TableItem> T editData(final T item) {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends TableItem> Collection<T> filter(final String facilityID) {
        return extractMaintenanceData(searchQuery(MAINTENANCES.FACILITYID.containsIgnoreCase(facilityID)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends TableItem> Collection<T> filterByDate(final LocalDate date) {
        return extractMaintenanceData(searchQuery(MAINTENANCES.DATE.eq(date)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull Optional<String> getErrorMessage() {
        return Optional.empty();
    }

    private Result<Record> searchQuery(final Condition condition) {
        return new QueryBuilder().createConnection()
                .queryAction(db -> db.select(MAINTENANCES.asterisk(),
                                RESPONSIBILITIES.asterisk().except(RESPONSIBILITIES.FACILITYID, RESPONSIBILITIES.DATE))
                        .from(MAINTENANCES)
                        .join(RESPONSIBILITIES)
                        .onKey()
                        .and(condition)
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
