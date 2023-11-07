package org.apdb4j.core.permissions;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class AccessTypeTest {

    private static final String GLOBAL = "GLOBAL";
    private static final String LOCAL = "LOCAL";
    private static final String NONE = "NONE";
    private static final String EMPTY = "EMPTY";

    @SuppressFBWarnings("UPM_UNCALLED_PRIVATE_METHOD")
    private static Stream<Arguments> priorityTestCases() {
        return Stream.of(
                Arguments.of(AccessType.Write.class),
                Arguments.of(AccessType.Read.class)
        );
    }

    @ParameterizedTest
    @MethodSource("priorityTestCases")
    <T extends Enum<T>> void priorityTest(final Class<T> accessType) {
        final int a = Enum.valueOf(accessType, GLOBAL).compareTo(Enum.valueOf(accessType, LOCAL));
        final int b = Enum.valueOf(accessType, GLOBAL).compareTo(Enum.valueOf(accessType, NONE));
        final int c = Enum.valueOf(accessType, GLOBAL).compareTo(Enum.valueOf(accessType, EMPTY));
        assertTrue(isGreaterThan(a));
        assertTrue(isGreaterThan(b));
        assertTrue(isGreaterThan(c));
        final int d = Enum.valueOf(accessType, LOCAL).compareTo(Enum.valueOf(accessType, GLOBAL));
        final int e = Enum.valueOf(accessType, LOCAL).compareTo(Enum.valueOf(accessType, NONE));
        final int f = Enum.valueOf(accessType, LOCAL).compareTo(Enum.valueOf(accessType, EMPTY));
        assertTrue(isLessThan(d));
        assertTrue(isGreaterThan(e));
        assertTrue(isGreaterThan(f));
        final int g = Enum.valueOf(accessType, NONE).compareTo(Enum.valueOf(accessType, GLOBAL));
        final int h = Enum.valueOf(accessType, NONE).compareTo(Enum.valueOf(accessType, LOCAL));
        final int i = Enum.valueOf(accessType, NONE).compareTo(Enum.valueOf(accessType, EMPTY));
        assertTrue(isLessThan(g));
        assertTrue(isLessThan(h));
        assertTrue(isGreaterThan(i));
        final int l = Enum.valueOf(accessType, EMPTY).compareTo(Enum.valueOf(accessType, GLOBAL));
        final int m = Enum.valueOf(accessType, EMPTY).compareTo(Enum.valueOf(accessType, LOCAL));
        final int n = Enum.valueOf(accessType, EMPTY).compareTo(Enum.valueOf(accessType, NONE));
        assertTrue(isLessThan(l));
        assertTrue(isLessThan(m));
        assertTrue(isLessThan(n));
    }

    private boolean isLessThan(final int x) {
        return x < 0;
    }

    private boolean isGreaterThan(final int x) {
        return x > 0;
    }

}
