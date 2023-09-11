package org.apdb4j.util;

import lombok.Getter;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.apdb4j.core.permissions.Access;

import java.util.Collections;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Implementation of a {@link BitSequence}.
 */
public class BitSequenceImpl implements BitSequence {

    /**
     * Returns the access type.
     * @return the type of access
     */
    @Getter @NonNull private final Class<? extends Access> accessType;
    @NonNull private final SortedMap<String, Bit> accessSettings;

    /**
     * The bit sequence constructor.
     * @param accessInterface an interface that extends {@link Access}
     * @param accessImpl a class that implements {@link Access} or its subinterfaces
     */
    public BitSequenceImpl(@NonNull final Class<? extends Access> accessInterface,
                           @NonNull final Class<? extends Access> accessImpl) {
        if (!accessInterface.isInterface()) {
            throw new IllegalStateException(accessInterface.getName() + " is not an interface");
        }
        // Checks if the class 'accessImpl' implements the accessInterface interface.
        if (accessImpl.isInterface() || !accessInterface.isAssignableFrom(accessImpl)) {
            throw new IllegalStateException(accessImpl.getName()
                    + " is an interface or not a class that implements "
                    + accessInterface.getName());
        }
        this.accessType = accessInterface;
        this.accessSettings = generateMapFromClass(accessInterface, accessImpl);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SortedMap<String, Bit> getAccessSettings() {
        return Collections.unmodifiableSortedMap(accessSettings);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAccessSetting(@NonNull final String methodName, @NonNull final Bit value) {
        accessSettings.replace(methodName, value);
    }

    @SneakyThrows
    private SortedMap<String, Bit> generateMapFromClass(final Class<? extends Access> superInterface,
                                                        final Class<? extends Access> subClass) {
        final SortedMap<String, Bit> map = new TreeMap<>();
        final Access instance = subClass.getConstructor().newInstance();
        for (final var m : superInterface.getDeclaredMethods()) {
            if (!m.getReturnType().equals(boolean.class)) {
                continue;
            }
            final var value = (boolean) m.invoke(instance);
            map.put(m.getName(), Bit.fromBoolean(value));
        }
        return map;
    }

}
