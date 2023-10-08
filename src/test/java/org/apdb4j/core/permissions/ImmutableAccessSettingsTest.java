package org.apdb4j.core.permissions;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ImmutableAccessSettingsTest {

    private static final Pair<AccessType.Read, Set<Class<? extends Access>>> READ = Pair.of(
            AccessType.Read.LOCAL,
            Set.of(GuestPermission.class));
    private static final Pair<AccessType.Write, Set<Class<? extends Access>>> WRITE = Pair.of(
            AccessType.Write.GLOBAL,
            Set.of(AdminPermission.class));
    private static final AccessSetting ACCESS_SETTINGS = new ImmutableAccessSetting(READ, WRITE);

    @Test
    void testSetAccess() {
        final var exception = UnsupportedOperationException.class;
        assertThrows(exception, () -> ACCESS_SETTINGS.setReadAccess(READ.getLeft(), READ.getRight()));
        assertThrows(exception, () -> ACCESS_SETTINGS.setWriteAccess(WRITE.getLeft(), WRITE.getRight()));
    }

    @Test
    void testGetAccess() {
        assertEquals(READ, ACCESS_SETTINGS.getReadAccess());
        assertEquals(WRITE, ACCESS_SETTINGS.getWriteAccess());
    }

    @Test
    void testReturnedImmutablePair() {
        assertInstanceOf(ImmutablePair.class, ACCESS_SETTINGS.getReadAccess());
        assertInstanceOf(ImmutablePair.class, ACCESS_SETTINGS.getWriteAccess());
    }

}
