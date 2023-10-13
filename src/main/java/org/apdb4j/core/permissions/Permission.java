package org.apdb4j.core.permissions;

import lombok.NonNull;

/**
 * Represents an account's privileges required to execute a query.
 * @param requiredPermission the required permission
 * @param email the executor's actual email
 * @param values the required {@link AccessSetting}
 */
public record Permission(Access requiredPermission, String email, AccessSetting values) {

    /**
     * Creates a new {@code Permission} record instance.
     * @param requiredPermission the required permission
     * @param email the executor's actual email
     * @param values the required {@link AccessSetting}
     */
    public Permission(final @NonNull Access requiredPermission,
                      final @NonNull String email,
                      final @NonNull AccessSetting values) {
        this.requiredPermission = requiredPermission;
        this.email = email;
        this.values = AccessSetting.of(values);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AccessSetting values() {
        return AccessSetting.of(values);
    }

    /**
     * A builder for {@link Permission}.
     */
    @SuppressWarnings("PMD.LinguisticNaming")
    public static class Builder {

        private Access requiredPermission;
        private String email;
        private AccessSetting values;

        /**
         * Sets the required permission.
         * @param requiredPermission the required permission
         * @return {@code this} for fluent style
         */
        public Builder setRequiredPermission(final @NonNull Access requiredPermission) {
            this.requiredPermission = requiredPermission;
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
            this.values = AccessSetting.of(values);
            return this;
        }

        /**
         * Creates a new instance of {@link Permission}.
         * @return the permission instance
         */
        public Permission build() {
            return new Permission(requiredPermission, email, values);
        }

    }

}
