package com.kalanblow.school_management.model.shared.enums;

import lombok.Getter;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

@Getter
public enum MaritalStatus implements Serializable {
    MARRIED("Married"),
    SINGLE("Single"),
    DIVORCED("Divorce");

    private final String value;

    MaritalStatus(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static MaritalStatus fromValue(String value) {
        for (MaritalStatus status : MaritalStatus.values()) {
            if (status.getValue().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid MaritalStatus value: " + value);
    }
}