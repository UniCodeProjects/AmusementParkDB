package org.apdb4j.core.managers;

import org.apdb4j.core.permissions.AccessDeniedException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.apdb4j.db.Tables.ACCOUNTS;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
class AccountManagerTest {

    private static final String ADMIN_ACCOUNT = "mariorossi@gmail.com";
    private static final String GUEST_ACCOUNT = "riccardoferrari@gmail.com";
    public static final String STAFF_ACCOUNT = "andreaverdi@gmail.com";

    @Test
    void addNewAccountTest() {
        assertDoesNotThrow(() -> {
            assertTrue(AccountManager.addNewAccount("foo@bar.com", "Staff", STAFF_ACCOUNT));
            final AtomicBoolean result = new AtomicBoolean(false);
            assertDoesNotThrow(() -> result.set(AccountManager.addNewAccount("alice@bob.com",
                    "alice123",
                    "abc123456",
                    "Guest",
                    ADMIN_ACCOUNT)));
            assertTrue(result.get());
            assertFalse(AccountManager.addNewAccount("foo@", "Admin", ADMIN_ACCOUNT));
            assertThrows(IllegalArgumentException.class,
                    () -> AccountManager.addNewAccount("foo@bar.it", "Unknown", ADMIN_ACCOUNT));
            assertThrows(AccessDeniedException.class,
                    () -> AccountManager.addNewAccount("bar@foo.com", "Guest", GUEST_ACCOUNT));
        });
    }

    @Test
    void addCredentialsForAccountTest() {
        assertDoesNotThrow(() -> {
            assertTrue(AccountManager.addNewAccount("foo@mail.com", "Staff", ADMIN_ACCOUNT));
            assertThrows(AccessDeniedException.class,
                    () -> AccountManager.addCredentialsForAccount("foo@mail.com",
                            "foo_bar123",
                            "foobar123456789",
                            GUEST_ACCOUNT));
            assertTrue(AccountManager.addCredentialsForAccount("foo@mail.com", "foo_bar123", "foobar123456789", STAFF_ACCOUNT));
            assertFalse(AccountManager.addCredentialsForAccount("foo@mail.com",
                    "new_foo_bar123",
                    "new_foobar123456789",
                    ADMIN_ACCOUNT));
            assertFalse(AccountManager.addCredentialsForAccount("not.an@email.com", "username123", "A@BbCc25", ADMIN_ACCOUNT));
        });
    }

    @Test
    void updateAccountPasswordTest() {
        assertDoesNotThrow(() -> {
            AccountManager.addNewAccount("alice@mail.com",
                    "a_lice123",
                    "alice123456789",
                    "Staff",
                    ADMIN_ACCOUNT);
            assertThrows(AccessDeniedException.class,
                    () -> AccountManager.updateAccountPassword("alice@mail.com", "alice123456789", "bob1234567", GUEST_ACCOUNT));
            assertTrue(AccountManager.updateAccountPassword("alice@mail.com", "alice123456789", "bob1234567", STAFF_ACCOUNT));
            assertFalse(AccountManager.updateAccountPassword("not.an@email.com", "bob1234567", "alice123456789", ADMIN_ACCOUNT));
            assertFalse(AccountManager.updateAccountPassword("alice@mail.com", "A@BbCc25", "alice123456789", ADMIN_ACCOUNT));
        });
    }

    @AfterAll
    static void afterAll() {
        List.of("foo@bar.com", "alice@bob.com", "foo@mail.com", "alice@mail.com")
                .forEach(s -> Manager.removeTupleFromDB(ACCOUNTS, ADMIN_ACCOUNT, s));
    }

}
