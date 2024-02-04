package org.apdb4j.controllers.staff;

import lombok.NonNull;
import org.apdb4j.core.managers.RideManager;
import org.apdb4j.util.QueryBuilder;
import org.apdb4j.view.staff.tableview.RideTableItem;
import org.apdb4j.view.staff.tableview.TableItem;
import org.jooq.Condition;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DSL;
import org.jooq.types.UInteger;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.apdb4j.db.Tables.*;

/**
 * An implementation of a ride controller.
 */
public class RideControllerImpl implements RideController {

    private String errorMessage;

    /**
     * {@inheritDoc}
     * @throws org.jooq.exception.DataAccessException if query fails
     */
    @Override
    public <T extends TableItem> Collection<T> getData() {
        return extractRideData(searchQuery(DSL.condition(true)));
    }

    /**
     * {@inheritDoc}
     * @throws org.jooq.exception.DataAccessException if query fails
     */
    @Override
    public <T extends TableItem> T addData(final T item) {
        final RideTableItem ride = (RideTableItem) item;
        final Duration rideDuration = Duration.ofMinutes(ride.getDuration());
        final boolean successfulQuery = RideManager.addNewRide(ride.getId(),
                ride.getName(),
                ride.getOpeningTime(),
                ride.getClosingTime(),
                ride.getType(),
                ride.getIntensity(),
                LocalTime.of(rideDuration.toHoursPart(), rideDuration.toMinutesPart()),
                ride.getMaxSeats(),
                ride.getDescription(),
                ride.getMinHeight(),
                ride.getMaxHeight(),
                Math.toIntExact(Math.round(ride.getMinWeight())),
                Math.toIntExact(Math.round(ride.getMaxWeight())),
                ride.getStatus().charAt(0),
                "");
        if (!successfulQuery) {
            errorMessage = "Could not add ride.";
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
        final RideTableItem ride = (RideTableItem) item;
        final Duration rideDuration = Duration.ofMinutes(ride.getDuration());
        if ("O".equals(ride.getStatus())) {
            new QueryBuilder().createConnection()
                    .queryAction(db -> db.update(RIDE_DETAILS)
                            .set(RIDE_DETAILS.ESTIMATEDWAITTIME, LocalTime.MIN)
                            .set(RIDE_DETAILS.STATUS, ride.getStatus())
                            .where(RIDE_DETAILS.RIDEID.eq(ride.getId()))
                            .execute())
                    .closeConnection();
        } else {
            new QueryBuilder().createConnection()
                    .queryAction(db -> db.update(RIDE_DETAILS)
                            .set(RIDE_DETAILS.ESTIMATEDWAITTIME, (LocalTime) null)
                            .set(RIDE_DETAILS.STATUS, ride.getStatus())
                            .where(RIDE_DETAILS.RIDEID.eq(ride.getId()))
                            .execute())
                    .closeConnection();
        }
        new QueryBuilder().createConnection()
                .queryAction(db -> db.update(RIDES
                                .join(RIDE_DETAILS)
                                .on(RIDE_DETAILS.RIDEID.eq(RIDES.RIDEID))
                                .join(PARK_SERVICES)
                                .on(PARK_SERVICES.PARKSERVICEID.eq(RIDES.RIDEID))
                                .join(FACILITIES)
                                .on(FACILITIES.FACILITYID.eq(RIDES.RIDEID)))
                        .set(PARK_SERVICES.NAME, ride.getName())
                        .set(FACILITIES.OPENINGTIME, ride.getOpeningTime())
                        .set(FACILITIES.CLOSINGTIME, ride.getClosingTime())
                        .set(PARK_SERVICES.TYPE, ride.getType())
                        .set(RIDES.INTENSITY, ride.getIntensity())
                        .set(RIDES.DURATION, LocalTime.of(rideDuration.toHoursPart(), rideDuration.toMinutesPart()))
                        .set(RIDES.MAXSEATS, ride.getMaxSeats())
                        .set(PARK_SERVICES.DESCRIPTION, ride.getDescription())
                        .set(RIDES.MINHEIGHT, UInteger.valueOf(ride.getMinHeight()))
                        .set(RIDES.MAXHEIGHT, UInteger.valueOf(ride.getMaxHeight()))
                        .set(RIDES.MINWEIGHT, UInteger.valueOf(Math.round(ride.getMinWeight())))
                        .set(RIDES.MAXWEIGHT, UInteger.valueOf(Math.round(ride.getMaxWeight())))
                        .set(PARK_SERVICES.AVGRATING, BigDecimal.valueOf(ride.getAverageRating()))
                        .set(PARK_SERVICES.NUMREVIEWS, UInteger.valueOf(ride.getRatings()))
                        .where(RIDES.RIDEID.eq(ride.getId()))
                        .execute())
                .closeConnection();
        return item;
    }

    /**
     * {@inheritDoc}
     * @throws org.jooq.exception.DataAccessException if query fails
     */
    @Override
    public <T extends TableItem> Collection<T> filter(final String attractionName) {
        return extractRideData(searchQuery(PARK_SERVICES.NAME.containsIgnoreCase(attractionName)));
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
     * @throws org.jooq.exception.DataAccessException if query fails
     */
    @Override
    public List<String> getExistingIntensities() {
        return Arrays.stream(new QueryBuilder().createConnection()
                        .queryAction(db -> db.selectDistinct(RIDES.INTENSITY)
                                .from(RIDES)
                                .fetch())
                        .closeConnection()
                        .getResultAsRecords()
                        .intoArray(RIDES.INTENSITY))
                .toList();
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
                .queryAction(db -> db.select()
                        .from(RIDES.join(RIDE_DETAILS)
                                .on(RIDE_DETAILS.RIDEID.eq(RIDES.RIDEID))
                                .join(PARK_SERVICES)
                                .on(PARK_SERVICES.PARKSERVICEID.eq(RIDES.RIDEID))
                                .join(FACILITIES)
                                .on(FACILITIES.FACILITYID.eq(RIDES.RIDEID)))
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
