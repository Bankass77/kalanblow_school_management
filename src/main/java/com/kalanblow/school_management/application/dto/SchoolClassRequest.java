package com.kalanblow.school_management.application.dto;

import jakarta.validation.constraints.NotBlank;

public record SchoolClassRequest(@NotBlank String name, @NotBlank int capacity) {
}
