package org.apdb4j.util;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.NonNull;
import org.apdb4j.core.permissions.AbstractPermission;
import org.apdb4j.core.permissions.Access;
import org.apdb4j.core.permissions.AccessDeniedException;
import org.apdb4j.core.permissions.AccessType;
import org.apdb4j.core.permissions.services.PictureAccess;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;

class QueryBuilderTest {

    private static final QueryBuilder DB = new QueryBuilder();
    private static final Access D1 = new DummyAccess1();
    private static final Access D2 = new DummyAccess2();
    private static final Access D3 = new DummyAccess3();

    @SuppressFBWarnings("UPM_UNCALLED_PRIVATE_METHOD")
    private static Stream<Arguments> accessDeniedTestCases() {
        return Stream.of(
                Arguments.of(D1, D2),
                Arguments.of(D1, D3),
                Arguments.of(D2, D3)
        );
    }

    @ParameterizedTest
    @MethodSource("accessDeniedTestCases")
    void accessDeniedTest(final Access a, final Access b) {
        assertThrows(AccessDeniedException.class, () -> DB.defineAccess(a, b));
    }

    /**
     * A class that models a dummy permission.
     */
    protected static final class DummyAccess1 extends AbstractPermission implements PictureAccess {
        @Override
        public @NonNull AccessType getAccessOfPicturePath() {
            return AccessType.WRITE;
        }
    }

    /**
     * A class that models a dummy permission.
     */
    protected static final class DummyAccess2 extends AbstractPermission implements PictureAccess {
        @Override
        public @NonNull AccessType getAccessOfPicturePath() {
            return AccessType.READ;
        }
    }

    /**
     * A class that models a dummy permission.
     */
    protected static final class DummyAccess3 extends AbstractPermission implements PictureAccess {
        @Override
        public @NonNull AccessType getAccessOfPicturePath() {
            return AccessType.ALL;
        }
    }

}
