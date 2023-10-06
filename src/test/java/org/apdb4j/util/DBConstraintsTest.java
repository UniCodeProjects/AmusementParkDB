package org.apdb4j.util;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.exception.DataAccessException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static org.apdb4j.db.Tables.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests the database constraints.
 */
class DBConstraintsTest {

    private static final QueryBuilder QUERY_BUILDER = new QueryBuilder();
    private static final int CHECK_CONSTRAINT_VIOLATED_ERROR_CODE = 3819;
    private static final Collection<Pair<Table<Record>, Object[]>> TUPLES_TO_REMOVE = new ArrayList<>();
    private static final String EXHIBITIONID_SAMPLE = "EX-001";
    private static final String RIDEID_SAMPLE = "RI-001";

    @Test
    void accountsConstraintsTest() {
        final var correctUsernameSample = "bar.bar";
        final var correctPasswordSample = "12345678";
        insertTupleAndCheckForErrorCode(ACCOUNTS, false,
                "bar@gmail.com", correctUsernameSample, "bar", null);
        insertTupleAndCheckForErrorCode(ACCOUNTS, false,
                "bargmail.com", correctUsernameSample, correctPasswordSample, null);
        // insertTupleAndCheckForErrorCode(ACCOUNTS, false,
                // "bar@gmailcom", correctUsernameSample, correctPasswordSample, null); // problem
        insertTupleAndCheckForErrorCode(ACCOUNTS, false,
                "bAR@gmail.com", correctUsernameSample, correctPasswordSample, null);
        insertTupleAndCheckForErrorCode(ACCOUNTS, false,
                "bar@gmAIL.com", correctUsernameSample, correctPasswordSample, null);
        insertTupleAndCheckForErrorCode(ACCOUNTS, false,
                "bar@gmail.cOM", correctUsernameSample, correctPasswordSample, null);
        insertTupleAndCheckForErrorCode(ACCOUNTS, false,
                "b-ar@gmail.com", correctUsernameSample, correctPasswordSample, null);
        insertTupleAndCheckForErrorCode(ACCOUNTS, false,
                "bar@g-mail.com", correctUsernameSample, correctPasswordSample, null);
        // insertTupleAndCheckForErrorCode(ACCOUNTS, false,
                // "bar@gmail.c-om", correctUsernameSample, correctPasswordSample, null); // problem
        insertTupleAndCheckForErrorCode(ACCOUNTS, false,
                "bar@gmail.c", correctUsernameSample, correctPasswordSample, null);
        // insertTupleAndCheckForErrorCode(ACCOUNTS, false,
                // "bar@gmail", correctUsernameSample, correctPasswordSample, null); // problem
        insertTupleAndCheckForErrorCode(ACCOUNTS, false,
                "bar@gmail.", correctUsernameSample, correctPasswordSample, null);
        insertTupleAndCheckForErrorCode(ACCOUNTS, false,
                "bar", correctUsernameSample, correctPasswordSample, null);
        insertTupleAndCheckForErrorCode(ACCOUNTS, false,
                "bar@", correctUsernameSample, correctPasswordSample, null);
        // insertTupleAndCheckForErrorCode(ACCOUNTS, false,
                // "bar@.com", correctUsernameSample, correctPasswordSample, null); // problem
        insertTupleAndCheckForErrorCode(ACCOUNTS, true,
                "bar_.02%@gmail.com", correctUsernameSample, correctPasswordSample, null);
        TUPLES_TO_REMOVE.add(new ImmutablePair<>(ACCOUNTS, new Object[]{"bar_.02%@gmail.com"}));
    }

    @Test
    void contractConstraintsTest() {
        final var employerNIDSample = "MRARSS77E15A944I";
        final var employeeNIDSample = "RSSLRD89L17C573J";
        final var correctContractIDSample = "C-001";
        // test for constraint CONTRACTID_FORMAT
        insertTupleAndCheckForErrorCode(CONTRACTS, false, "SFA-001",
                LocalDate.of(2021, 12, 10),
                LocalDate.of(2022, 1, 1),
                null,
                1100.00,
                employerNIDSample,
                employeeNIDSample);
        // test for constraint DATES_CONSISTENCY_1
        insertTupleAndCheckForErrorCode(CONTRACTS, false, correctContractIDSample,
                LocalDate.of(2023, 6, 6),
                LocalDate.of(2023, 6, 1),
                null,
                1100.00,
                employerNIDSample,
                employeeNIDSample);
        // test for constraint DATES_CONSISTENCY_2
        insertTupleAndCheckForErrorCode(CONTRACTS, false, correctContractIDSample,
                LocalDate.of(2021, 11, 10),
                LocalDate.of(2022, 1, 1),
                LocalDate.of(2021, 12, 12),
                1100.00,
                employerNIDSample,
                employeeNIDSample);
        // tests for constraint SALARY_NON_NEGATIVITY_CHECK
        insertTupleAndCheckForErrorCode(CONTRACTS, false, correctContractIDSample,
                LocalDate.of(2021, 10, 10),
                LocalDate.of(2022, 1, 1),
                null,
                0,
                employerNIDSample,
                employeeNIDSample);
        insertTupleAndCheckForErrorCode(CONTRACTS, false, correctContractIDSample,
                LocalDate.of(2021, 9, 10),
                LocalDate.of(2022, 1, 1),
                null,
                -1.0,
                employerNIDSample,
                employeeNIDSample);
        // valid tuples
        insertTupleAndCheckForErrorCode(CONTRACTS, true, "C-010",
                LocalDate.of(2021, 12, 15),
                LocalDate.of(2022, 1, 1),
                null,
                1500.00,
                employerNIDSample,
                employeeNIDSample);
        TUPLES_TO_REMOVE.add(new ImmutablePair<>(CONTRACTS, new Object[]{"C-010"}));
        insertTupleAndCheckForErrorCode(CONTRACTS, true, "C-011",
                LocalDate.of(2021, 12, 29),
                LocalDate.of(2021, 12, 29),
                LocalDate.of(2022, 12, 10),
                1500.00,
                employerNIDSample,
                employeeNIDSample);
        TUPLES_TO_REMOVE.add(new ImmutablePair<>(CONTRACTS, new Object[]{"C-011"}));
    }

