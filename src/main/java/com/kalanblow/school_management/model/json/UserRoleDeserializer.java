package com.kalanblow.school_management.model.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import com.kalanblow.school_management.model.enums.Role;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class UserRoleDeserializer extends JsonDeserializer<Set<Role>> {
    @Override
    public Set<Role> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        Set<Role> roles = new HashSet<>();

        if (node.isArray()) {
            Iterator<JsonNode> elements = node.elements();
            while (elements.hasNext()) {
                JsonNode element = elements.next();
                String roleValue = element.asText();
                roles.add(Role.fromValue(roleValue));
            }
        }
        return roles;
    }
}
