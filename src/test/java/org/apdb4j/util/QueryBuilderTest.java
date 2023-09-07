package org.apdb4j.util;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.apdb4j.core.permissions.Permission;
import org.apdb4j.core.permissions.PermissionDeniedException;
import org.apdb4j.core.permissions.facilities.PictureAccess;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Objects;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;

class QueryBuilderTest {

    private static final QueryBuilder DB = new QueryBuilder();
    private static final Permission P1 = new SimplePermissions1();
    private static final Permission P2 = new SimplePermissions2();
    private static final Permission P3 = new SimplePermissions3();

    @SuppressFBWarnings("UPM_UNCALLED_PRIVATE_METHOD")
    private static Stream<Arguments> permissionDeniedTestCases() {
        return Stream.of(
                Arguments.of(P1, P2),
                Arguments.of(P1, P3),
                Arguments.of(P2, P3)
        );
    }

    @ParameterizedTest
    @MethodSource("permissionDeniedTestCases")
    void permissionDeniedTest(final Permission a, final Permission b) {
        assertThrows(PermissionDeniedException.class, () -> DB.definePermissions(a, b));
    }

    /**
     * A class that models a simple permission.
     */
    private static final class SimplePermissions1 implements PictureAccess {

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
            final SimplePermissions1 that = (SimplePermissions1) o;
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
    private static final class SimplePermissions2 implements PictureAccess {

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
            final SimplePermissions2 that = (SimplePermissions2) o;
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
    private static final class SimplePermissions3 implements PictureAccess {

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
            final SimplePermissions3 that = (SimplePermissions3) o;
            return Objects.equals(type, that.type);
        }

        @Override
        public int hashCode() {
            return Objects.hash(type);
        }
    }

}