    @Test
    void costConstraintsTest() {
        // test for constraint COST_ID_CHECK
        insertTupleAndCheckForErrorCode(COSTS, false, "S", 1000.00, 2000.00, 10, 2022);
        // tests for constraint MONEY_DATA_NON_NEGATIVITY_CHECK
        insertTupleAndCheckForErrorCode(COSTS, false, "SH-010", -10.0, 2312.00, 10, 2022);
        insertTupleAndCheckForErrorCode(COSTS, false, "SH-011", 2312.00, -10.0, 10, 2022);
        insertTupleAndCheckForErrorCode(COSTS, false, "SH-012", -123.00, -2414.00, 10, 2022);
        // tests for constraint MONTH_DOMAIN
        insertTupleAndCheckForErrorCode(COSTS, false, "SH-013", 2024.00, 2342.00, 15, 2022);
        // // tests for constraint YEAR_DOMAIN
        insertTupleAndCheckForErrorCode(COSTS, false, "SH-014", 2414.00, 24_234.00, 10, 1900);
        // valid tuples
        insertTupleAndCheckForErrorCode(COSTS, true, "SH-001", 0, 0, 10, 2002);
        TUPLES_TO_REMOVE.add(new ImmutablePair<>(COSTS, new Object[]{"SH-001", 10, 2002}));
        insertTupleAndCheckForErrorCode(COSTS, true, "SH-002", 21_412.00, 2121.00, 1, 2001);
        TUPLES_TO_REMOVE.add(new ImmutablePair<>(COSTS, new Object[]{"SH-002", 1, 2001}));
    }

    @Test
    void exhibitionDetailConstraintsTest() {
        final var exhibitionIDCorrectSample = "EX-010";
        // test for constraint EX_DET_ID_CHECK
        insertTupleAndCheckForErrorCode(EXHIBITION_DETAILS, false, "SADFS",
                LocalDate.of(2010, 10, 10),
                LocalTime.of(10, 10, 45),
                300,
                null);
        // test for constraint SPECTATORS_CONSISTENCY
        insertTupleAndCheckForErrorCode(EXHIBITION_DETAILS, false, exhibitionIDCorrectSample,
                LocalDate.of(2011, 11, 11),
                LocalTime.of(10, 10, 45),
                250,
                300);
        // test for constraint EX_DET_MAX_SEATS_DOMAIN
        insertTupleAndCheckForErrorCode(EXHIBITION_DETAILS, false, exhibitionIDCorrectSample,
                LocalDate.of(2001, 10, 10),
                LocalTime.of(10, 10, 10),
                0,
                null);
        insertTupleAndCheckForErrorCode(EXHIBITION_DETAILS, false, exhibitionIDCorrectSample,
                LocalDate.of(2002, 10, 10),
                LocalTime.of(10, 10, 10),
                -1,
                null);
        // valid tuples
        insertTupleAndCheckForErrorCode(EXHIBITION_DETAILS, true, EXHIBITIONID_SAMPLE,
                LocalDate.of(2012, 12, 10),
                LocalTime.of(10, 10, 12),
                250,
                250);
        TUPLES_TO_REMOVE.add(new ImmutablePair<>(EXHIBITION_DETAILS, new Object[]{EXHIBITIONID_SAMPLE,
                LocalDate.of(2012, 12, 10),
                LocalTime.of(10, 10, 12)}));
        insertTupleAndCheckForErrorCode(EXHIBITION_DETAILS, true, "EX-002",
                LocalDate.of(2009, 9, 10),
                LocalTime.of(10, 10, 9),
                250,
                null);
        TUPLES_TO_REMOVE.add(new ImmutablePair<>(EXHIBITION_DETAILS, new Object[]{"EX-002",
                LocalDate.of(2009, 9, 10),
                LocalTime.of(10, 10, 9)}));
    }

