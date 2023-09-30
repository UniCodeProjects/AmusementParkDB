package org.apdb4j.core.permissions.uid;

import lombok.NonNull;

/**
 * An UID record backed by a string.
 * @param uid the string to be turned into an UID
 */
public record UID(@NonNull String uid) {
}
