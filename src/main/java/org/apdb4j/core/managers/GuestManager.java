package org.apdb4j.core.managers;

import lombok.NonNull;
import org.apdb4j.util.QueryBuilder;

import static org.apdb4j.db.Tables.GUESTS;
import static org.apdb4j.db.Tables.STAFF;

/**
 * Contains all the SQL queries that are related to the {@link org.apdb4j.db.tables.Guests} table.
 */
public final class GuestManager {

    private static final QueryBuilder DB = new QueryBuilder();
    private static final String PERMISSION_TYPE = "Guest";

    private GuestManager() {
    }

    /**
     * Performs the SQL query that adds a new guest in the database.
     * @param guestID the identifier of the new guest.
     * @param name the guest's name.
     * @param surname the guest's surname.
     * @param email the guest's email.
     * @return {@code true} on successful tuple insertion
     */
    public static boolean addNewGuest(final @NonNull String guestID, final @NonNull String name, final @NonNull String surname,
                                      final @NonNull String email) {
        if (isStaff(email)) {
            return false;
        }
        DB.createConnection()
                .queryAction(db -> {
                    db.transaction(configuration -> {
                        AccountManager.addNewAccount(email, PERMISSION_TYPE);
                        configuration.dsl()
                                .insertInto(GUESTS)
                                .values(guestID, email, name, surname)
                                .execute();
                    });
                    return 1;
                })
                .closeConnection();
        return true;
    }

    /**
     * Performs the SQL query that adds a new guest in the database.
     * @param guestID the identifier of the new guest.
     * @param name the guest's name.
     * @param surname the guest's surname.
     * @param email the guest's email.
     * @param username the username of the new account.
     * @param password the password of the new account.
     * @return {@code true} on successful tuple insertion
     */
    public static boolean addNewGuest(final @NonNull String guestID,
                                      final @NonNull String name,
                                      final @NonNull String surname,
                                      final @NonNull String email,
                                      final @NonNull String username,
                                      final @NonNull String password) {
        if (isStaff(email)) {
            return false;
        }
        DB.createConnection()
                .queryAction(db -> {
                    db.transaction(configuration -> {
                        AccountManager.addNewAccount(email,
                                username,
                                password,
                                PERMISSION_TYPE
                        );
                        configuration.dsl()
                                .insertInto(GUESTS)
                                .values(guestID, email, name, surname)
                                .execute();
                    });
                    return 1;
                })
                .closeConnection();
        return true;
    }

    /**
     * Retrieves the name of the guest with the provided {@code guestID}, if such guest exists.
     * Otherwise, an {@link IllegalArgumentException} will be thrown.
     * @param guestID the guest identifier.
     * @return the name of the guest with the provided {@code guestID}.
     */
    public static String getGuestName(final @NonNull String guestID) {
        if (isIDInvalid(guestID)) {
            throw new IllegalArgumentException("\"" + guestID + "\"" + " is not a valid guest identifier");
        }
        return DB.createConnection()
                .queryAction(db -> db.select(GUESTS.NAME)
                        .from(GUESTS)
                        .where(GUESTS.GUESTID.eq(guestID))
                        .fetch())
                .closeConnection()
                .getResultAsRecords()
                .getValue(0, GUESTS.NAME);
    }

    /**
     * Retrieves the surname of the guest with the provided {@code guestID}, if such guest exists.
     * Otherwise, an {@link IllegalArgumentException} will be thrown.
     * @param guestID the guest identifier.
     * @return the surname of the guest with the provided {@code guestID}.
     */
    public static String getGuestSurname(final @NonNull String guestID) {
        if (isIDInvalid(guestID)) {
            throw new IllegalArgumentException("\"" + guestID + "\"" + " is not a valid guest identifier");
        }
        return DB.createConnection()
                .queryAction(db -> db.select(GUESTS.SURNAME)
                        .from(GUESTS)
                        .where(GUESTS.GUESTID.eq(guestID))
                        .fetch())
                .closeConnection()
                .getResultAsRecords()
                .getValue(0, GUESTS.SURNAME);
    }

    private static boolean isIDInvalid(final @NonNull String guestID) {
        return DB.createConnection()
                .queryAction(db -> db.selectCount()
                        .from(GUESTS)
                        .where(GUESTS.GUESTID.eq(guestID))
                        .fetchOne(0, int.class))
                .closeConnection()
                .getResultAsInt() == 0;
    }

    private static boolean isStaff(final String email) {
        return DB.createConnection()
                .queryAction(db -> db.selectCount()
                        .from(STAFF)
                        .where(STAFF.EMAIL.eq(email))
                        .fetchOne(0, int.class))
                .closeConnection()
                .getResultAsInt() == 1;
    }

}
