package org.apdb4j.core.permissions.uid;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.apdb4j.core.permissions.Access;
import org.apdb4j.core.permissions.AccessType;
import org.apdb4j.core.permissions.GuestPermission;
import org.apdb4j.core.permissions.account.AccountAccess;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UIDParserTest {

    private static final UID PERMISSION_UID = new AppPermissionUID(new GuestPermission()).getUid();
    private static final List<UIDSection> PARSED = UIDParser.parse(PERMISSION_UID.uid());
    private static final UIDSection ACCOUNT_ACCESS_SECTION = UIDParser.get(PARSED, AccountAccess.class);
    private static final int NUMBER_OF_METHODS = 4;

    @Test
    void packageInterfaceSequenceTest() {
        final var packageInterfaceSequence = ACCOUNT_ACCESS_SECTION.packageInterfaceSequence();
        assertEquals("org.apdb4j.core.permissions.account", packageInterfaceSequence.getPackage().getName());
        assertEquals(AccountAccess.class, packageInterfaceSequence.getInterface());
        assertEquals(packageInterfaceSequence, PackageInterfaceSequence.getFromHash(packageInterfaceSequence.getHash()));
    }

    @SuppressFBWarnings("UPM_UNCALLED_PRIVATE_METHOD")
    private static Stream<Arguments> provideExpectedValuesForReturnSequenceTest() {
        final List<ReturnSequence> sequences = ACCOUNT_ACCESS_SECTION.returnSequence();
        assertEquals(NUMBER_OF_METHODS, sequences.size());
        return Stream.of(
                Arguments.of(sequences.get(0),
                        AccessType.Read.LOCAL,
                        AccessType.Write.LOCAL,
                        Set.of(),
                        Set.of(GuestPermission.class)),
                Arguments.of(sequences.get(1),
                        AccessType.Read.LOCAL,
                        AccessType.Write.LOCAL,
                        Set.of(),
                        Set.of()),
                Arguments.of(sequences.get(2),
                        AccessType.Read.NONE,
                        AccessType.Write.NONE,
                        Set.of(),
                        Set.of()),
                Arguments.of(sequences.get(3),
                        AccessType.Read.GLOBAL,
                        AccessType.Write.LOCAL,
                        Set.of(),
                        Set.of(GuestPermission.class))
        );
    }

    @ParameterizedTest
    @MethodSource("provideExpectedValuesForReturnSequenceTest")
    void returnSequenceTest(final ReturnSequence sequence, final AccessType.Read read, final AccessType.Write write,
                            final Set<Class<? extends Access>> readTarget, final Set<Class<? extends Access>> writeTarget) {
        assertEquals(read, sequence.getRead());
        assertEquals(write, sequence.getWrite());
        assertEquals(readTarget, sequence.getReadTargets());
        assertEquals(writeTarget, sequence.getWriteTargets());
        assertEquals(sequence, ReturnSequence.getFromHash(sequence.getHash()));
    }

}
