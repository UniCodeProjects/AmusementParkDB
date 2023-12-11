package org.apdb4j.core.permissions;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Custom JSON deserializer for the Permission record object.
 * @see Permission
 */
public class PermissionDeserializer implements JsonDeserializer<Attribute> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Attribute deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) {
        final JsonObject jsonObject = json.getAsJsonObject();
        final String attribute = jsonObject.get("attribute").getAsString();
        final JsonArray userTypePermsArray = jsonObject.getAsJsonArray("userTypePermissions");
        final List<UserTypePermission> userTypePermissions = new ArrayList<>();
        for (final JsonElement userTypePermElement : userTypePermsArray) {
            final JsonObject userTypePermObject = userTypePermElement.getAsJsonObject();
            final String userType = userTypePermObject.get("userType").getAsString();
            final JsonArray permissionArray = userTypePermObject.getAsJsonArray("permissions");
            final List<Permission> permissions = new ArrayList<>();
            for (final JsonElement permissionElement : permissionArray) {
                final JsonObject permissionObject = permissionElement.getAsJsonObject();
                final JsonElement forUserTypeArray = permissionObject.get("forUserType");
                List<String> forUserTypeList;
                if (forUserTypeArray.isJsonNull()) {
                    forUserTypeList = Collections.emptyList();
                } else {
                    forUserTypeList = forUserTypeArray.getAsJsonArray()
                            .asList().stream()
                            .map(JsonElement::getAsString)
                            .toList();
                }
                final boolean read = permissionObject.get("read").getAsBoolean();
                final boolean write = permissionObject.get("write").getAsBoolean();
                permissions.add(new Permission(forUserTypeList, read, write));
            }
            userTypePermissions.add(new UserTypePermission(userType, permissions));
        }
        return new Attribute(attribute, userTypePermissions);
    }

}
