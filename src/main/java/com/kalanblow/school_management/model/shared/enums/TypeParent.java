package com.kalanblow.school_management.model.shared.enums;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.io.Serializable;

@Getter
public enum TypeParent implements Serializable {
    PERE("Père"),
    MERE("Mère"),
    TUTEUR("Tuteur");

    private final String value;

    TypeParent(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static TypeParent fromValue(String value) {
        for (TypeParent type : TypeParent.values()) {
            if (type.getValue().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid TypeParent value: " + value);
    }
}