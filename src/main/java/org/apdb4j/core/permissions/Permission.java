package org.apdb4j.core.permissions;

import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.lang3.tuple.Pair;
import org.jooq.Record;
import org.jooq.TableField;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
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
         * @param value the required value
         * @return {@code this} for fluent style
         * @see Value
         */
        public Builder setRequiredValues(final @NonNull Value value) {
            if (Objects.nonNull(value.getAttribute())) {
                this.values.add(AccessSetting.of(value.getAttribute(),
                        Pair.of(value.getRead().orElse(AccessType.Read.EMPTY),
                                value.getReadTargets().orElse(Collections.emptySet())),
                        Pair.of(value.getWrite().orElse(AccessType.Write.EMPTY),
                                value.getWriteTargets().orElse(Collections.emptySet()))));
                return this;
            }
            this.values.add(AccessSetting.of(value.getAttributes(),
                    Pair.of(value.getRead().orElse(AccessType.Read.EMPTY),
                            value.getReadTargets().orElse(Collections.emptySet())),
                    Pair.of(value.getWrite().orElse(AccessType.Write.EMPTY),
                            value.getWriteTargets().orElse(Collections.emptySet()))));
            return this;
        }

        /**
         * Creates a new instance of {@link Permission}.
         * @return the permission instance
         */
        public Permission build() {
            return new Permission(email, values, requiredPermission);
        }

        /**
         * The required value for defining a permission.
         */
        @Getter
        public static class Value {

            private TableField<Record, ?> attribute;
            private Set<TableField<Record, ?>> attributes;
            private final Optional<AccessType.Read> read;
            private final Optional<Set<Class<? extends Access>>> readTargets;
            private final Optional<AccessType.Write> write;
            private final Optional<Set<Class<? extends Access>>> writeTargets;

            /**
             * Defines the {@code Read} and {@code Write} values for the provided attribute.
             * {@link org.apdb4j.util.QueryBuilder#definePermissions(Permission)} requires BOTH {@code AccessType} to be valid.
             * @param attribute the attribute
             * @param read the read value
             * @param write the write value
             */
            public Value(final @NonNull TableField<Record, ?> attribute,
                         final @NonNull AccessType.Read read,
                         final @NonNull AccessType.Write write) {
                this.attribute = attribute;
                this.read = Optional.of(read);
                this.readTargets = Optional.empty();
                this.write = Optional.of(write);
                this.writeTargets = Optional.empty();
            }

            /**
             * Defines the {@code Read} and {@code Write} values for the provided attributes.
             * {@link org.apdb4j.util.QueryBuilder#definePermissions(Permission)} requires BOTH {@code AccessType} to be valid.
             * @param attributes the set of attributes
             * @param read the read value
             * @param write the write value
             */
            public Value(final @NonNull Set<TableField<Record, ?>> attributes,
                         final @NonNull AccessType.Read read,
                         final @NonNull AccessType.Write write) {
                this.attributes = Set.copyOf(attributes);
                this.read = Optional.of(read);
                this.readTargets = Optional.empty();
                this.write = Optional.of(write);
                this.writeTargets = Optional.empty();
            }

            /**
             * Defines the {@code Read} value for the provided attribute.
             * Only {@code AccessType.Read} is required to be
             * valid by {@link org.apdb4j.util.QueryBuilder#definePermissions(Permission)}.
             * @param attribute the attribute
             * @param read the read value
             */
            public Value(final @NonNull TableField<Record, ?> attribute, final @NonNull AccessType.Read read) {
                this.attribute = attribute;
                this.read = Optional.of(read);
                this.readTargets = Optional.empty();
                this.write = Optional.empty();
                this.writeTargets = Optional.empty();
            }

            /**
             * Defines the {@code Write} value for the provided attribute.
             * Only {@code AccessType.Write} is required to be
             * valid by {@link org.apdb4j.util.QueryBuilder#definePermissions(Permission)}.
             * @param attribute the attribute
             * @param write the write value
             */
            public Value(final @NonNull TableField<Record, ?> attribute, final @NonNull AccessType.Write write) {
                this.attribute = attribute;
                this.read = Optional.empty();
                this.readTargets = Optional.empty();
                this.write = Optional.of(write);
                this.writeTargets = Optional.empty();
            }

            /**
             * Defines the {@code Read} and {@code Write} values and their access targets for the provided attribute.
             * {@link org.apdb4j.util.QueryBuilder#definePermissions(Permission)} requires BOTH {@code AccessType} to be valid.
             * @param attribute the attribute
             * @param read the read value
             * @param write the write value
             */
            public Value(final @NonNull TableField<Record, ?> attribute,
                         final @NonNull Pair<AccessType.Read, Set<Class<? extends Access>>> read,
                         final @NonNull Pair<AccessType.Write, Set<Class<? extends Access>>> write) {
                this.attribute = attribute;
                this.read = Optional.ofNullable(read.getLeft());
                this.readTargets = Optional.ofNullable(read.getRight());
                this.write = Optional.ofNullable(write.getLeft());
                this.writeTargets = Optional.ofNullable(write.getRight());
            }

            /**
             * Defines the {@code Read} and {@code Write} (with its access targets) values for the provided attribute.
             * {@link org.apdb4j.util.QueryBuilder#definePermissions(Permission)} requires BOTH {@code AccessType} to be valid.
             * @param attribute the attribute
             * @param read the read value
             * @param write the write value and its access targets
             */
            public Value(final @NonNull TableField<Record, ?> attribute,
                         final @NonNull AccessType.Read read,
                         final @NonNull Pair<AccessType.Write, Set<Class<? extends Access>>> write) {
                this.attribute = attribute;
                this.read = Optional.of(read);
                this.readTargets = Optional.empty();
                this.write = Optional.ofNullable(write.getLeft());
                this.writeTargets = Optional.ofNullable(write.getRight());
            }

            /**
             * Defines the {@code Read} (with its access targets) and {@code Write} values for the provided attribute.
             * {@link org.apdb4j.util.QueryBuilder#definePermissions(Permission)} requires BOTH {@code AccessType} to be valid.
             * @param attribute the attribute
             * @param read the read value and its access targets
             * @param write the write value
             */
            public Value(final @NonNull TableField<Record, ?> attribute,
                         final @NonNull Pair<AccessType.Read, Set<Class<? extends Access>>> read,
                         final @NonNull AccessType.Write write) {
                this.attribute = attribute;
                this.read = Optional.of(read.getLeft());
                this.readTargets = Optional.ofNullable(read.getRight());
                this.write = Optional.of(write);
                this.writeTargets = Optional.empty();
            }

            /**
             * Defines the {@code Read} value and its access targets for the provided attribute.
             * Only {@code AccessType.Read} is required to be
             * valid by {@link org.apdb4j.util.QueryBuilder#definePermissions(Permission)}.
             * @param attribute the attribute
             * @param read the read value
             * @param readTargets the access targets
             */
            public Value(final @NonNull TableField<Record, ?> attribute,
                         final @NonNull AccessType.Read read,
                         final @NonNull Set<Class<? extends Access>> readTargets) {
                this.attribute = attribute;
                this.read = Optional.of(read);
                this.readTargets = Optional.of(readTargets);
                this.write = Optional.empty();
                this.writeTargets = Optional.empty();
            }

            /**
             * Defines the {@code Write} value and its access targets for the provided attribute.
             * Only {@code AccessType.Write} is required to be
             * valid by {@link org.apdb4j.util.QueryBuilder#definePermissions(Permission)}.
             * @param attribute the attribute
             * @param write the write value
             * @param writeTargets the access targets
             */
            public Value(final @NonNull TableField<Record, ?> attribute,
                         final @NonNull AccessType.Write write,
                         final @NonNull Set<Class<? extends Access>> writeTargets) {
                this.attribute = attribute;
                this.read = Optional.empty();
                this.readTargets = Optional.empty();
                this.write = Optional.of(write);
                this.writeTargets = Optional.of(writeTargets);
            }

        }

    }

}
