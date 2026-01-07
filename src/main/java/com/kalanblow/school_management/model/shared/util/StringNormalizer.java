package com.kalanblow.school_management.model.shared.util;

import java.text.Normalizer;

public class StringNormalizer {

    public static String normalize(String input) {
        if (input == null) return null;

        return Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .toLowerCase()
                .trim();
    }
}
