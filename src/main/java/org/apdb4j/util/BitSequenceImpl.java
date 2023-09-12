package org.apdb4j.util;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.ToString;
import org.apdb4j.core.permissions.Access;

import java.util.Collections;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Implementation of a {@link BitSequence}.
 */
@EqualsAndHashCode
@ToString
public class BitSequenceImpl implements BitSequence {

    /**
     * Returns the access interface.
     * @return the interface name
     */
    @Getter @NonNull private final String accessInterface;
    /**
     * Returns the access instance.
     * @return the class name
     */
    @Getter @NonNull private final String accessInstance;
    @NonNull private final SortedMap<String, Bit> accessSettings;

    /**
     * The bit sequence constructor.
     * @param accessInterface an interface that extends {@link Access}
     * @param accessInstance a class that implements {@link Access} or its subinterfaces
     */
    public BitSequenceImpl(@NonNull final Class<? extends Access> accessInterface,
                           @NonNull final Class<? extends Access> accessInstance) {
        if (!accessInterface.isInterface()) {
            throw new IllegalStateException(accessInterface.getName() + " is not an interface");
        }
        // Checks if the class 'accessInstance' implements the accessInterface interface.
        if (accessInstance.isInterface() || !accessInterface.isAssignableFrom(accessInstance)) {
            throw new IllegalStateException(accessInstance.getName()
                    + " is an interface or not a class that implements "
                    + accessInterface.getName());
        }
        this.accessInterface = accessInterface.getName();
        this.accessInstance = accessInstance.getName();
        this.accessSettings = generateMapFromClass(accessInterface, accessInstance);
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

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSequenceAsString() {
        return String.join(
                "",
                getAccessSettings().values().stream()
                        .map(Bit::toString)
                        .toList());
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
