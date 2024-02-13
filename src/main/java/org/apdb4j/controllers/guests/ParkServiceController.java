package org.apdb4j.controllers.guests;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.NonNull;
import org.apdb4j.util.QueryBuilder;

import java.util.*;

import static org.apdb4j.db.Tables.*;

public interface ParkServiceController {
    default Collection<String> getSortFields() {
        return List.of("Average rating", "Name");
    }

    Map<String, List<Map<String, String>>> getSortFieldsWithAscendingResults();

    Map<String, List<Map<String, String>>> getSortFieldsWithDescendingResults();

    Collection<Map<String, String>> getMainInfo();

    Map<String, String> getAllInfo(@NonNull String parkServiceName);

    default String getParkServiceDescription(final @NonNull String parkServiceName) {
        return new QueryBuilder().createConnection()
                .queryAction(db -> db.select(PARK_SERVICES.DESCRIPTION)
                        .from(PARK_SERVICES)
                        .where(PARK_SERVICES.NAME.eq(parkServiceName))
                        .fetch())
                .closeConnection()
                .getResultAsRecords().getValue(0, PARK_SERVICES.DESCRIPTION);
    }

    Map<String, Collection<String>> getAllFiltersWithValues();
//        final Collection<String> types = new QueryBuilder().createConnection()
//                .queryAction(db -> db.selectDistinct(PARK_SERVICES.TYPE)
//                        .from(PARK_SERVICES)
//                        .fetch())
//                .closeConnection()
//                .getResultAsRecords()
//                .getValues(PARK_SERVICES.TYPE);
//        return new HashMap<>(Map.of("Type", types));

    Collection<Map<String, String>> filterBy(@NonNull String attribute, @NonNull String value);
    Collection<Map<String, String>> filterByAverageRating(int maxRating, boolean ranged);
//    List<Map<String, String>> sortByAscending(@NonNull String sortField);
//    List<Map<String, String>> sortByDescending(@NonNull String sortField);

    @SuppressFBWarnings("NP_NULL_ON_SOME_PATH")
    default String getParkServiceID(final @NonNull String parkServiceName) {
        return Objects.requireNonNull(new QueryBuilder().createConnection()
                .queryAction(db -> db.select(PARK_SERVICES.PARKSERVICEID)
                        .from(PARK_SERVICES)
                        .where(PARK_SERVICES.NAME.eq(parkServiceName))
                        .fetch())
                .closeConnection()
                .getResultAsRecords()
                .getValue(0, PARK_SERVICES.PARKSERVICEID));
    }
}
