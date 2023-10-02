package org.apdb4j.core.permissions;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ImmutableAccessSettingsTest {

    private static final Pair<AccessType, Set<Class<? extends Access>>> ALL = Pair.of(
            AccessType.GLOBAL_ALL,
            Set.of(StaffPermission.class));
    private static final Pair<AccessType, Set<Class<? extends Access>>> READ = Pair.of(
            AccessType.LOCAL_READ,
            Set.of(GuestPermission.class));
    private static final Pair<AccessType, Set<Class<? extends Access>>> WRITE = Pair.of(
            AccessType.GLOBAL_WRITE,
            Set.of(AdminPermission.class));
    private static final AccessSettings ACCESS_SETTINGS = new ImmutableAccessSettings(ALL, READ, WRITE);

    @Test
    void testSetAccess() {
        final var exception = UnsupportedOperationException.class;
        assertThrows(exception, () -> ACCESS_SETTINGS.setAllAccess(ALL.getLeft(), ALL.getRight()));
        assertThrows(exception, () -> ACCESS_SETTINGS.setReadAccess(READ.getLeft(), READ.getRight()));
        assertThrows(exception, () -> ACCESS_SETTINGS.setWriteAccess(WRITE.getLeft(), WRITE.getRight()));
    }

    @Test
    void testGetAccess() {
        assertEquals(ALL, ACCESS_SETTINGS.getAllAccess());
        assertEquals(READ, ACCESS_SETTINGS.getReadAccess());
        assertEquals(WRITE, ACCESS_SETTINGS.getWriteAccess());
    }

    @Test
    void testReturnedImmutablePair() {
        assertInstanceOf(ImmutablePair.class, ACCESS_SETTINGS.getAllAccess());
        assertInstanceOf(ImmutablePair.class, ACCESS_SETTINGS.getReadAccess());
        assertInstanceOf(ImmutablePair.class, ACCESS_SETTINGS.getWriteAccess());
    }

    @Test
    void testNoneAtCreation() {
        final AccessSettings accessSettings = new ImmutableAccessSettings(WRITE, ALL, READ);
        assertEquals(AccessType.NONE, accessSettings.getAllAccess().getLeft());
        assertEquals(AccessType.NONE, accessSettings.getReadAccess().getLeft());
        assertEquals(AccessType.NONE, accessSettings.getWriteAccess().getLeft());
    }

}
