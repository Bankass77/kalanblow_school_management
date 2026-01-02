package com.kalanblow.school_management.model.util;

import java.text.ParseException;
import java.util.Locale;

import com.kalanblow.school_management.model.PhoneNumber;
import jakarta.annotation.Nonnull;

import org.springframework.format.Formatter;


public class PhoneNumberFormatter implements Formatter<PhoneNumber> {

    @Override
    @Nonnull
    public String print(@Nonnull PhoneNumber object, @Nonnull Locale locale) {

        return object.asString();
    }

    @Nonnull
    @Override
    public PhoneNumber parse(@Nonnull String text, @Nonnull Locale locale) throws ParseException {

        return new PhoneNumber(text);
    }

}