    @Test
    void facilityConstraintsTest() {
        // tests for constraint FACILITYID_CHECK
        insertTupleAndCheckForErrorCode(FACILITIES, false, EXHIBITIONID_SAMPLE,
                LocalTime.of(10, 10, 10),
                LocalTime.of(19, 19, 19),
                false);
        insertTupleAndCheckForErrorCode(FACILITIES, false, "ASFA",
                LocalTime.of(10, 10, 10),
                LocalTime.of(19, 19, 19),
                false);
        // tests for constraint SHOP_CHECK
        // isShop = false but the id is that of a shop
        insertTupleAndCheckForErrorCode(FACILITIES, false, "SH-001",
                LocalTime.of(10, 10, 10),
                LocalTime.of(19, 19, 19),
                false);
        // isShop = true but the id is not that of a shop
        insertTupleAndCheckForErrorCode(FACILITIES, false, RIDEID_SAMPLE,
                LocalTime.of(10, 10, 10),
                LocalTime.of(19, 19, 19),
                true);
        // tests for constraint TIMES_CONSISTENCY
        insertTupleAndCheckForErrorCode(FACILITIES, false, RIDEID_SAMPLE,
                LocalTime.of(10, 10, 10),
                LocalTime.of(10, 10, 10),
                false);
        insertTupleAndCheckForErrorCode(FACILITIES, false, RIDEID_SAMPLE,
                LocalTime.of(10, 10, 10),
                LocalTime.of(9, 9, 9),
                false);
    }

    @Test
    void maintenanceConstraintsTest() {
        insertTupleAndCheckForErrorCode(MAINTENANCES, false, RIDEID_SAMPLE,
                -10.00,
                "maintenance 1",
                LocalDate.of(2001, 10, 10));
        // valid tuples
        insertTupleAndCheckForErrorCode(MAINTENANCES, true, RIDEID_SAMPLE,
                0,
                "maintenance 2",
                LocalDate.of(2003, 10, 10));
        TUPLES_TO_REMOVE.add(new ImmutablePair<>(MAINTENANCES, new Object[]{RIDEID_SAMPLE, LocalDate.of(2003, 10, 10)}));
    }

    @Test
    void monthlyRecapConstraintsTest() {
        // test for constraint DAY_FORMAT
        insertTupleAndCheckForErrorCode(MONTHLY_RECAPS, false, LocalDate.of(2004, 10, 10), 15_000.00);
        // test for constraint REVENUE_NON_NEGATIVITY_CHECK
        insertTupleAndCheckForErrorCode(MONTHLY_RECAPS, false, LocalDate.of(2002, 10, 10),
                -10.0);
        // valid tuples
        insertTupleAndCheckForErrorCode(MONTHLY_RECAPS, true, LocalDate.of(2001, 10, 1), 0);
        TUPLES_TO_REMOVE.add(new ImmutablePair<>(MONTHLY_RECAPS, new Object[]{LocalDate.of(2001, 10, 1)}));
        insertTupleAndCheckForErrorCode(MONTHLY_RECAPS, true, LocalDate.of(2004, 10, 1), 221_221.00);
        TUPLES_TO_REMOVE.add(new ImmutablePair<>(MONTHLY_RECAPS, new Object[]{LocalDate.of(2004, 10, 1)}));
    }

    @Test
    void parkServiceConstraintsTest() {
        // test for constraint PARKSERVICEID_CHECK
        insertTupleAndCheckForErrorCode(PARK_SERVICES, false,
                "PROVA",
                "name 10",
                4.5,
                123,
                "type 10",
                null,
                false);
        // tests for constraint EXHIBITION_CHECK
        insertTupleAndCheckForErrorCode(PARK_SERVICES, false,
                "EX-050",
                "name 11",
                4.7,
                4534,
                "type 11",
                null,
                false);
        insertTupleAndCheckForErrorCode(PARK_SERVICES, false,
                "RI-024",
                "name 24",
                4.1,
                243,
                "type 24",
                null,
                true);
        // tests for AVGRATING_DOMAIN
        insertTupleAndCheckForErrorCode(PARK_SERVICES, false,
                "EX-021",
                "name 21",
                0,
                3423,
                "type 21",
                null,
                true);
        insertTupleAndCheckForErrorCode(PARK_SERVICES, false,
                "EX-042",
                "name 42",
                -1,
                342,
                "type 42",
                null,
                true);
        // valid tuples
        insertTupleAndCheckForErrorCode(PARK_SERVICES, true,
                "EX-050",
                "name 50",
                4.5,
                423,
                "type 50",
                null,
                true);
        TUPLES_TO_REMOVE.add(new ImmutablePair<>(PARK_SERVICES, new Object[]{"EX-050"}));
        insertTupleAndCheckForErrorCode(PARK_SERVICES, true,
                "RE-021",
                "name r021",
                4.4,
                324,
                "type r021",
                null,
                false);
        TUPLES_TO_REMOVE.add(new ImmutablePair<>(PARK_SERVICES, new Object[]{"RE-021"}));
    }

