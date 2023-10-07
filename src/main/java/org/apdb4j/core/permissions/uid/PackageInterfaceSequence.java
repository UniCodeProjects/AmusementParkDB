package org.apdb4j.core.permissions.uid;

import lombok.Getter;
import lombok.ToString;
import org.apdb4j.core.permissions.Access;

/**
 * Represents the UID section that contains the package and interface information.
 */
@Getter
@ToString
public class PackageInterfaceSequence extends HashableSequence implements Sequence {

    private final String hash;
    private final Package aPackage;
    private final Class<? extends Access> aInterface;

    /**
     * Creates a new sequence containing the package and interface information.
     * @param aPackage the package
     * @param aInterface the interface
     */
    public PackageInterfaceSequence(final Package aPackage, final Class<? extends Access> aInterface) {
        this.aPackage = aPackage;
        this.aInterface = aInterface;
        hash = generateHash(this, aPackage.getName() + aInterface.getName());
    }

}
