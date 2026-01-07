package com.kalanblow.school_management.application.dto.student;

import com.kalanblow.school_management.model.shared.User;

import java.time.LocalDate;

public record StudentResponse(User user, LocalDate dateDeNaissance, String className, String numeroIne) {
}
