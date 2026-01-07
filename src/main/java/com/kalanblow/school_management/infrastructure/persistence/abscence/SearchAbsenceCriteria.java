package com.kalanblow.school_management.infrastructure.persistence.abscence;

import com.kalanblow.school_management.model.anneescolaire.Abscence;

import java.time.LocalDate;

public record SearchAbsenceCriteria(Long studentId,
                                    Long periodeId,
                                    LocalDate startDate,
                                    LocalDate endDate,
                                    Boolean justifie,
                                    String motif) {
}
