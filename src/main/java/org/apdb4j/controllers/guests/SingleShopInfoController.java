package org.apdb4j.controllers.guests;

import lombok.NonNull;
import org.apdb4j.util.QueryBuilder;

import java.util.Map;
import java.util.Optional;

import static org.apdb4j.db.Tables.FACILITIES;
import static org.apdb4j.db.Tables.PARK_SERVICES;

/**
 * Implementation of a single info controller for shops.
 */
public class SingleShopInfoController extends AbstractSingleParkServiceInfoController {

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
        return formatQueryResult(new QueryBuilder().createConnection()
                .queryAction(db -> db.select(PARK_SERVICES.TYPE,
                        FACILITIES.OPENINGTIME,
                        FACILITIES.CLOSINGTIME)
                        .from(PARK_SERVICES).join(FACILITIES).on(PARK_SERVICES.PARKSERVICEID.eq(FACILITIES.FACILITYID))
                        .where(PARK_SERVICES.NAME.eq(parkServiceName))
                        .fetch())
                .closeConnection().getResultAsRecords().get(0));
    }
}
