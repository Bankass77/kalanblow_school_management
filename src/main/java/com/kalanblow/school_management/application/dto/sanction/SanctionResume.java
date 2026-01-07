package com.kalanblow.school_management.application.dto.sanction;

import com.kalanblow.school_management.model.shared.enums.TypeSanction;

import java.time.LocalDate;

public record SanctionResume(Long id,
                             TypeSanction typeSanction,
                             LocalDate dateSanction,
                             Integer gravite,
                             String raison) {
}
