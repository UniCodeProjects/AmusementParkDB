package org.apdb4j.controllers.guests;

import lombok.NonNull;
import org.apdb4j.util.QueryBuilder;
import org.jooq.Record;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.apdb4j.db.Tables.EXHIBITION_DETAILS;
import static org.apdb4j.db.Tables.PARK_SERVICES;
/**
 * Implementation of a single info controller for exhibitions.
 */
public class SingleExhibitionInfoController extends AbstractSingleParkServiceInfoController {

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull Optional<String> getErrorMessage() {
        return Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> getAllParkServiceInfo(final @NonNull String parkServiceName) {
        final List<Record> exhibitionDetail = new QueryBuilder().createConnection()
                .queryAction(db -> db.select(PARK_SERVICES.TYPE,
                        EXHIBITION_DETAILS.DATE,
                        EXHIBITION_DETAILS.TIME,
                        EXHIBITION_DETAILS.MAXSEATS)
                        .from(PARK_SERVICES).join(EXHIBITION_DETAILS)
                        .on(PARK_SERVICES.PARKSERVICEID.eq(EXHIBITION_DETAILS.EXHIBITIONID))
                        .where(PARK_SERVICES.NAME.eq(parkServiceName))
                        .and(EXHIBITION_DETAILS.DATE.ge(LocalDate.now()))
                        .orderBy(EXHIBITION_DETAILS.DATE.asc())
                        .limit(1)
                        .fetch())
                .closeConnection().getResultAsRecords();
        if (exhibitionDetail.isEmpty()) {
            return formatQueryResult(new QueryBuilder().createConnection()
                    .queryAction(db -> db.select(PARK_SERVICES.TYPE)
                            .from(PARK_SERVICES)
                            .where(PARK_SERVICES.NAME.eq(parkServiceName))
                            .fetch())
                    .closeConnection().getResultAsRecords().get(0));
        }
        return formatQueryResult(exhibitionDetail.get(0));
    }
}
