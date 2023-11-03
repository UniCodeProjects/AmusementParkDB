package org.apdb4j.core.permissions;

import lombok.Getter;
import lombok.NonNull;
import org.apdb4j.db.Tables;
import org.jooq.Record;
import org.jooq.TableField;

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
         * @param value
         * @return {@code this} for fluent style
         */
        public Builder setRequiredValues(final @NonNull Value value) {
            this.values.add(AccessSetting.of(value.getAttribute(), value.getRead(), value.getWrite()));
            return this;
        }

        /**
         * Creates a new instance of {@link Permission}.
         * @return the permission instance
         */
        public Permission build() {
            return new Permission(email, values, requiredPermission);
        }

        @Getter
        public static class Value {

            private final TableField<Record, ?> attribute;
            private AccessType.Read read;
            private AccessType.Write write;

            public Value(final @NonNull TableField<Record, ?> attribute) {
                this.attribute = attribute;
            }

            // the permission is valid if at least one accesstype is valid for the XPermission.
            public Value withAny(final @NonNull AccessType.Read read, final @NonNull AccessType.Write write) {
                this.read = read;
                this.write = write;
                return this;
            }
        }

        public static void main(String[] args) {
            // TODO: Perhaps stop using a builder and put this directly in the query builder?
            new Permission.Builder()
                    .setRequiredPermission(new AdminPermission())
                    .setRequiredValues(new Value(Tables.ACCOUNTS.EMAIL).withAny(AccessType.Read.NONE, AccessType.Write.NONE));
        }

    }

}
