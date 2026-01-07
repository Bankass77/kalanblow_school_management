package com.kalanblow.school_management.application.dto.note;

import java.time.LocalDate;

public record AbscenceResponse(    Long studentId,
                                   Long periodeId,
                                   LocalDate dateAbsence,
                                   boolean justifie,
                                   String motif) {
}
