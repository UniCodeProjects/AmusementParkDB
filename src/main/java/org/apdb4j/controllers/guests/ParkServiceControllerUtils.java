package org.apdb4j.controllers.guests;

import lombok.NonNull;
import org.jooq.Field;

import java.time.LocalTime;
import java.util.Map;

import static org.apdb4j.db.Tables.*;

class ParkServiceControllerUtils {

    static String HEIGHT_MEASUREMENT_UNIT = "cm";
    static String WEIGHT_MEASUREMENT_UNIT = "kg";

    static Map<Field<?>, String> getFieldNames() {
        return Map.of(PARK_SERVICES.AVGRATING, "Average rating",
                PARK_SERVICES.NUMREVIEWS, "Number of reviews",
                FACILITIES.OPENINGTIME, "Opening time",
                FACILITIES.CLOSINGTIME, "Closing time",
                EXHIBITION_DETAILS.MAXSEATS, "Maximum number of seats",
                RIDES.MAXSEATS, "Maximum number of seats",
                RIDES.MINHEIGHT, "Minimum height required",
                RIDES.MAXHEIGHT, "Maximum height",
                RIDES.MINWEIGHT, "Minimum weight required",
                RIDES.MAXWEIGHT, "Maximum weight");
    }

    static String getRideStatusCompleteName(final @NonNull String status) {
        return switch (status) {
          case "O" -> "Open";
          case "M" -> "In maintenance";
          case "C" -> "Closed";
          default -> "";
        };
    }

    static String formatDuration(final @NonNull LocalTime duration) {
        return duration.getMinute() + " minutes " + (duration.getSecond() > 0 ? duration.getSecond() + " seconds" : "");
    }
}
