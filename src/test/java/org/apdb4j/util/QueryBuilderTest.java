package org.apdb4j.util;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.NonNull;
import org.apdb4j.core.permissions.AbstractPermission;
import org.apdb4j.core.permissions.Access;
import org.apdb4j.core.permissions.AccessDeniedException;
import org.apdb4j.core.permissions.AccessSettings;
import org.apdb4j.core.permissions.AccessType;
import org.apdb4j.core.permissions.ImmutableAccessSettings;
import org.apdb4j.core.permissions.uid.AppPermissionUID;
import org.apdb4j.core.permissions.services.PictureAccess;
import org.jooq.Record;
import org.jooq.Result;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.apdb4j.db.Tables.ACCOUNTS;
import static org.apdb4j.db.Tables.PERMISSIONS;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
class QueryBuilderTest {

    private static final QueryBuilder DB = new QueryBuilder();

    @BeforeEach
    void setUp() {
        new AppPermissionUID(new FooPermission()).insertInDB();
        new AppPermissionUID(new BarPermission()).insertInDB();
        DB.createConnection()
                .queryAction(db -> db.insertInto(ACCOUNTS,
                                ACCOUNTS.EMAIL,
                                ACCOUNTS.USERNAME,
                                ACCOUNTS.PASSWORD,
                                ACCOUNTS.PERMISSIONTYPE)
                        .values("foo@mail.com", "foo", "f1234567", "Foo")
                        .execute())
                .closeConnection();
        DB.createConnection()
                .queryAction(db -> db.insertInto(ACCOUNTS,
                                ACCOUNTS.EMAIL,
                                ACCOUNTS.USERNAME,
                                ACCOUNTS.PASSWORD,
                                ACCOUNTS.PERMISSIONTYPE)
                        .values("bar@mail.com", "bar", "b1234567", "Bar")
                        .execute())
                .closeConnection();
    }

    @SuppressFBWarnings("UPM_UNCALLED_PRIVATE_METHOD")
    private static Stream<Arguments> accessDeniedTestCases() {
        return Stream.of(
                Arguments.of(new FooPermission(), "bar@mail.com"),
                Arguments.of(new BarPermission(), "foo@mail.com")
        );
    }

    @ParameterizedTest
    @MethodSource("accessDeniedTestCases")
    void accessDeniedTest(final Access permission, final String email) {
        assertThrows(AccessDeniedException.class, () -> DB.definePermissions(permission, email));
    }

    @AfterEach
    void tearDown() {
        DB.createConnection()
                .queryAction(db -> db.deleteFrom(ACCOUNTS)
                        .where(ACCOUNTS.PERMISSIONTYPE.eq("Foo"))
                        .or(ACCOUNTS.PERMISSIONTYPE.eq("Bar"))
                        .execute())
                .closeConnection();
        DB.createConnection()
                .queryAction(db -> db.deleteFrom(PERMISSIONS)
                        .where(PERMISSIONS.PERMISSIONTYPE.eq("Foo"))
                        .or(PERMISSIONS.PERMISSIONTYPE.eq("Bar"))
                        .execute())
                .closeConnection();
        final Result<Record> records1 = DB.createConnection()
                .queryAction(db -> db.select()
                        .from(PERMISSIONS)
                        .where(PERMISSIONS.PERMISSIONTYPE.eq("Foo"))
                        .or(PERMISSIONS.PERMISSIONTYPE.eq("Bar"))
                        .fetch())
                .closeConnection()
                .getResultAsRecords();
        final Result<Record> records2 = DB.createConnection()
                .queryAction(db -> db.select()
                        .from(ACCOUNTS)
                        .where(ACCOUNTS.PERMISSIONTYPE.eq("Foo"))
                        .or(ACCOUNTS.PERMISSIONTYPE.eq("Bar"))
                        .fetch())
                .closeConnection()
                .getResultAsRecords();
        assertTrue(records1.isEmpty());
        assertTrue(records2.isEmpty());
    }

    /**
     * A class that models a dummy permission.
     */
    protected static final class FooPermission extends AbstractPermission implements PictureAccess {
        @Override
        public @NonNull AccessSettings getAccessOfPicturePath() {
            return new ImmutableAccessSettings(AccessType.Read.LOCAL, AccessType.Write.NONE);
        }
    }

    /**
     * A class that models a dummy permission.
     */
    protected static final class BarPermission extends AbstractPermission implements PictureAccess {
        @Override
        public @NonNull AccessSettings getAccessOfPicturePath() {
            return new ImmutableAccessSettings(AccessType.Read.NONE, AccessType.Write.GLOBAL);
        }
    }

}
