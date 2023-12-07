package org.apdb4j.util;

import org.junit.jupiter.api.Test;

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
class QueryBuilderTest {

//    private static final QueryBuilder DB = new QueryBuilder();

//    @SuppressFBWarnings("UPM_UNCALLED_PRIVATE_METHOD")
//    private static Stream<Arguments> accessDeniedTestCases() {
//        return Stream.of(
//                // Wrong permission case
//                Arguments.of(new Permission.Builder()
//                        .setRequiredPermission(new StaffPermission())
//                        .setRequiredValues(new Value(ACCOUNTS.USERNAME, AccessType.Read.GLOBAL, AccessType.Write.GLOBAL))
//                        .setActualEmail("mariorossi@gmail.com")
//                        .build()),
//                // Multiple wrong permission case
//                Arguments.of(new Permission.Builder()
//                        .setRequiredPermission(new StaffPermission(), new GuestPermission())
//                        .setRequiredValues(new Value(ACCOUNTS.USERNAME, AccessType.Read.GLOBAL))
//                        .setActualEmail("mariorossi@gmail.com")
//                        .build()),
//                // Wrong email case
//                Arguments.of(new Permission.Builder()
//                        .setRequiredPermission(new AdminPermission())
//                        .setRequiredValues(new Value(PICTURES.PATH, AccessType.Write.GLOBAL))
//                        .setActualEmail("sofiaverdi@gmail.com")
//                        .build()),
//                // Wrong AccessSetting case
//                Arguments.of(new Permission.Builder()
//                        .setRequiredPermission(new GuestPermission())
//                        .setRequiredValues(new Value(ACCOUNTS.EMAIL, AccessType.Read.GLOBAL, Pair.of(AccessType.Write.GLOBAL,
//                                Set.of(GuestPermission.class))))
//                        .setRequiredValues(new Value(CONTRACTS.CONTRACTID, AccessType.Read.NONE, AccessType.Write.GLOBAL))
//                        .setActualEmail("alessandrogialli@gmail.com")
//                        .build())
//        );
//    }

//    @ParameterizedTest
//    @MethodSource("accessDeniedTestCases")
//    void accessDeniedTest(final @NonNull Permission permissionBuilder) {
//        assertThrows(AccessDeniedException.class, () -> DB.definePermissions(permissionBuilder));
//    }

    @Test
    void adminPermissionTest() {
//        assertDoesNotThrow(() -> DB.definePermissions(new Permission.Builder()
//                .setRequiredPermission(new AdminPermission())
//                .setRequiredValues(new Value(ACCOUNTS.USERNAME, AccessType.Read.NONE, AccessType.Write.NONE))
//                .setActualEmail("mariorossi@gmail.com")
//                .build()));
//        assertDoesNotThrow(() -> DB.definePermissions(new Permission.Builder()
//                .setRequiredPermission(new GuestPermission())
//                .setRequiredValues(new Value(Set.of(FACILITIES.OPENINGTIME, FACILITIES.CLOSINGTIME),
//                        AccessType.Read.GLOBAL,
//                        AccessType.Write.NONE))
//                .setActualEmail("alessandrogialli@gmail.com")
//                .build()));
    }

    @Test
    void accessDeniedBySetTest() {
//        final var set = Set.of(
//                new Permission.Builder()
//                        .setRequiredPermission(new StaffPermission())
//                        .setRequiredValues(new Value(ACCOUNTS.USERNAME, AccessType.Read.GLOBAL, AccessType.Write.GLOBAL))
//                        .setActualEmail("mariorossi@gmail.com")
//                        .build(),
//                new Permission.Builder()
//                        .setRequiredPermission(new AdminPermission())
//                        .setRequiredValues(new Value(PICTURES.PATH, AccessType.Read.GLOBAL))
//                        .setActualEmail("sofiaverdi@gmail.com")
//                        .build(),
//                new Permission.Builder()
//                        .setRequiredPermission(new GuestPermission())
//                        .setRequiredValues(new Value(ACCOUNTS.EMAIL, AccessType.Read.GLOBAL, Pair.of(AccessType.Write.GLOBAL,
//                                Set.of(GuestPermission.class))))
//                        .setActualEmail("alessandrogialli@gmail.com")
//                        .build()
//        );
//        assertThrows(AccessDeniedException.class, () -> DB.definePermissions(set));
    }

    @Test
    void accessTypePriorityTest() {
//        assertThrows(AccessDeniedException.class, () -> DB.definePermissions(new Permission.Builder()
//                .setRequiredPermission(new GuestPermission())
//                .setRequiredValues(new Value(ACCOUNTS.PASSWORD, AccessType.Read.GLOBAL))
//                .setActualEmail("alessandrogialli@gmail.com")
//                .build()));
    }

}
