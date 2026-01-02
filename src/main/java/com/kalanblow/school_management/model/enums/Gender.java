package com.kalanblow.school_management.model.enums;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import com.kalanblow.school_management.model.json.GenderDeserializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@JsonDeserialize(using = GenderDeserializer.class)
@NoArgsConstructor
@AllArgsConstructor
public enum Gender implements Serializable {

    MALE("Homme"), FEMALE("Femme");

    private String value;

	public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
