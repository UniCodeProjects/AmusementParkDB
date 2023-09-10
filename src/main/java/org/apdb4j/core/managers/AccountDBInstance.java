package org.apdb4j.core.managers;

import org.apdb4j.core.permissions.Permission;

/**
 * Interface that represents an instance of the table {@link org.apdb4j.db.tables.Accounts}
 * at the application level, with the addition of a {@link Permission}.
 */
public interface AccountDBInstance {

    /**
     * Retrieves the {@link Permission} associated with this account.
     * @return the permissions associated with this account
     */
    Permission getPermissions();

}
