package com.kalanblow.school_management.model.enums;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import com.kalanblow.school_management.model.json.MaritalStatusDeserializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(using = MaritalStatusDeserializer.class)
public enum MaritalStatus implements Serializable {
    MARRIED("Married"), SINGLE("Single"), DIVORCED("Divorce");
	private String value;

}
