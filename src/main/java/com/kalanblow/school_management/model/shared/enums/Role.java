package com.kalanblow.school_management.model.shared.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


@Getter
public enum Role implements Serializable {
    STUDENT("Élève"),
    TEACHER("Enseignant"),
    MANAGER("Gestionnaire"),
    ADMIN("Administrateur"),
    USER("Utilisateur"),
    PARENT("Parent");

    private final String value;

    // Constructeur explicite (toujours privé pour les enums)
    Role(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static Role fromValue(String value) {
        for (Role userRole : Role.values()) {
            if (userRole.getValue().equalsIgnoreCase(value)) {
                return userRole;
            }
        }
        throw new IllegalArgumentException("Invalid Role value: " + value);
    }

    public static Set<Role> valueAsSet() {
        return new HashSet<>(Arrays.asList(Role.values()));
    }
}