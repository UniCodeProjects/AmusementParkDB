package org.apdb4j.util;

import lombok.NonNull;
import org.apache.commons.codec.digest.XXHash32;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * Utility class to generate a hash using XXHash32.
 */
public final class HashUtils {

    private HashUtils() {
    }

    /**
     * Generates an XXHash32 from the provided input.
     * @param input the objects to generate the hash from
     * @return the hash represented as a hexadecimal string
     */
    public static @NonNull String generate(final @NonNull Object... input) {
        final XXHash32 hash = new XXHash32();
        final byte[] inputBytes = Arrays.stream(input)
                .map(o -> o.toString().getBytes(StandardCharsets.UTF_8))
                .reduce((bytes1, bytes2) -> {
                    final var outputStream = new ByteArrayOutputStream();
                    try {
                        outputStream.write(bytes1);
                        outputStream.write(bytes2);
                        return outputStream.toByteArray();
                    } catch (final IOException e) {
                        return new byte[0];
                    }
                }).orElse(new byte[0]);
        hash.update(inputBytes);
        return Long.toHexString(hash.getValue());
    }

    /**
     * Generates an XXHash32 from the provided input.
     * @param input the string to generate the hash from
     * @return the hash represented as a hexadecimal string
     */
    public static @NonNull String generate(final @NonNull String input) {
        final XXHash32 hash = new XXHash32();
        hash.update(input.getBytes(StandardCharsets.UTF_8));
        return Long.toHexString(hash.getValue());
    }

}
