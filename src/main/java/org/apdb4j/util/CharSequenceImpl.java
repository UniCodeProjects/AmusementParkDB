package org.apdb4j.util;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.ToString;
import org.apdb4j.core.permissions.Access;
import org.apdb4j.core.permissions.AccessType;

import java.util.Collections;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Implementation of a {@link CharSequence}.
 */
@EqualsAndHashCode
@ToString
public class CharSequenceImpl implements CharSequence {

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
    @NonNull private final SortedMap<String, AccessType> accessSettings;

    /**
     * The char sequence constructor.
     * @param accessInterface an interface that extends {@link Access}
     * @param accessInstance a class that implements {@link Access} or its subinterfaces
     */
    public CharSequenceImpl(@NonNull final Class<? extends Access> accessInterface,
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
    public SortedMap<String, AccessType> getAccessSettings() {
        return Collections.unmodifiableSortedMap(accessSettings);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAccessSetting(@NonNull final String methodName, @NonNull final AccessType value) {
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
                        .map(AccessType::toString)
                        .toList());
    }

    @SneakyThrows
    private SortedMap<String, AccessType> generateMapFromClass(final Class<? extends Access> superInterface,
                                                        final Class<? extends Access> subClass) {
        final SortedMap<String, AccessType> map = new TreeMap<>();
        final Access instance = subClass.getConstructor().newInstance();
        for (final var m : superInterface.getDeclaredMethods()) {
            if (!m.getReturnType().equals(AccessType.class)) {
                continue;
            }
            final var value = (AccessType) m.invoke(instance);
            map.put(m.getName(), value);
        }
        return map;
    }

}
