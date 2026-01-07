package com.kalanblow.school_management.repository;

import com.kalanblow.school_management.application.usecase.abscence.AbscenceSpecifications;
import com.kalanblow.school_management.infrastructure.persistence.abscence.SearchAbsenceCriteria;
import com.kalanblow.school_management.model.anneescolaire.Abscence;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AbsenceRepository {
    Integer countAbsencesNonJustifieesByStudentAndPeriode(long studentId, LocalDate debutAnnee, LocalDate finAnnee);
    Abscence save(Abscence abscence);
    List<Abscence> findByStudent(Long studentId);
    long countByStudentStudentIdAndJustifieFalse(Long studentId);
    boolean existsByStudentAndDate(Long studentId, LocalDate date);
    void deleteById(Long absence);
    Optional<Abscence> findById(Long id);
    Page<Abscence> search(Specification<Abscence> specification, Pageable pageable);
    List<Abscence> getAbsencesByStudentAndPeriode(Long studentId,
                                                  Long periodeId);
    // Recherche par date exacte
    List<Abscence> findByStudentStudentIdAndDateAbsence(Long studentId, LocalDate dateAbsence);

    List<Abscence> findByStudentStudentIdAndDateAbsenceBetween(
            Long studentId,
            LocalDate startDate,
            LocalDate endDate);

    // Recherche par période et statut de justification
    List<Abscence> findByStudentStudentIdAndPeriodeIdAndJustifie(
            Long studentId,
            Long periodeId,
            boolean justifie);

   List<Abscence> searchAbsences(SearchAbsenceCriteria criteria) ;
}
