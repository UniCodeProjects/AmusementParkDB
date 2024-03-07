package org.apdb4j.controllers.guests;

import lombok.NonNull;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.jooq.Field;
import org.jooq.Record;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.apdb4j.db.Tables.EXHIBITION_DETAILS;
import static org.apdb4j.db.Tables.FACILITIES;
import static org.apdb4j.db.Tables.PARK_SERVICES;
import static org.apdb4j.db.Tables.RIDES;
import static org.apdb4j.db.Tables.RIDE_DETAILS;

/**
 * Utility class that formats the values read from the database
 * in a way that they can be easily understood by the user.
 */
public final class Formatter {

    private static final Map<Field<?>, String> ATTRIBUTES_WITH_NAMES = Map.ofEntries(
            Map.entry(PARK_SERVICES.NUMREVIEWS, "Number of reviews"),
            Map.entry(PARK_SERVICES.AVGRATING, "Average rating"),
            Map.entry(EXHIBITION_DETAILS.MAXSEATS, "Maximum number of seats"),
            Map.entry(FACILITIES.OPENINGTIME, "Opening time"),
            Map.entry(FACILITIES.CLOSINGTIME, "Closing time"),
            Map.entry(RIDES.MAXSEATS, "Maximum number of seats"),
            Map.entry(RIDES.MINHEIGHT, "Minimum height required"),
            Map.entry(RIDES.MAXHEIGHT, "Maximum height required"),
            Map.entry(RIDES.MINWEIGHT, "Minimum weight required"),
            Map.entry(RIDES.MAXWEIGHT, "Maximum weight required"),
            Map.entry(RIDE_DETAILS.ESTIMATEDWAITTIME, "Estimated wait time"));
    private static final Map<String, String> RIDE_DETAILS_STATUS = Map.of("O", "Open",
            "M", "In maintenance",
            "C", "Closed");
    private static final String HEIGHT_MEASUREMENT_UNIT = "cm";
    private static final String WEIGHT_MEASUREMENT_UNIT = "kg";

    private Formatter() {
    }

    /**
     * Formats the fields' names and values of all the {@link Record}s in the provided list.
     * @param records the records that have to be shown in the user interface.
     * @return a list of maps. Each map represents a record of the input parameter {@code records}. In each map the keys are the
     *         names of the fields of the record, and the values are the values of each field of the record. Both are
     *         well-formatted for the GUI.
     *         Note that the order of this list is the same of the input list.
     */
    public static List<Map<String, String>> format(final @NonNull List<Record> records) {
        return records.stream().map(record -> Arrays.stream(record.fields())
                .map(field -> new ImmutablePair<>(formatFieldName(field), formatFieldValue(field, record)))
                .collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue)))
                .toList();
    }

    private static String formatFieldName(final @NonNull Field<?> field) {
        return ATTRIBUTES_WITH_NAMES.containsKey(field) ? ATTRIBUTES_WITH_NAMES.get(field) : field.getName();
    }

    private static String formatFieldValue(final @NonNull Field<?> field, final @NonNull Record record) {
        String fieldValue;
        if (field.equals(RIDE_DETAILS.STATUS)) {
            fieldValue = RIDE_DETAILS_STATUS.get(record.get(field).toString());
        } else if (field.equals(RIDES.MINHEIGHT) || field.equals(RIDES.MAXHEIGHT)) {
            fieldValue = record.get(field) + " " + HEIGHT_MEASUREMENT_UNIT;
        } else if (field.equals(RIDES.MINWEIGHT) || field.equals(RIDES.MAXWEIGHT)) {
            fieldValue = record.get(field) + " " + WEIGHT_MEASUREMENT_UNIT;
        } else if (field.equals(RIDE_DETAILS.ESTIMATEDWAITTIME)) {
            fieldValue = record.get(field) != null ? formatTime((LocalTime) record.get(field)) : "Ride closed";
        } else if (record.get(field) instanceof LocalTime) {
            fieldValue = formatTime((LocalTime) record.get(field));
        } else {
            fieldValue = record.get(field).toString();
        }
        return fieldValue;
    }

    private static String formatTime(final @NonNull LocalTime time) {
        return time.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }
}
