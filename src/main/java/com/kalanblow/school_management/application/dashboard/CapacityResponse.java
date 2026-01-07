package com.kalanblow.school_management.application.dashboard;

import java.time.LocalDateTime;

public record CapacityResponse(Integer totalCapacity,
                               Integer currentStudents,
                               Double occupancyRate,
                               LocalDateTime timestamp) {
}
