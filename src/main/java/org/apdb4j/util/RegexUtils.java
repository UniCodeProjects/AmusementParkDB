package org.apdb4j.util;

import lombok.NonNull;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Provides utility methods for regex matching.
 */
public final class RegexUtils {

    private RegexUtils() {
    }

    /**
     * Returns the matched string by the provided regular expression.
     * @param input the input string
     * @param regex the regular expression
     * @return the matched string
     */
    public static @NonNull Optional<String> getMatch(final @NonNull String input, final @NonNull String regex) {
        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(input);
        Optional<String> match = Optional.empty();
        if (matcher.find()) {
            match = Optional.of(matcher.group());
        }
        return match;
    }

}
