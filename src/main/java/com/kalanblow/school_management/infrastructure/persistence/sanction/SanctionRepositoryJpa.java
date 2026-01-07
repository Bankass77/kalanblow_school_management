package com.kalanblow.school_management.infrastructure.persistence.sanction;

import com.kalanblow.school_management.application.dto.inscription.Sanction;
import com.kalanblow.school_management.infrastructure.persistence.student.JpaStudentRepository;
import com.kalanblow.school_management.model.shared.enums.StatutSanction;
import com.kalanblow.school_management.repository.SanctionRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class SanctionRepositoryJpa implements SanctionRepository {

    private final JpaSanctionRepository jpaSanctionRepository;

    private final JpaStudentRepository jpaStudentRepository;

    public SanctionRepositoryJpa(JpaSanctionRepository jpaSanctionRepository, JpaStudentRepository jpaStudentRepository) {
        this.jpaSanctionRepository = jpaSanctionRepository;
        this.jpaStudentRepository = jpaStudentRepository;
    }

    @Override
    public Sanction save(Sanction sanction) {
        return jpaSanctionRepository.save(sanction);
    }

    @Override
    public Optional<Sanction> findById(Long id) {
        return jpaSanctionRepository.findById(id);
    }

    @Override
    public List<Sanction> findSanctionsNonLevees(Long studentId) {

        return jpaSanctionRepository.findAllById(Collections.singleton(studentId));
    }

    @Override
    public List<Sanction> findSanctionsGraves(Long studentId) {
        return jpaSanctionRepository.findAllById(Collections.singleton(studentId));
    }

    @Override
    public List<Sanction> findByStudentIdOrderByDateSanctionDesc(Long studentId) {
        return List.of();
    }

    @Override
    public List<Sanction> findByStudentIdAndStatut(Long studentId, StatutSanction statut) {
        return List.of();
    }

    @Override
    public int countByStudentIdAndStatutAndDateSanctionAfter(Long studentId, StatutSanction statut, LocalDate date) {
        return 0;
    }
}