    @Test
    void reviewConstraintsTest() {
        // test for constraint RATING_FORMAT
        insertTupleAndCheckForErrorCode(REVIEWS, false,
                "12312",
                0,
                LocalDate.of(2001, 10, 10),
                LocalTime.of(12, 0, 0),
                null,
                "mariorossi@gmail.com",
                EXHIBITIONID_SAMPLE);
        // valid tuple
        insertTupleAndCheckForErrorCode(REVIEWS, true,
                "12431",
                4,
                LocalDate.of(2001, 10, 10),
                LocalTime.of(12, 0, 0),
                null,
                "mariorossi@gmail.com",
                "EX-002");
        TUPLES_TO_REMOVE.add(new ImmutablePair<>(REVIEWS, new Object[]{12_431}));
    }

    @SuppressWarnings("CPD-START")
    @Test
    void rideDetailsConstraintsTest() {
        final var rideIDCorrectSample1 = "RI-100";
        final var rideIDCorrectSample2 = "RI-101";
        final var rideIDCorrectSample3 = "RI-102";
        final var rideIDCorrectSample4 = "RI-103";
        final var rideIDCorrectSample5 = "RI-104";
        final var rideIDCorrectSample6 = "RI-105";
        final var rideIDCorrectSample7 = "RI-106";
        final var rideIDCorrectSamplesFormat = "RI-1%";

        // tuples inserted only for tests
        // tuple 1
        insertTupleAndCheckForErrorCode(PARK_SERVICES, true,
                rideIDCorrectSample1, "ride 100", 4.3, 2412, "type 100", null, false);
        insertTupleAndCheckForErrorCode(FACILITIES, true,
                rideIDCorrectSample1, LocalTime.of(9, 0, 0), LocalTime.of(22, 0, 0), false);
        insertTupleAndCheckForErrorCode(RIDES, true,
                rideIDCorrectSample1, "intensity 100", LocalTime.of(0, 3, 0), 121, 100, 200, 30, 100);
        // tuple 2
        insertTupleAndCheckForErrorCode(PARK_SERVICES, true,
                rideIDCorrectSample2, "ride 101", 4.2, 24, "type 101", null, false);
        insertTupleAndCheckForErrorCode(FACILITIES, true, rideIDCorrectSample2, LocalTime.of(9, 0, 0),
                LocalTime.of(22, 0, 0), false);
        insertTupleAndCheckForErrorCode(RIDES, true,
                rideIDCorrectSample2, "intensity 101", LocalTime.of(0, 2, 30), 113, 100, 200, 30, 100);
        // tuple 3
        insertTupleAndCheckForErrorCode(PARK_SERVICES, true,
                rideIDCorrectSample3, "ride 102", 4.1, 241, "type 102", null, false);
        insertTupleAndCheckForErrorCode(FACILITIES, true, rideIDCorrectSample3, LocalTime.of(9, 0, 0),
                LocalTime.of(22, 0, 0), false);
        insertTupleAndCheckForErrorCode(RIDES, true,
                rideIDCorrectSample3, "intensity 102", LocalTime.of(0, 1, 45), 127, 100, 200, 30, 100);
        // tuple 4
        insertTupleAndCheckForErrorCode(PARK_SERVICES, true,
                rideIDCorrectSample4, "ride 103", 4.7, 221, "type 103", null, false);
        insertTupleAndCheckForErrorCode(FACILITIES, true, rideIDCorrectSample4, LocalTime.of(9, 0, 0),
                LocalTime.of(22, 0, 0), false);
        insertTupleAndCheckForErrorCode(RIDES, true,
                rideIDCorrectSample4, "intensity 103", LocalTime.of(0, 3, 30), 127, 100, 200, 30, 100);
        // tuple 5
        insertTupleAndCheckForErrorCode(PARK_SERVICES, true,
                rideIDCorrectSample5, "ride 104", 3.8, 1421, "type 104", null, false);
        insertTupleAndCheckForErrorCode(FACILITIES, true, rideIDCorrectSample5, LocalTime.of(9, 0, 0),
                LocalTime.of(22, 0, 0), false);
        insertTupleAndCheckForErrorCode(RIDES, true,
                rideIDCorrectSample5, "intensity 104", LocalTime.of(0, 1, 45), 127, 100, 200, 30, 100);
        // tuple 6
        insertTupleAndCheckForErrorCode(PARK_SERVICES, true,
                rideIDCorrectSample6, "ride 105", 3.7, 1420, "type 105", null, false);
        insertTupleAndCheckForErrorCode(FACILITIES, true, rideIDCorrectSample6, LocalTime.of(9, 0, 0),
                LocalTime.of(22, 0, 0), false);
        insertTupleAndCheckForErrorCode(RIDES, true,
                rideIDCorrectSample6, "intensity 105", LocalTime.of(0, 1, 35), 127, 100, 200, 30, 100);
        // tuple 7
        insertTupleAndCheckForErrorCode(PARK_SERVICES, true,
                rideIDCorrectSample7, "ride 106", 3.5, 1423, "type 106", null, false);
        insertTupleAndCheckForErrorCode(FACILITIES, true, rideIDCorrectSample7, LocalTime.of(9, 0, 0),
                LocalTime.of(22, 0, 0), false);
        insertTupleAndCheckForErrorCode(RIDES, true,
                rideIDCorrectSample7, "intensity 106", LocalTime.of(0, 2, 45), 127, 100, 200, 30, 100);

        // tests for constraint RIDEID_CHECK
        insertTupleAndCheckForErrorCode(RIDE_DETAILS, false, "DSGS", "O", null);
        // tests for constraint STATUS_CHECK
        insertTupleAndCheckForErrorCode(RIDE_DETAILS, false, rideIDCorrectSample1, 'O', null);
        insertTupleAndCheckForErrorCode(RIDE_DETAILS, false, rideIDCorrectSample2, 'C', LocalTime.of(0, 5, 0));
        insertTupleAndCheckForErrorCode(RIDE_DETAILS, false, rideIDCorrectSample3, 'M', LocalTime.of(0, 5, 0));
        // tests for constraint STATUS_DOMAIN
        insertTupleAndCheckForErrorCode(RIDE_DETAILS, false, rideIDCorrectSample4, 'A', LocalTime.of(0, 10, 0));
        // valid tuples
        insertTupleAndCheckForErrorCode(RIDE_DETAILS, true, rideIDCorrectSample5, 'C', null);
        insertTupleAndCheckForErrorCode(RIDE_DETAILS, true, rideIDCorrectSample6, 'M', null);
        insertTupleAndCheckForErrorCode(RIDE_DETAILS, true, rideIDCorrectSample7, 'O', LocalTime.of(0, 15, 0));

        assertEquals(3, QUERY_BUILDER.createConnection()
                .queryAction(db -> db.deleteFrom(RIDE_DETAILS).
                        where(RIDE_DETAILS.RIDEID.like(rideIDCorrectSamplesFormat)).execute())
                .closeConnection()
                .getResultAsInt());

        // removal of tuples added for tests
        assertEquals(7, QUERY_BUILDER.createConnection()
                .queryAction(db -> db.deleteFrom(RIDES).
                        where(RIDES.RIDEID.like(rideIDCorrectSamplesFormat)).execute())
                .closeConnection()
                .getResultAsInt());

        assertEquals(7, QUERY_BUILDER.createConnection()
                .queryAction(db -> db.deleteFrom(FACILITIES).
                        where(FACILITIES.FACILITYID.like(rideIDCorrectSamplesFormat)).execute())
                .closeConnection()
                .getResultAsInt());
        assertEquals(7, QUERY_BUILDER.createConnection()
                .queryAction(db -> db.deleteFrom(PARK_SERVICES).
                        where(PARK_SERVICES.PARKSERVICEID.like(rideIDCorrectSamplesFormat)).execute())
                .closeConnection()
                .getResultAsInt());
    }

