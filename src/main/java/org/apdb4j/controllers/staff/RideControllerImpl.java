package org.apdb4j.controllers.staff;

import lombok.NonNull;
import org.apdb4j.util.QueryBuilder;
import org.apdb4j.view.staff.tableview.RideTableItem;
import org.apdb4j.view.staff.tableview.TableItem;
import org.jooq.Condition;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.impl.DSL;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.apdb4j.db.Tables.*;

/**
 * An implementation of a ride controller.
 */
public class RideControllerImpl implements RideController {

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends TableItem> Collection<T> getData() {
        return extractRideData(searchQuery(DSL.condition(true)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends TableItem> T addData(final T item) throws SQLException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends TableItem> T editData(final T item) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends TableItem> Collection<T> filter(final String attractionName) {
        return extractRideData(searchQuery(PARK_SERVICES.NAME.containsIgnoreCase(attractionName)));
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
                .queryAction(db -> db.select()
                        .from(RIDES)
                        .join(RIDE_DETAILS)
                        .on(RIDE_DETAILS.RIDEID.eq(RIDES.RIDEID))
                        .join(PARK_SERVICES)
                        .on(PARK_SERVICES.PARKSERVICEID.eq(RIDES.RIDEID))
                        .join(FACILITIES)
                        .on(FACILITIES.FACILITYID.eq(RIDES.RIDEID))
                        .where(condition)
                        .fetch())
                .closeConnection()
                .getResultAsRecords();
    }

    @SuppressWarnings("unchecked")
    private <T extends TableItem> Collection<T> extractRideData(final Result<Record> result) {
        final List<T> data = new ArrayList<>();
        result.forEach(record -> data.add((T) new RideTableItem(record.get(RIDES.RIDEID),
                record.get(PARK_SERVICES.NAME),
                record.get(FACILITIES.OPENINGTIME),
                record.get(FACILITIES.CLOSINGTIME),
                record.get(PARK_SERVICES.TYPE),
                record.get(RIDES.INTENSITY),
                record.get(RIDES.DURATION).getMinute(),
                record.get(RIDES.MAXSEATS),
                record.get(PARK_SERVICES.DESCRIPTION),
                record.get(RIDES.MINHEIGHT).intValue(),
                record.get(RIDES.MAXHEIGHT).intValue(),
                record.get(RIDES.MINWEIGHT).doubleValue(),
                record.get(RIDES.MAXWEIGHT).doubleValue(),
                record.get(RIDE_DETAILS.STATUS),
                record.get(PARK_SERVICES.AVGRATING).doubleValue(),
                record.get(PARK_SERVICES.NUMREVIEWS).intValue())));
        return data;
    }

}
