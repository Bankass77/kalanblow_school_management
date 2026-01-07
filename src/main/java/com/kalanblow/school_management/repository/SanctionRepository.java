package com.kalanblow.school_management.repository;

import com.kalanblow.school_management.application.dto.inscription.Sanction;
import com.kalanblow.school_management.model.shared.enums.StatutSanction;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SanctionRepository {
    Sanction save(Sanction sanction);
    Optional<Sanction> findById(Long id);
    List<Sanction> findSanctionsNonLevees(Long studentId);
    List<Sanction> findSanctionsGraves(Long studentId);
    List<Sanction> findByStudentIdOrderByDateSanctionDesc(Long studentId);
    List<Sanction> findByStudentIdAndStatut(Long studentId, StatutSanction statut);
    int countByStudentIdAndStatutAndDateSanctionAfter(
            Long studentId,
            StatutSanction statut,
            LocalDate date
    );
}
