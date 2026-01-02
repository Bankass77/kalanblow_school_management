package com.kalanblow.school_management.model.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.kalanblow.school_management.model.enums.Gender;

import java.io.IOException;

public class GenderDeserializer extends JsonDeserializer<Gender> {
    @Override
    public Gender deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String value = jsonParser.getText();
        for (Gender gender : Gender.values()) {
            if (gender.getValue().equalsIgnoreCase(value)) {
                return gender;
            }
        }
        throw new IllegalArgumentException("Invalid Gender value: " + value);
    }
}