    @Test
    void ridesConstraintsTest() {
        final var rideIDCorrectSample1 = "RI-200";
        final var rideIDCorrectSample2 = "RI-201";
        final var rideIDCorrectSample3 = "RI-202";
        final var rideIDCorrectSample4 = "RI-203";
        final var rideIDCorrectSample5 = "RI-204";
        final var rideIDCorrectSample6 = "RI-205";
        final var rideIDCorrectSample7 = "RI-206";
        final var rideIDCorrectSamplesFormat = "RI-2%";

        // tuples inserted only for tests
        // tuple 1
        insertTupleAndCheckForErrorCode(PARK_SERVICES, true,
                rideIDCorrectSample1, "ride 200", 4.3, 2412, "type 200", null, false);
        insertTupleAndCheckForErrorCode(FACILITIES, true, rideIDCorrectSample1, LocalTime.of(9, 0, 0),
                LocalTime.of(22, 0, 0), false);
        // tuple 2
        insertTupleAndCheckForErrorCode(PARK_SERVICES, true,
                rideIDCorrectSample2, "ride 201", 4.2, 24, "type 201", null, false);
        insertTupleAndCheckForErrorCode(FACILITIES, true, rideIDCorrectSample2, LocalTime.of(9, 0, 0),
                LocalTime.of(22, 0, 0), false);
        // tuple 3
        insertTupleAndCheckForErrorCode(PARK_SERVICES, true,
                rideIDCorrectSample3, "ride 202", 4.1, 241, "type 202", null, false);
        insertTupleAndCheckForErrorCode(FACILITIES, true, rideIDCorrectSample3, LocalTime.of(9, 0, 0),
                LocalTime.of(22, 0, 0), false);
        // tuple 4
        insertTupleAndCheckForErrorCode(PARK_SERVICES, true,
                rideIDCorrectSample4, "ride 203", 4.7, 221, "type 203", null, false);
        insertTupleAndCheckForErrorCode(FACILITIES, true, rideIDCorrectSample4, LocalTime.of(9, 0, 0),
                LocalTime.of(22, 0, 0), false);
        // tuple 5
        insertTupleAndCheckForErrorCode(PARK_SERVICES, true,
                rideIDCorrectSample5, "ride 204", 3.8, 1421, "type 204", null, false);
        insertTupleAndCheckForErrorCode(FACILITIES, true, rideIDCorrectSample5, LocalTime.of(9, 0, 0),
                LocalTime.of(22, 0, 0), false);
        // tuple 6
        insertTupleAndCheckForErrorCode(PARK_SERVICES, true,
                rideIDCorrectSample6, "ride 205", 3.8, 1421, "type 205", null, false);
        insertTupleAndCheckForErrorCode(FACILITIES, true, rideIDCorrectSample6, LocalTime.of(9, 0, 0),
                LocalTime.of(22, 0, 0), false);
        // tuple 7
        insertTupleAndCheckForErrorCode(PARK_SERVICES, true,
                rideIDCorrectSample7, "ride 206", 3.8, 1421, "type 206", null, false);
        insertTupleAndCheckForErrorCode(FACILITIES, true, rideIDCorrectSample7, LocalTime.of(9, 0, 0),
                LocalTime.of(22, 0, 0), false);

        // test for constraint RIDEID_FORMAT
        insertTupleAndCheckForErrorCode(RIDES, false, "AFS", "asfafdgrsgdr", LocalTime.of(0, 3, 0), 342, 100, 200, 30, 100);
        // tests for constraint RIDES_MAX_SEATS_DOMAIN
        insertTupleAndCheckForErrorCode(RIDES, false,
                rideIDCorrectSample6, "intensity 205", LocalTime.of(0, 2, 30), 0, 100, 200, 30, 100);
        insertTupleAndCheckForErrorCode(RIDES, false,
                rideIDCorrectSample7, "intensity 206", LocalTime.of(0, 2, 30), -1, 100, 200, 30, 100);
        // tests for constraint HEIGHT_VALUES_CONSISTENCY
        insertTupleAndCheckForErrorCode(RIDES, false,
                rideIDCorrectSample1, "intensity 200", LocalTime.of(0, 2, 30), 342, 200, 100, 30, 100);
        insertTupleAndCheckForErrorCode(RIDES, false,
                rideIDCorrectSample2, "intensity 201", LocalTime.of(0, 2, 30), 342, 100, 100, 30, 100);
        // tests for constraint WEIGHT_VALUES_CONSISTENCY
        insertTupleAndCheckForErrorCode(RIDES, false,
                rideIDCorrectSample3, "intensity 202", LocalTime.of(0, 2, 30), 342, 100, 200, 100, 30);
        insertTupleAndCheckForErrorCode(RIDES, false,
                rideIDCorrectSample4, "intensity 203", LocalTime.of(0, 2, 30), 342, 100, 200, 30, 30);
        // valid tuples
        insertTupleAndCheckForErrorCode(RIDES, true,
                rideIDCorrectSample5, "intensity 204", LocalTime.of(0, 3, 30), 300, 100, 200, 30, 120);

        assertEquals(1, QUERY_BUILDER.createConnection()
                .queryAction(db -> db.deleteFrom(RIDES).where(RIDES.RIDEID.equal(rideIDCorrectSample5)).execute())
                .closeConnection()
                .getResultAsInt());

        // removal of tuples added for tests
        assertEquals(7, QUERY_BUILDER.createConnection()
                .queryAction(db -> db.deleteFrom(FACILITIES).
                        where(FACILITIES.FACILITYID.like(rideIDCorrectSamplesFormat)).execute())
                .closeConnection()
                .getResultAsInt());
        assertEquals(7, QUERY_BUILDER.createConnection()
                .queryAction(db -> db.deleteFrom(PARK_SERVICES).
                        where(PARK_SERVICES.PARKSERVICEID.like(rideIDCorrectSamplesFormat)).execute())
                .closeConnection()
                .getResultAsInt());

    }

