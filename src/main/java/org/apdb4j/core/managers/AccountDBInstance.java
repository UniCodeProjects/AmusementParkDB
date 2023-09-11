package org.apdb4j.core.managers;

import org.apdb4j.core.permissions.Access;

/**
 * Interface that represents an instance of the table {@link org.apdb4j.db.tables.Accounts}
 * at the application level, with the addition of an {@link Access}.
 */
public interface AccountDBInstance {

    /**
     * Retrieves the {@link Access} associated with this account.
     * @return the access associated with this account
     */
    Access getPermissions();

}
