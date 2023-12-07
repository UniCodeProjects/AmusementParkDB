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
import java.util.Objects;

/**
 * Custom JSON deserializer for the Permission record object.
 * @see Permission
 */
public class PermissionDeserializer implements JsonDeserializer<Permission> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Permission deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) {
        final JsonObject jsonObject = json.getAsJsonObject();
        final JsonElement attributeArray = jsonObject.get("attributes");
        if (attributeArray.isJsonNull()) {
            return new Permission("admin", Collections.emptyList());
        }
        final List<Attribute> attributes = new ArrayList<>();
        for (final JsonElement attributeElement : attributeArray.getAsJsonArray()) {
            final JsonObject attributeObject =  attributeElement.getAsJsonObject();
            final String attribute = attributeObject.get("attribute").getAsString();
            final JsonArray accessArray = Objects.requireNonNull(attributeObject.getAsJsonArray("access"));
            final List<Access> accesses = new ArrayList<>();
            for (final JsonElement accessElement : accessArray) {
                final JsonObject accessObject = accessElement.getAsJsonObject();
                final String target = accessObject.get("target").getAsString();
                final String accessType = accessObject.get("type").getAsString();
                final boolean read = accessObject.get("read").getAsBoolean();
                final boolean write = accessObject.get("write").getAsBoolean();
                accesses.add(new Access(target, accessType, read, write));
            }
            attributes.add(new Attribute(attribute, accesses));
        }
        final String type = jsonObject.get("type").getAsString();
        return new Permission(type, attributes);
    }

}
