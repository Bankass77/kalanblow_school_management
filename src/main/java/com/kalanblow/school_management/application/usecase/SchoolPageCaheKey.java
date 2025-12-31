package com.kalanblow.school_management.application.usecase;

public record SchoolPageCaheKey(int page,
                                int size,
                                String firstName,
                                String lastName,
                                Long classId) {
}
