package org.apdb4j.util;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.NonNull;
import org.apache.commons.lang3.tuple.Pair;
import org.apdb4j.core.permissions.AccessDeniedException;
import org.apdb4j.core.permissions.AccessSetting;
import org.apdb4j.core.permissions.AccessType;
import org.apdb4j.core.permissions.AdminPermission;
import org.apdb4j.core.permissions.GuestPermission;
import org.apdb4j.core.permissions.Permission;
import org.apdb4j.core.permissions.StaffPermission;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;
import java.util.stream.Stream;

import static org.apdb4j.db.Tables.ACCOUNTS;
import static org.apdb4j.db.Tables.CONTRACTS;
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
                Arguments.of(new Permission.Builder()
                        .setRequiredPermission(new StaffPermission())
                        .setRequiredValues(AccessSetting.of(ACCOUNTS.USERNAME, AccessType.Read.GLOBAL, AccessType.Write.GLOBAL))
                        .setActualEmail("mariorossi@gmail.com")
                        .build()),
                // Multiple wrong permission case
                Arguments.of(new Permission.Builder()
                        .setRequiredPermission(new StaffPermission(), new GuestPermission())
                        .setRequiredValues(AccessSetting.of(ACCOUNTS.USERNAME, AccessType.Read.GLOBAL, AccessType.Write.GLOBAL))
                        .setActualEmail("mariorossi@gmail.com")
                        .build()),
                // Wrong email case
                Arguments.of(new Permission.Builder()
                        .setRequiredPermission(new AdminPermission())
                        .setRequiredValues(AccessSetting.of(PICTURES.PATH, AccessType.Read.GLOBAL, AccessType.Write.GLOBAL))
                        .setActualEmail("sofiaverdi@gmail.com")
                        .build()),
                // Wrong AccessSetting case
                Arguments.of(new Permission.Builder()
                        .setRequiredPermission(new GuestPermission())
                        .setRequiredValues(AccessSetting.of(ACCOUNTS.EMAIL,
                                AccessType.Read.GLOBAL,
                                Pair.of(AccessType.Write.GLOBAL, Set.of(GuestPermission.class))))
                        .setRequiredValues(AccessSetting.of(CONTRACTS.CONTRACTID, AccessType.Read.NONE, AccessType.Write.GLOBAL))
                        .setActualEmail("alessandrogialli@gmail.com")
                        .build())
        );
    }

    @ParameterizedTest
    @MethodSource("accessDeniedTestCases")
    void accessDeniedTest(final @NonNull Permission permissionBuilder) {
        assertThrows(AccessDeniedException.class, () -> DB.definePermissions(permissionBuilder));
    }

    @Test
    void adminPermissionTest() {
        assertDoesNotThrow(() -> DB.definePermissions(new Permission.Builder()
                .setRequiredPermission(new AdminPermission())
                .setRequiredValues(AccessSetting.of(ACCOUNTS.USERNAME, AccessType.Read.NONE, AccessType.Write.NONE))
                .setActualEmail("mariorossi@gmail.com")
                .build()));
        assertDoesNotThrow(() -> DB.definePermissions(new Permission.Builder()
                .setRequiredPermission(new GuestPermission())
                .setRequiredValues(AccessSetting.of(Set.of(FACILITIES.OPENINGTIME, FACILITIES.CLOSINGTIME),
                        AccessType.Read.GLOBAL,
                        AccessType.Write.NONE))
                .setActualEmail("alessandrogialli@gmail.com")
                .build()));
    }

    @Test
    void accessDeniedBySetTest() {
        final var set = Set.of(
                new Permission.Builder()
                        .setRequiredPermission(new StaffPermission())
                        .setRequiredValues(AccessSetting.of(ACCOUNTS.USERNAME,
                                AccessType.Read.GLOBAL,
                                AccessType.Write.GLOBAL))
                        .setActualEmail("mariorossi@gmail.com")
                        .build(),
                new Permission.Builder()
                        .setRequiredPermission(new AdminPermission())
                        .setRequiredValues(AccessSetting.of(PICTURES.PATH,
                                AccessType.Read.GLOBAL,
                                AccessType.Write.GLOBAL))
                        .setActualEmail("sofiaverdi@gmail.com")
                        .build(),
                new Permission.Builder()
                        .setRequiredPermission(new GuestPermission())
                        .setRequiredValues(AccessSetting.of(ACCOUNTS.EMAIL,
                                AccessType.Read.GLOBAL,
                                Pair.of(AccessType.Write.GLOBAL, Set.of(GuestPermission.class))))
                        .setActualEmail("alessandrogialli@gmail.com")
                        .build()
        );
        assertThrows(AccessDeniedException.class, () -> DB.definePermissions(set));
    }

}
