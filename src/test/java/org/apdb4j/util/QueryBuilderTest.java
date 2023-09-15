package org.apdb4j.util;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.apdb4j.core.permissions.Access;
import org.apdb4j.core.permissions.AccessDeniedException;
import org.apdb4j.core.permissions.services.PictureAccess;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Objects;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;

class QueryBuilderTest {

    private static final QueryBuilder DB = new QueryBuilder();
    private static final Access P1 = new SimpleAccess1();
    private static final Access P2 = new SimpleAccess2();
    private static final Access P3 = new SimpleAccess3();

    @SuppressFBWarnings("UPM_UNCALLED_PRIVATE_METHOD")
    private static Stream<Arguments> accessDeniedTestCases() {
        return Stream.of(
                Arguments.of(P1, P2),
                Arguments.of(P1, P3),
                Arguments.of(P2, P3)
        );
    }

    @ParameterizedTest
    @MethodSource("accessDeniedTestCases")
    void accessDeniedTest(final Access a, final Access b) {
        assertThrows(AccessDeniedException.class, () -> DB.defineAccess(a, b));
    }

    /**
     * A class that models a simple permission.
     */
    private static final class SimpleAccess1 implements PictureAccess {

        private final String type = this.getClass().getName();

        @Override
        public boolean canAccessPicturePath() {
            return true;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            final SimpleAccess1 that = (SimpleAccess1) o;
            return Objects.equals(type, that.type);
        }

        @Override
        public int hashCode() {
            return Objects.hash(type);
        }
    }

    /**
     * A class that models a simple permission.
     */
    private static final class SimpleAccess2 implements PictureAccess {

        private final String type = this.getClass().getName();

        @Override
        public boolean canAccessPicturePath() {
            return true;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            final SimpleAccess2 that = (SimpleAccess2) o;
            return Objects.equals(type, that.type);
        }

        @Override
        public int hashCode() {
            return Objects.hash(type);
        }
    }

    /**
     * A class that models a simple permission.
     */
    private static final class SimpleAccess3 implements PictureAccess {

        private final String type = this.getClass().getName();

        @Override
        public boolean canAccessPicturePath() {
            return true;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            final SimpleAccess3 that = (SimpleAccess3) o;
            return Objects.equals(type, that.type);
        }

        @Override
        public int hashCode() {
            return Objects.hash(type);
        }
    }

}
