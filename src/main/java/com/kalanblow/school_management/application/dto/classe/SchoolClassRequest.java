package com.kalanblow.school_management.application.dto.classe;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SchoolClassRequest(@NotBlank String name, @NotNull int capacity, @NotNull
Long etablissemntId, Long anneeScolaireId) {
}
