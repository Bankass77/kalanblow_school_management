package com.kalanblow.school_management.application.usecase.abscence;

import java.time.LocalDate;

public record AbscencePageCacheKey(int page, int size, Long studentId, LocalDate startDate, LocalDate endDate,
                                   Long periodId, String motif) {
}
