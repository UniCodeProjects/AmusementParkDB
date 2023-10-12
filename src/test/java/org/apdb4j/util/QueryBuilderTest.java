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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;
import java.util.stream.Stream;

import static org.apdb4j.db.Tables.ACCOUNTS;
import static org.apdb4j.db.Tables.FACILITIES;
import static org.apdb4j.db.Tables.PICTURES;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
class QueryBuilderTest {

    private static final QueryBuilder DB = new QueryBuilder();

    @SuppressFBWarnings("UPM_UNCALLED_PRIVATE_METHOD")
    private static Stream<Arguments> accessDeniedTestCases() {
        return Stream.of(
                // Wrong permission case
                Arguments.of(new StaffPermission(),
                        "mariorossi@gmail.com",
                        AccessSetting.of(ACCOUNTS.USERNAME, AccessType.Read.GLOBAL, AccessType.Write.GLOBAL)),
                // Wrong email case
                Arguments.of(new AdminPermission(),
                        "sofiaverdi@gmail.com",
                        AccessSetting.of(PICTURES.PATH, AccessType.Read.GLOBAL, AccessType.Write.GLOBAL),
                // Wrong AccessSetting case
                Arguments.of(new GuestPermission(),
                        "alessandrogialli@gmail.com",
                        AccessSetting.of(ACCOUNTS.EMAIL,
                                AccessType.Read.GLOBAL,
                                Pair.of(AccessType.Write.GLOBAL, Set.of(GuestPermission.class))))
        ));
    }

    @ParameterizedTest
    @MethodSource("accessDeniedTestCases")
    void accessDeniedTest(final @NonNull Access requiredPermission,
                          final @NonNull String actualAccountEmail,
                          final @NonNull AccessSetting values) {
        assertThrows(AccessDeniedException.class,
                () -> DB.definePermissions(new QueryBuilder.CheckingValues(requiredPermission, actualAccountEmail),
                        new QueryBuilder.ActualValues(values)));
    }

    @Test
    void adminPermissionTest() {
        assertDoesNotThrow(() -> DB.definePermissions(
                new QueryBuilder.CheckingValues(new AdminPermission(), "mariorossi@gmail.com"),
                new QueryBuilder.ActualValues(AccessSetting.of(ACCOUNTS.USERNAME, AccessType.Read.NONE, AccessType.Write.NONE))));
        assertDoesNotThrow(() -> DB.definePermissions(
                new QueryBuilder.CheckingValues(new GuestPermission(), "alessandrogialli@gmail.com"),
                new QueryBuilder.ActualValues(AccessSetting.of(Set.of(FACILITIES.OPENINGTIME, FACILITIES.CLOSINGTIME),
                        AccessType.Read.GLOBAL,
                        AccessType.Write.NONE))));
    }

}
