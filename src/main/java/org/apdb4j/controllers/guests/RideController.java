package org.apdb4j.controllers.guests;

import lombok.NonNull;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apdb4j.util.QueryBuilder;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Result;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.apdb4j.db.Tables.*;

public class RideController implements ParkServiceController {

    private final Map<String, Collection<Map<String, String>>> typeFilters = new HashMap<>();
    private final Map<String, Collection<Map<String, String>>> intensityFilters = new HashMap<>();
    private final Map<Integer, Collection<Map<String, String>>> averageRatingNonRangedFilters = new HashMap<>();
    private final Map<Integer, Collection<Map<String, String>>> averageRatingRangedFilters = new HashMap<>();
    private final Map<String, Map<String, Collection<Map<String, String>>>> filters = new HashMap<>();

    public RideController() {
        final List<String> types = new QueryBuilder()
                .createConnection()
                .queryAction(db -> db.selectDistinct(PARK_SERVICES.TYPE)
                        .from(PARK_SERVICES).join(RIDES).on(PARK_SERVICES.PARKSERVICEID.eq(RIDES.RIDEID))
                        .fetch())
                .closeConnection()
                .getResultAsRecords()
                .getValues(PARK_SERVICES.TYPE);
        types.forEach(type -> typeFilters.put(type, getParkServiceInfoFromQueryResult(new QueryBuilder()
                .createConnection()
                .queryAction(db -> db.select(PARK_SERVICES.NAME, RIDES.INTENSITY, PARK_SERVICES.AVGRATING)
                        .from(PARK_SERVICES).join(RIDES).on(PARK_SERVICES.PARKSERVICEID.eq(RIDES.RIDEID))
                        .where(PARK_SERVICES.TYPE.eq(type))
                        .fetch())
                .closeConnection()
                .getResultAsRecords())));
        filters.put("Type", typeFilters);
        final List<String> intensities = new QueryBuilder()
                .createConnection()
                .queryAction(db -> db.selectDistinct(RIDES.INTENSITY)
                        .from(RIDES)
                        .fetch())
                .closeConnection()
                .getResultAsRecords()
                .getValues(RIDES.INTENSITY);
        intensities.forEach(intensity -> intensityFilters.put(intensity, getParkServiceInfoFromQueryResult(new QueryBuilder()
                .createConnection()
                .queryAction(db -> db.select(PARK_SERVICES.NAME, RIDES.INTENSITY, PARK_SERVICES.AVGRATING)
                        .from(PARK_SERVICES).join(RIDES).on(PARK_SERVICES.PARKSERVICEID.eq(RIDES.RIDEID))
                        .where(RIDES.INTENSITY.eq(intensity))
                        .fetch())
                .closeConnection()
                .getResultAsRecords())));
        filters.put("Intensity", intensityFilters);
        IntStream.range(1, 6).forEach(rating -> {
            averageRatingNonRangedFilters.put(rating, getParkServiceInfoFromQueryResult(new QueryBuilder()
                    .createConnection()
                    .queryAction(db -> db.select(PARK_SERVICES.NAME, RIDES.INTENSITY, PARK_SERVICES.AVGRATING)
                            .from(PARK_SERVICES).join(RIDES).on(PARK_SERVICES.PARKSERVICEID.eq(RIDES.RIDEID))
                            .where(PARK_SERVICES.AVGRATING.eq(BigDecimal.valueOf(rating)))
                            .fetch())
                    .closeConnection()
                    .getResultAsRecords()));
            averageRatingRangedFilters.put(rating, getParkServiceInfoFromQueryResult(new QueryBuilder()
                    .createConnection()
                    .queryAction(db -> db.select(PARK_SERVICES.NAME, RIDES.INTENSITY, PARK_SERVICES.AVGRATING)
                            .from(PARK_SERVICES).join(RIDES).on(PARK_SERVICES.PARKSERVICEID.eq(RIDES.RIDEID))
                            .where(PARK_SERVICES.AVGRATING.lessOrEqual(BigDecimal.valueOf(rating)))
                            .fetch())
                    .closeConnection()
                    .getResultAsRecords()));
        });
    }

