package org.apdb4j.core.permissions;

import lombok.SneakyThrows;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * An abstract permission used to implement the {@code Equals()} method
 * based on the return values of the implemented access methods.
 */
public abstract class AbstractPermission implements Access {

    // Field lazily initialised, necessary because of reflection use.
    private Supplier<List<AccessType>> accessTypes = () -> {
        final List<AccessType> value = getMethodsReturnValues(getClass());
        // Reassigning the implementation of Supplier.get() to return the exiting value.
        accessTypes = () -> value;
        return value;
    };

    @SneakyThrows
    private List<AccessType> getMethodsReturnValues(final Class<? extends Access> access) {
        final var instance = access.getConstructor().newInstance();
        final List<AccessType> list = new ArrayList<>();
        final List<Method> methods = Arrays.stream(
                instance.getClass()
                        .getDeclaredMethods())
                .sorted(Comparator.comparing(Method::getName))
                .toList();
        for (final var m : methods) {
            final var returnValue = (AccessType) m.invoke(instance);
            list.add(returnValue);
        }
        return list;
    }

    /**
     * This {@code equals()} override controls if two permissions are equals
     * based on the return value of the implemented access methods.<br>
     * This means that if two classes implement the same interfaces in the same
     * way they will be equals regardless of the different class name.
     * @param o the other permission object
     * @return {@code true} if they implement the same methods with the same
     *         return value.
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (!(o instanceof Access)) {
            return false;
        }
        if (getClass() != o.getClass()) {
            final var thisInterfaces = new HashSet<>(Arrays.asList(getClass().getInterfaces()));
            final var oInterfaces = new HashSet<>(Arrays.asList(o.getClass().getInterfaces()));
            if (thisInterfaces.containsAll(oInterfaces)) {
                // control if they implement the same interfaces and have the same return values.
                final var thisReturnValues = getMethodsReturnValues(getClass());
                final var oReturnValues = getMethodsReturnValues(o.getClass().asSubclass(Access.class));
                return thisReturnValues.equals(oReturnValues);
            }
        }
        final AbstractPermission that = (AbstractPermission) o;
        return Objects.equals(accessTypes.get(), that.accessTypes.get());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(accessTypes.get());
    }

}
