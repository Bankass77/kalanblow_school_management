package com.kalanblow.school_management.model.enums;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.kalanblow.school_management.model.json.UserRoleDeserializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
@JsonDeserialize(using = UserRoleDeserializer.class)
@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum Role implements Serializable {
    STUDENT("Elève"), TEACHER("Enseignant"), MANAGER("Gestionnaire"), ADMIN("Administrateur"), USER("Utilisateur"), PARENT("Parent");

    private String value;

	public static Set<Role> valueAsSet() {
        return new HashSet<>(Arrays.asList(Role.values()));
    }

    public static Role fromValue(String value) {
        for (Role userRole :Role.values()) {
            if (userRole.getValue().equalsIgnoreCase(value)) {
                return userRole;
            }
        }
        throw new IllegalArgumentException("Invalid UserRole value: " + value);
    }

}
