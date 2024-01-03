package org.apdb4j.controllers;

import lombok.NonNull;
import org.apdb4j.util.QueryBuilder;
import org.jooq.impl.DSL;

import java.time.LocalTime;
import java.util.Optional;

import static org.apdb4j.db.Tables.CONTRACTS;
import static org.apdb4j.db.Tables.FACILITIES;
import static org.apdb4j.db.Tables.PARK_SERVICES;
import static org.apdb4j.db.Tables.STAFF;

/**
 * A simple implementation of a read-only controller for the overview tab.
 */
public class OverviewControllerImpl implements OverviewController {

    private static final QueryBuilder QUERY_BUILDER = new QueryBuilder();

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
    public String getParkName() {
        return (String) QUERY_BUILDER.createConnection()
                .queryAction(db -> db.select(DSL.field("database()").as("DbName"))
                        .fetch())
                .closeConnection()
                .getResultAsRecords()
                .getValue(0, "DbName");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalTime getOpeningTime() {
        return (LocalTime) QUERY_BUILDER.createConnection()
                .queryAction(db -> db.select(DSL.min(FACILITIES.OPENINGTIME).as("OpeningTime"))
                        .from(FACILITIES)
                        .fetch())
                .closeConnection()
                .getResultAsRecords()
                .getValue(0, "OpeningTime");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalTime getClosingTime() {
        return (LocalTime) QUERY_BUILDER.createConnection()
                .queryAction(db -> db.select(DSL.max(FACILITIES.CLOSINGTIME).as("ClosingTime"))
                        .from(FACILITIES)
                        .fetch())
                .closeConnection()
                .getResultAsRecords()
                .getValue(0, "ClosingTime");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getAdministrator() {
        return (String) QUERY_BUILDER.createConnection()
                .queryAction(db -> db.select(DSL.concat(STAFF.NAME, DSL.inline(" "), STAFF.SURNAME).as("Name"))
                        .from(STAFF)
                        .where(STAFF.ISADMIN.isTrue())
                        .fetch())
                .closeConnection()
                .getResultAsRecords()
                .getValue(0, "Name");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getAttractionsAmount() {
        return QUERY_BUILDER.createConnection()
                .queryAction(db -> db.selectCount()
                        .from(PARK_SERVICES)
                        .join(FACILITIES)
                        .on(FACILITIES.FACILITYID.eq(PARK_SERVICES.PARKSERVICEID))
                        .where(FACILITIES.ISSHOP.isFalse())
                        .fetchOne(0, int.class))
                .closeConnection()
                .getResultAsInt();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getShopsAmount() {
        return QUERY_BUILDER.createConnection()
                .queryAction(db -> db.selectCount()
                        .from(PARK_SERVICES)
                        .join(FACILITIES)
                        .on(FACILITIES.FACILITYID.eq(PARK_SERVICES.PARKSERVICEID))
                        .where(FACILITIES.ISSHOP.isTrue())
                        .fetchOne(0, int.class))
                .closeConnection()
                .getResultAsInt();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getEmployeesAmount() {
        return QUERY_BUILDER.createConnection()
                .queryAction(db -> db.selectCount()
                        .from(STAFF)
                        .join(CONTRACTS)
                        .on(CONTRACTS.EMPLOYEENID.eq(STAFF.NATIONALID))
                        .where(STAFF.ISEMPLOYEE.isTrue())
                        .and(CONTRACTS.ENDDATE.isNull())
                        .fetchOne(0, int.class))
                .closeConnection()
                .getResultAsInt();
    }

}
