package org.apdb4j.core.permissions;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apdb4j.db.Tables;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ImmutableAccessSettingsTest {

    private static final Pair<AccessType.Read, Set<Class<? extends Access>>> READ = Pair.of(
            AccessType.Read.LOCAL,
            Set.of(GuestPermission.class));
    private static final Pair<AccessType.Write, Set<Class<? extends Access>>> WRITE = Pair.of(
            AccessType.Write.GLOBAL,
            Set.of(AdminPermission.class));
    private static final AccessSetting ACCESS_SETTINGS = new ImmutableAccessSetting(Tables.ACCOUNTS.USERNAME, READ, WRITE);

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

    @Test
    void testAttributePresence() {
        assertTrue(ACCESS_SETTINGS.getAttribute().isPresent());
        assertTrue(ACCESS_SETTINGS.getAttributes().isPresent());
        final var a1 = new ImmutableGlobalAccessSetting();
        assertTrue(a1.getAttribute().isEmpty());
        assertTrue(a1.getAttributes().isEmpty());
        final var a2 = new ImmutableNoneAccessSetting();
        assertTrue(a2.getAttribute().isEmpty());
        assertTrue(a2.getAttributes().isEmpty());
    }

}
