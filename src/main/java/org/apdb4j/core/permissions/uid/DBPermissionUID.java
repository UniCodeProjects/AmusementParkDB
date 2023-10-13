package org.apdb4j.core.permissions.uid;

import lombok.Getter;
import lombok.NonNull;
import org.apdb4j.core.permissions.Access;
import org.apdb4j.util.QueryBuilder;
import org.jooq.Record;
import org.jooq.Result;

import java.util.NoSuchElementException;

import static org.apdb4j.db.Tables.ACCOUNTS;
import static org.apdb4j.db.Tables.PERMISSIONS;

/**
 * Represents an UID (Unique ID) for a set of requiredPermission settings (permission).
 * It represents the database-side permission UID.
 * @see Access
 */
@Getter
public class DBPermissionUID implements PermissionUID {

    private final UID uid;
    private static final String CLASS_NAME_PATTERN_TO_MATCH = "Permission";

    /**
     * Retrieves the UID associated with an account.<br>
     * A {@link NoSuchElementException} is thrown if no account is linked to the given email.
     * @param accountEmail the account's email from where to get the UID
     */
    public DBPermissionUID(final @NonNull String accountEmail) {
        final int accountsFound = new QueryBuilder()
                .createConnection()
                .queryAction(db -> db.selectCount()
                        .from(ACCOUNTS)
                        .where(ACCOUNTS.EMAIL.eq(accountEmail))
                        .fetchOne(0, int.class))
                .closeConnection()
                .getResultAsInt();
        if (accountsFound == 0) {
            throw new NoSuchElementException();
        }
        final String type = new QueryBuilder()
                .createConnection()
                .queryAction(db -> db.select(ACCOUNTS.PERMISSIONTYPE)
                        .from(ACCOUNTS)
                        .where(ACCOUNTS.EMAIL.eq(accountEmail))
                        .fetch())
                .closeConnection()
                .getResultAsRecords()
                .stream().filter(record -> record.size() > 0)
                .findFirst()
                .orElseThrow()
                .getValue(ACCOUNTS.PERMISSIONTYPE);
        final Result<Record> uid = new QueryBuilder()
                .createConnection()
                .queryAction(db -> db.select(PERMISSIONS.ACCESSSEQUENCE)
                        .from(PERMISSIONS)
                        .where(PERMISSIONS.PERMISSIONTYPE.eq(type))
                        .fetch())
                .closeConnection()
                .getResultAsRecords();
        if (uid.isEmpty()) {
            throw new IllegalStateException("Could not fetch UID from DB.");
        }
        this.uid = new UID(uid.get(0).getValue(PERMISSIONS.ACCESSSEQUENCE));
    }

}
