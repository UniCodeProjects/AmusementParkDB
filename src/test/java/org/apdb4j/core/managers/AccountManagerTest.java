package org.apdb4j.core.managers;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.apdb4j.db.Tables.ACCOUNTS;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AccountManagerTest {

    private static final String ADMIN_ACCOUNT = "mariorossi@gmail.com";

    @Test
    void addNewAccountTest() {
        assertTrue(AccountManager.addNewAccount("foo@bar.com", "Staff", ADMIN_ACCOUNT));
        final AtomicBoolean result = new AtomicBoolean(false);
        assertDoesNotThrow(() -> result.set(AccountManager.addNewAccount("alice@bob.com",
                "alice123",
                "abc123456",
                "Guest",
                ADMIN_ACCOUNT)));
        assertTrue(result.get());
        assertFalse(AccountManager.addNewAccount("foo@", "Admin", ADMIN_ACCOUNT));
        assertThrows(IllegalArgumentException.class, () -> AccountManager.addNewAccount("foo@bar.it", "Unknown", ADMIN_ACCOUNT));
        // TODO: add case that is all correct but the account, after definePermission is correctly implemented.
    }

    @Test
    void addCredentialsForAccountTest() {
        assertTrue(AccountManager.addNewAccount("foo@mail.com", "Guest", ADMIN_ACCOUNT));
        assertTrue(AccountManager.addCredentialsForAccount("foo@mail.com", "foo_bar123", "foobar123456789", ADMIN_ACCOUNT));
        // TODO: add more test cases.
    }

    @Test
    void updateAccountPasswordTest() {
        assertDoesNotThrow(() -> AccountManager.addNewAccount("alice@mail.com",
                "a_lice123",
                "alice123456789",
                "Guest",
                ADMIN_ACCOUNT));
        assertTrue(AccountManager.updateAccountPassword("alice@mail.com", "alice123456789", "bob123456789", ADMIN_ACCOUNT));
        // TODO: add more test cases.
    }

    @AfterAll
    static void afterAll() {
        List.of("foo@bar.com", "alice@bob.com", "foo@mail.com", "alice@mail.com")
                .forEach(s -> Manager.removeTupleFromDB(ACCOUNTS, ADMIN_ACCOUNT, s));
    }

}
