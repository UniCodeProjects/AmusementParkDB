package org.apdb4j.core.permissions.uid;

import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.apdb4j.core.permissions.Access;

import java.util.Objects;

/**
 * Represents the UID section that contains the package and interface information.
 */
@ToString
public class PackageInterfaceSequence extends HashableSequence implements Sequence {

    @Getter private final String hash;
    private final Package aPackage;
    private final Class<? extends Access> aInterface;

    /**
     * Creates a new sequence containing the package and interface information.
     * @param aPackage the package
     * @param aInterface the interface
     */
    public PackageInterfaceSequence(final @NonNull Package aPackage, final @NonNull Class<? extends Access> aInterface) {
        this.aPackage = aPackage;
        this.aInterface = aInterface;
        hash = generateHash(this, aPackage.getName() + aInterface.getName());
    }

    /**
     * Retrieves the package contained in the sequence.
     * @return the package
     */
    public @NonNull Package getPackage() {
        return aPackage;
    }

    /**
     * Retrieves the interface contained in the sequence.
     * @return the interface
     */
    public @NonNull Class<? extends Access> getInterface() {
        return aInterface;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PackageInterfaceSequence that = (PackageInterfaceSequence) o;
        return Objects.equals(hash, that.hash) && Objects.equals(aPackage, that.aPackage)
                && Objects.equals(aInterface, that.aInterface);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(hash, aPackage, aInterface);
    }

}
