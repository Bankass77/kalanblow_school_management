package com.kalanblow.school_management.infrastructure.persistence.sanction;

import com.kalanblow.school_management.application.dto.inscription.Sanction;
import com.kalanblow.school_management.model.shared.enums.GraviteSanction;
import com.kalanblow.school_management.model.shared.enums.StatutSanction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.util.List;

public interface JpaSanctionRepository extends JpaRepository<Sanction, Long>, JpaSpecificationExecutor<Sanction> {
    List<Sanction> findByStudentId(Long studentId);

    List<Sanction> findByStudentIdAndDateLeveeIsNull(Long studentId);

    // Cette méthode remplace findSanctionsGraves
    List<Sanction> findByStudentIdAndGraviteIn(Long studentId, List<GraviteSanction> gravites);

    List<Sanction> findByStudentIdOrderByDateSanctionDesc(Long studentId);

    List<Sanction> findByStudentIdAndStatut(Long studentId, StatutSanction statut);

    int countByStudentIdAndStatutAndDateSanctionAfter(
            Long studentId,
            StatutSanction statut,
            LocalDate date);

    // Méthodes supplémentaires
    List<Sanction> findByStudentIdAndAppliqueeTrue(Long studentId);

    List<Sanction> findByStudentIdAndDateSanctionBetween(
            Long studentId,
            LocalDate dateDebut,
            LocalDate dateFin);

    List<Sanction> findByStatutAndDateDebutLessThanEqualAndDateFinGreaterThanEqualOrDateFinIsNull(
            StatutSanction statut,
            LocalDate currentDate1,
            LocalDate currentDate2);

    int countByStudentIdAndGraviteAndDateSanctionAfter(
            Long studentId,
            GraviteSanction gravite,
            LocalDate date);
}
