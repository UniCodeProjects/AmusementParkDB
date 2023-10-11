package org.apdb4j.util;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.NonNull;
import org.apache.commons.lang3.tuple.Pair;
import org.apdb4j.core.permissions.Access;
import org.apdb4j.core.permissions.AccessDeniedException;
import org.apdb4j.core.permissions.AccessSetting;
import org.apdb4j.core.permissions.AccessType;
import org.apdb4j.core.permissions.AdminPermission;
import org.apdb4j.core.permissions.GuestPermission;
import org.apdb4j.core.permissions.StaffPermission;
import org.jooq.Record;
import org.jooq.TableField;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;
import java.util.stream.Stream;

import static org.apdb4j.db.Tables.ACCOUNTS;
import static org.apdb4j.db.Tables.PICTURES;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
class QueryBuilderTest {

    private static final QueryBuilder DB = new QueryBuilder();

    @SuppressFBWarnings("UPM_UNCALLED_PRIVATE_METHOD")
    private static Stream<Arguments> accessDeniedTestCases() {
        return Stream.of(
                Arguments.of(new AdminPermission(),
                        ACCOUNTS.USERNAME,
                        AccessSetting.of(ACCOUNTS.USERNAME, AccessType.Read.NONE, AccessType.Write.NONE),
                        "mariorossi@gmail.com"),
                Arguments.of(new StaffPermission(),
                        PICTURES.PATH,
                        AccessSetting.of(PICTURES.PATH, AccessType.Read.LOCAL, AccessType.Write.LOCAL),
                        "sofiaverdi@gmail.com"),
                Arguments.of(new GuestPermission(),
                        ACCOUNTS.EMAIL,
                        AccessSetting.of(ACCOUNTS.EMAIL, AccessType.Read.GLOBAL, Pair.of(AccessType.Write.GLOBAL, Set.of(GuestPermission.class))),
                        "foo.bar@mail.com")
        );
    }

    @ParameterizedTest
    @MethodSource("accessDeniedTestCases")
    void accessDeniedTest(final @NonNull Access requiredAccess,
                          final @NonNull TableField<Record, ?> recordTableField,
                          final @NonNull AccessSetting values,
                          final @NonNull String actualAccountEmail) {
        assertThrows(AccessDeniedException.class,
                () -> DB.definePermissions(requiredAccess, recordTableField, values, actualAccountEmail));
    }

}
