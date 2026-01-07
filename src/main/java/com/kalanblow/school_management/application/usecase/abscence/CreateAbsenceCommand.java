package com.kalanblow.school_management.application.usecase.abscence;

import java.time.LocalDate;

public record CreateAbsenceCommand(
        Long studentId,
        Long periodeId,
        LocalDate dateAbsence,
        boolean justifie,
        String motif
) {}