    @SuppressWarnings("CPD-END")
    @Test
    void staffConstraintsTest() {
        final var accountsNum = 7;
        // tuples inserted only for tests
        insertTupleAndCheckForErrorCode(ACCOUNTS, true, "foo1@gmail.com", "foo1.foo1", "foo1foo1foo1", null);
        insertTupleAndCheckForErrorCode(ACCOUNTS, true, "foo2@gmail.com", "foo2.foo2", "foo2foo2foo2", null);
        insertTupleAndCheckForErrorCode(ACCOUNTS, true, "foo3@gmail.com", "foo3.foo3", "foo3foo3foo3", null);
        insertTupleAndCheckForErrorCode(ACCOUNTS, true, "foo4@gmail.com", "foo4.foo4", "foo4foo4foo4", null);
        insertTupleAndCheckForErrorCode(ACCOUNTS, true, "foo5@gmail.com", "foo5.foo5", "foo5foo5foo5", null);
        insertTupleAndCheckForErrorCode(ACCOUNTS, true, "foo6@gmail.com", "foo6.foo6", "foo6foo6foo6", null);
        insertTupleAndCheckForErrorCode(ACCOUNTS, true, "foo7@gmail.com", "foo7.foo7", "foo7foo7foo7", null);

        // test for constraint GENDER_DOMAIN
        insertTupleAndCheckForErrorCode(STAFF, false,
                "E-100",
                "DSFSDKFPSK",
                "foo1@gmail.com",
                "Foo1", "Foo1",
                LocalDate.of(1960, 1, 1),
                "Bologna",
                'O',
                "role 100",
                false, true);

        // tests for constraint ROLE_CHECK
        insertTupleAndCheckForErrorCode(STAFF, false,
                "E-101",
                "SGPFDSPJPDSFJ",
                "foo2@gmail.com",
                "Foo2", "Foo2",
                LocalDate.of(1970, 1, 1),
                "Cesena",
                'M',
                null,
                false, true);
        insertTupleAndCheckForErrorCode(STAFF, false,
                "E-102", "SFDPDSJFPOAJ",
                "foo3@gmail.com",
                "Foo3", "Foo3",
                LocalDate.of(1980, 1, 1),
                "Forlì",
                'M',
                "role 102",
                true, false);

        // test for constraint FLAGS_CONSISTENCY
        insertTupleAndCheckForErrorCode(STAFF, false,
                "E-103", "SJOGPSDJF",
                "foo4@gmail.com",
                "Foo4", "Foo4",
                LocalDate.of(1967, 1, 1),
                "Rimini",
                'F',
                "role 103",
                false, false);
        insertTupleAndCheckForErrorCode(STAFF, false,
                "E-104", "KSPOFJSF",
                "foo5@gmail.com",
                "Foo5", "Foo5",
                LocalDate.of(1966, 1, 1),
                "Riccione",
                'F',
                "role 104",
                true, true);

        // valid tuples
        insertTupleAndCheckForErrorCode(STAFF, true,
                "E-105", "APFDSPF",
                "foo6@gmail.com",
                "Foo6", "Foo6",
                LocalDate.of(1966, 1, 1),
                "Ravenna",
                'M',
                "role 105",
                false, true);
        insertTupleAndCheckForErrorCode(STAFF, true,
                "A-106", "AFSOPFJPAO",
                "foo7@gmail.com",
                "Foo7", "Foo7",
                LocalDate.of(1977, 1, 1),
                "Ferrara",
                'F',
                null,
                true, false);
        removeTuplesFromDB(List.of(new ImmutablePair<>(STAFF, new Object[]{"AFSOPFJPAO"}),
                new ImmutablePair<>(STAFF, new Object[]{"APFDSPF"})));
        assertEquals(accountsNum, QUERY_BUILDER.createConnection()
                .queryAction(db -> db.deleteFrom(ACCOUNTS).where(ACCOUNTS.EMAIL.like("foo%")).execute())
                .closeConnection()
                .getResultAsInt());
    }