    @Override
    public Map<String, List<Map<String, String>>> getSortFieldsWithAscendingResults() {
        return Map.of("Average rating", getParkServiceInfoFromQueryResult(new QueryBuilder().createConnection()
                .queryAction(db -> db.select(PARK_SERVICES.NAME, RIDES.INTENSITY, PARK_SERVICES.AVGRATING)
                        .from(PARK_SERVICES).join(RIDES).on(PARK_SERVICES.PARKSERVICEID.eq(RIDES.RIDEID))
                        .orderBy(PARK_SERVICES.AVGRATING.asc()))
                .closeConnection()
                .getResultAsRecords()),
                "Name", getParkServiceInfoFromQueryResult(new QueryBuilder()
                        .createConnection()
                        .queryAction(db -> db.select(PARK_SERVICES.NAME, RIDES.INTENSITY, PARK_SERVICES.AVGRATING)
                                .from(PARK_SERVICES).join(RIDES).on(PARK_SERVICES.PARKSERVICEID.eq(RIDES.RIDEID))
                                .orderBy(PARK_SERVICES.NAME.asc()))
                        .closeConnection().getResultAsRecords()));
    }

    public Map<String, List<Map<String, String>>> getSortFieldsWithDescendingResults() {
        return Map.of("Average rating", getParkServiceInfoFromQueryResult(new QueryBuilder().createConnection()
                        .queryAction(db -> db.select(PARK_SERVICES.NAME, RIDES.INTENSITY, PARK_SERVICES.AVGRATING)
                                .from(PARK_SERVICES).join(RIDES).on(PARK_SERVICES.PARKSERVICEID.eq(RIDES.RIDEID))
                                .orderBy(PARK_SERVICES.AVGRATING.desc()))
                        .closeConnection()
                        .getResultAsRecords()),
                "Name", getParkServiceInfoFromQueryResult(new QueryBuilder()
                        .createConnection()
                        .queryAction(db -> db.select(PARK_SERVICES.NAME, RIDES.INTENSITY, PARK_SERVICES.AVGRATING)
                                .from(PARK_SERVICES).join(RIDES).on(PARK_SERVICES.PARKSERVICEID.eq(RIDES.RIDEID))
                                .orderBy(PARK_SERVICES.NAME.desc()))
                        .closeConnection().getResultAsRecords()));
    }

    @Override
    public Collection<Map<String, String>> getMainInfo() {
        final Result<Record> rides = new QueryBuilder().createConnection()
                .queryAction(db -> db.select(PARK_SERVICES.NAME, RIDES.INTENSITY, PARK_SERVICES.AVGRATING)
                        .from(PARK_SERVICES).join(RIDES).on(PARK_SERVICES.PARKSERVICEID.eq(RIDES.RIDEID))
                        .fetch())
                .closeConnection()
                .getResultAsRecords();
        return getParkServiceInfoFromQueryResult(rides);
    }

    @Override
    public Map<String, String> getAllInfo(final @NonNull String parkServiceName) {
        final Record ride = new QueryBuilder().createConnection()
                .queryAction(db -> db.select(RIDES.INTENSITY,
                        RIDES.DURATION,
                        RIDES.MAXSEATS,
                        RIDES.MINHEIGHT,
                        RIDES.MAXHEIGHT,
                        RIDES.MINWEIGHT,
                        RIDES.MAXWEIGHT,
                        FACILITIES.OPENINGTIME,
                        FACILITIES.CLOSINGTIME,
                        PARK_SERVICES.TYPE,
                        RIDE_DETAILS.STATUS)
                        .from(PARK_SERVICES)
                        .join(FACILITIES).on(PARK_SERVICES.PARKSERVICEID.eq(FACILITIES.FACILITYID))
                        .join(RIDES).on(FACILITIES.FACILITYID.eq(RIDES.RIDEID))
                        .join(RIDE_DETAILS).on(RIDES.RIDEID.eq(RIDE_DETAILS.RIDEID))
                        .where(PARK_SERVICES.NAME.eq(parkServiceName))
                        .fetch())
                .closeConnection()
                .getResultAsRecords()
                .get(0);
        return Arrays.stream(ride.fields())
                .map(field -> new ImmutablePair<>(ParkServiceControllerUtils.getFieldNames().containsKey(field) ?
                        ParkServiceControllerUtils.getFieldNames().get(field) : field.getName(),
                        formatFieldValue(field, ride)))
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
    }

