package com.kalanblow.school_management.model.util.converter;

import com.kalanblow.school_management.model.Email;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class EmailAttributeConverter implements AttributeConverter<Email, String> {

    @Override
    public String convertToDatabaseColumn(Email attribute) {

        return attribute.asString();
    }

    @Override
    public Email convertToEntityAttribute(String dbData) {

        return new Email(dbData);
    }

}