    @Test
    void ticketTypesConstraintsTest() {
        final var seasonTicketType = "Season ticket";
        final var singleDayTicketType = "Single day ticket";

        // tuples inserted only for tests
        insertTupleAndCheckForErrorCode(PRICE_LISTS, true, 2001);
        TUPLES_TO_REMOVE.add(new ImmutablePair<>(PRICE_LISTS, new Object[]{2001}));
        insertTupleAndCheckForErrorCode(PRICE_LISTS, true, 2002);
        TUPLES_TO_REMOVE.add(new ImmutablePair<>(PRICE_LISTS, new Object[]{2002}));
        insertTupleAndCheckForErrorCode(PRICE_LISTS, true, 2003);
        TUPLES_TO_REMOVE.add(new ImmutablePair<>(PRICE_LISTS, new Object[]{2003}));
        insertTupleAndCheckForErrorCode(PRICE_LISTS, true, 2004);
        TUPLES_TO_REMOVE.add(new ImmutablePair<>(PRICE_LISTS, new Object[]{2004}));
        insertTupleAndCheckForErrorCode(PRICE_LISTS, true, 2005);
        TUPLES_TO_REMOVE.add(new ImmutablePair<>(PRICE_LISTS, new Object[]{2005}));

        // test for constraint TYPE_DOMAIN
        insertTupleAndCheckForErrorCode(TICKET_TYPES, false, 2001, 50.00, "foo", "Disable", 2);
        // tests for constraint TYPE_CONSISTENCY
        insertTupleAndCheckForErrorCode(TICKET_TYPES, false, 2002, 55.00, singleDayTicketType, "Kids", 3);
        insertTupleAndCheckForErrorCode(TICKET_TYPES, false, 2002, 52.50, seasonTicketType, "Adults", 1);
        // test for constraint CATEGORY_DOMAIN
        insertTupleAndCheckForErrorCode(TICKET_TYPES, false, 2003, 45.00, singleDayTicketType, "foo", 1);
        // tests for constraint PRICE_NON_NEGATIVITY_CHECK
        insertTupleAndCheckForErrorCode(TICKET_TYPES, false, 2004, 0, singleDayTicketType, "Senior", 1);
        insertTupleAndCheckForErrorCode(TICKET_TYPES, false, 2004, -15.45, singleDayTicketType, "Kids", 1);

        // valid tuples
        insertTupleAndCheckForErrorCode(TICKET_TYPES, true, 2005, 26.50, singleDayTicketType, "Disable", 1);
        insertTupleAndCheckForErrorCode(TICKET_TYPES, true, 2005, 50.00, seasonTicketType, "Adults", 5);
        assertEquals(2, QUERY_BUILDER.createConnection()
                .queryAction(db -> db.deleteFrom(TICKET_TYPES).where(TICKET_TYPES.YEAR.equal(2005)).execute())
                .closeConnection().getResultAsInt());
    }

