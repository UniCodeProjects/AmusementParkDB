package org.apdb4j.core.permissions;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.jooq.Record;
import org.jooq.TableField;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Handler to access the deserialised permissions.
 */
public final class PermissionHandler {

    private static final List<Attribute> PERMISSIONS = deserializePermissions();

    private PermissionHandler() {
    }

    /**
     * Retrieves the permission given the attribute and user type.
     * @param attribute the attribute
     * @param userType the user type
     * @return the permission
     */
    public static Attribute getPermissions(final TableField<Record, ?> attribute, final String userType) {
        final List<Attribute> result = PERMISSIONS.stream()
                .filter(a -> a.attribute().equals(attribute.getQualifiedName().unquotedName().toString())
                        && a.userTypePermissions().stream()
                        .anyMatch(userTypePermission -> userTypePermission.userType().equalsIgnoreCase(userType)))
                .toList();
        if (result.size() != 1) {
            throw new IllegalStateException("Found multiple matches for: "
                    + attribute.getQualifiedName().unquotedName() + ", " + userType);
        }
        return result.get(0);
    }

    private static List<Attribute> deserializePermissions() {
        final Gson gson = new GsonBuilder()
                .registerTypeAdapter(Attribute.class, new PermissionDeserializer())
                .create();
        try (FileReader reader = new FileReader("src/main/resources/config/permissions.json", StandardCharsets.UTF_8)) {
            final Type perm = TypeToken.getParameterized(List.class, Attribute.class).getType();
            return gson.fromJson(reader, perm);
        } catch (final IOException e) {
            throw new IllegalStateException(e);
        }
    }

}
