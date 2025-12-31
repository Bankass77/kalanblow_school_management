package com.kalanblow.school_management.application.dto;

import jakarta.validation.constraints.NotBlank;

public record StudentRequest(@NotBlank String firstName, @NotBlank String LastName, Long schoolClassId) {
}
