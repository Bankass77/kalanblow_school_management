package com.kalanblow.school_management.application.dto;

import com.kalanblow.school_management.model.User;
import jakarta.validation.constraints.NotBlank;

public record StudentRequest(@NotBlank  User user, Long schoolClassId) {
}
