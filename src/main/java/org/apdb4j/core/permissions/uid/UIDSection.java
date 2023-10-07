package org.apdb4j.core.permissions.uid;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.List;

/**
 * Represents a section of the {@code PermissionUID} composed by a package, interface and return sequence.
 * @param packageInterfaceSequence the package and interface
 * @param returnSequence the return sequence
 */
@SuppressFBWarnings("EI_EXPOSE_REP")
public record UIDSection(PackageInterfaceSequence packageInterfaceSequence,
                         List<Sequence> returnSequence) implements Sequence {
}
