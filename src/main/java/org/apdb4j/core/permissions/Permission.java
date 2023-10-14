package org.apdb4j.core.permissions;

import lombok.NonNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Represents an account's privileges required to execute a query.
 * @param requiredPermission the required permission
 * @param email the executor's actual email
 * @param values the required {@link AccessSetting}
 */
public record Permission(String email, Set<AccessSetting> values, Access... requiredPermission) {

    /**
     * Creates a new {@code Permission} record instance.
     * @param requiredPermission the required permission
     * @param email the executor's actual email
     * @param values the required {@link AccessSetting}
     */
    public Permission(final @NonNull String email,
                      final @NonNull Set<AccessSetting> values,
                      final @NonNull Access... requiredPermission) {
        this.requiredPermission = Arrays.stream(requiredPermission).toArray(Access[]::new);
        this.email = email;
        this.values = values.stream().map(AccessSetting::of).collect(Collectors.toSet());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Access[] requiredPermission() {
        return Arrays.stream(requiredPermission).toArray(Access[]::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<AccessSetting> values() {
        return Set.copyOf(values);
    }

    /**
     * A builder for {@link Permission}.
     */
    @SuppressWarnings("PMD.LinguisticNaming")
    public static class Builder {

        private Access[] requiredPermission;
        private String email;
        private final Set<AccessSetting> values = new HashSet<>();

        /**
         * Sets the required permission.
         * @param requiredPermission the required permission
         * @return {@code this} for fluent style
         */
        public Builder setRequiredPermission(final @NonNull Access... requiredPermission) {
            this.requiredPermission = Arrays.stream(requiredPermission).toArray(Access[]::new);
            return this;
        }

        /**
         * Sets the actual email.
         * @param email the executor's actual email
         * @return {@code this} for fluent style
         */
        public Builder setActualEmail(final @NonNull String email) {
            this.email = email;
            return this;
        }

        /**
         * Sets the required {@link AccessSetting}.
         * @param values the required {@code AccessSetting}
         * @return {@code this} for fluent style
         */
        public Builder setRequiredValues(final @NonNull AccessSetting values) {
            this.values.add(AccessSetting.of(values));
            return this;
        }

        /**
         * Creates a new instance of {@link Permission}.
         * @return the permission instance
         */
        public Permission build() {
            return new Permission(email, values, requiredPermission);
        }

    }

}