    @Test
    void ticketsConstraintsTest() {
        final var ownerIDSample = "G-001";

        // tests for constraint TICKETID_FORMAT
        insertTupleAndCheckForErrorCode(TICKETS, false, "foo",
                LocalDate.of(2001, 1, 1),
                LocalDate.of(2001, 1, 1),
                null, 0, ownerIDSample);
        // tests for constraint PURCHASE_DATE_CHK
        insertTupleAndCheckForErrorCode(TICKETS, false, "T-100",
                LocalDate.of(2001, 3, 1),
                null, LocalDate.of(2001, 2, 1),
                5, ownerIDSample);
        insertTupleAndCheckForErrorCode(TICKETS, false, "T-101",
                LocalDate.of(2001, 1, 1),
                LocalDate.of(2000, 12, 25),
                null,
                3, ownerIDSample);
        // tests for constraint TICKET_TYPE_CHK
        insertTupleAndCheckForErrorCode(TICKETS, false, "T-102",
                LocalDate.of(2001, 1, 1),
                null, null,
                2, ownerIDSample);

        // valid tuples
        insertTupleAndCheckForErrorCode(TICKETS, true, "T-103",
                LocalDate.of(2001, 1, 1),
                LocalDate.of(2001, 2, 1),
                null,
                1, ownerIDSample);
        TUPLES_TO_REMOVE.add(new ImmutablePair<>(TICKETS, new Object[]{"T-103"}));
        insertTupleAndCheckForErrorCode(TICKETS, true, "T-104",
                LocalDate.of(2001, 1, 1),
                null,
                LocalDate.of(2001, 3, 1),
                5, ownerIDSample);
        TUPLES_TO_REMOVE.add(new ImmutablePair<>(TICKETS, new Object[]{"T-104"}));
    }

    private void insertTupleAndCheckForErrorCode(final Table<Record> table, final boolean isTupleValid, final Object... values) {
        if (!isTupleValid) {
            try {
                assertEquals(0, executeInsertionQuery(table, values), "Inserted tuple even if it is not valid.");
            } catch (DataAccessException ex) {
                final var cause = ex.getCause();
                assertTrue(cause instanceof SQLException);
                assertEquals(CHECK_CONSTRAINT_VIOLATED_ERROR_CODE, ((SQLException) cause).getErrorCode());
            }
        } else {
            assertEquals(1, executeInsertionQuery(table, values));
        }
    }

    private int executeInsertionQuery(final Table<Record> table, final Object... values) {
        return QUERY_BUILDER.createConnection()
                .queryAction(db -> db.insertInto(table)
                        .values(values)
                        .execute())
                .closeConnection()
                .getResultAsInt();
    }

    @AfterAll
    static void tearDown() {
        TUPLES_TO_REMOVE.forEach(pair -> assertEquals(1, QUERY_BUILDER.createConnection()
                .queryAction(db -> db.execute(generateDeletionQuery(pair)))
                .closeConnection()
                .getResultAsInt()));
    }

    static void removeTuplesFromDB(final Collection<Pair<Table<Record>, Object[]>> tuplesToRemove) {
        tuplesToRemove.forEach(pair -> assertEquals(1, QUERY_BUILDER.createConnection()
                .queryAction(db -> db.execute(generateDeletionQuery(pair)))
                .closeConnection()
                .getResultAsInt()));
    }

    private static String generateDeletionQuery(final Pair<Table<Record>, Object[]> pair) {
        final var tableName = pair.getLeft().getName();
        final var pk = getPrimaryKey(pair.getLeft());
        final var builder = new StringBuilder();
        for (int i = 0; i < pk.length; i++) {
            builder.append(pk[i]).append(" = '").append(pair.getRight()[i]).append("' and ");
            if (i == pk.length - 1) {
                builder.replace(builder.length() - 4, builder.length(), "");
            }
        }
        return "delete from " + tableName + " where " + builder;
    }

    @SuppressFBWarnings(value = "NP_NULL_ON_SOME_PATH",
            justification = "False positive. Check done with Objects.requireNonNull()")
    private static String[] getPrimaryKey(final Table<Record> table) {
        final var str = Objects.requireNonNull(table.getPrimaryKey()).toString();
        final var finalStr = str.substring(str.indexOf("(") + 1, str.indexOf(")"));
        final var arr = finalStr.split(", ");
        for (var i = 0; i < arr.length; i++) {
            arr[i] = arr[i].replace("\"", ""); // NOPMD // unable to use StringBuilder
        }
        return arr;
    }
}
