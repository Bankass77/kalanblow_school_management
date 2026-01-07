package com.kalanblow.school_management.application.dashboard;

public record ClassDetailResponse(String name,
                                  Integer capacity,
                                  Integer students,
                                  Double occupancy,
                                  Integer availableSeats) {
}