    @Override
    public Map<String, Collection<String>> getAllFiltersWithValues() {
        final Map<String, Collection<String>> filters = new HashMap<>();
        filters.put("Type", new QueryBuilder()
                .createConnection()
                .queryAction(db -> db.selectDistinct(PARK_SERVICES.TYPE)
                        .from(RIDES).join(PARK_SERVICES).on(PARK_SERVICES.PARKSERVICEID.eq(RIDES.RIDEID))
                        .fetch())
                .closeConnection()
                .getResultAsRecords()
                .getValues(PARK_SERVICES.TYPE));
        filters.put("Intensity", new QueryBuilder()
                .createConnection()
                .queryAction(db -> db.selectDistinct(RIDES.INTENSITY)
                        .from(RIDES)
                        .fetch())
                .closeConnection()
                .getResultAsRecords()
                .getValues(RIDES.INTENSITY));
        return filters;
    }

    @Override
    public Collection<Map<String, String>> filterBy(final @NonNull String attribute, final @NonNull String value) {
        return filters.containsKey(attribute) && filters.get(attribute).containsKey(value) ?
                filters.get(attribute).get(value) : List.of();
    }

    @Override
    public Collection<Map<String, String>> filterByAverageRating(int maxRating, boolean ranged) {
        return ranged ? averageRatingRangedFilters.get(maxRating) : averageRatingNonRangedFilters.get(maxRating);
    }

//    @Override
//    public List<Map<String, String>> sortByAscending(@NonNull String sortField) {
//        return new QueryBuilder().createConnection()
//                .queryAction(db -> db.select(PARK_SERVICES.NAME, RIDES.INTENSITY, PARK_SERVICES.AVGRATING)
//                        .from(PARK_SERVICES).join(RIDES).on(PARK_SERVICES.PARKSERVICEID.eq(RIDES.RIDEID))
//                        .fetch())
//                .closeConnection()
//                .getResultAsRecords();
//    }

//    @Override
//    public List<Map<String, String>> sortByDescending(@NonNull String sortField) {
//        return null;
//    }

    private List<Map<String, String>> getParkServiceInfoFromQueryResult(final Result<Record> queryResult) {
        final List<Map<String, String>> returnValue = new ArrayList<>();
        queryResult.stream().forEachOrdered(parkService -> {
            final Map<String, String> parkServiceInfo = new HashMap<>();
            Arrays.stream(parkService.fields())
                    .forEach(field -> parkServiceInfo.put(ParkServiceControllerUtils.getFieldNames().containsKey(field) ?
                                    ParkServiceControllerUtils.getFieldNames().get(field) : field.getName(),
                            Objects.requireNonNull(parkService.get(field)).toString()));
            returnValue.add(parkServiceInfo);
        });
        return returnValue;
    }

    private String formatFieldValue(final @NonNull Field<?> field, final @NonNull Record ride) {
        if (field.equals(RIDE_DETAILS.STATUS)) {
            return ParkServiceControllerUtils.getRideStatusCompleteName((String) ride.get(field));
        } else if (field.equals(RIDES.DURATION)) {
            return ParkServiceControllerUtils.formatDuration((LocalTime) ride.get(field));
        } else if (field.equals(RIDES.MINHEIGHT) || field.equals(RIDES.MAXHEIGHT)) {
            return ride.get(field) + " " + ParkServiceControllerUtils.HEIGHT_MEASUREMENT_UNIT;
        } else if (field.equals(RIDES.MINWEIGHT) || field.equals(RIDES.MAXWEIGHT)) {
            return ride.get(field) + " " + ParkServiceControllerUtils.WEIGHT_MEASUREMENT_UNIT;
        } else {
            return ride.get(field).toString();
        }
    }
}
