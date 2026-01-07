package com.kalanblow.school_management.infrastructure.persistence.abscence;

import com.kalanblow.school_management.application.usecase.abscence.AbscenceSpecifications;
import com.kalanblow.school_management.model.anneescolaire.Abscence;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface JpaAbsenceRepository extends JpaRepository<Abscence, Long> , JpaSpecificationExecutor<Abscence> {

    List<Abscence> findByStudentId(Long studentId);

    List<Abscence> findByStudentIdAndPeriode_Id(Long studentId, Long periodeId);

    @Query("SELECT COUNT(a) FROM Abscence a WHERE a.studentId = :studentId " +
            "AND a.justifie = false " +
            "AND a.dateAbsence BETWEEN :startDate AND :endDate")
    Integer countAbsencesNonJustifieesByStudentAndPeriode(
            @Param("studentId") Long studentId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    @Query("SELECT COUNT(a) FROM Abscence a WHERE a.studentId = :studentId AND a.justifie = false")
    Long countByStudentIdAndJustifieFalse(@Param("studentId") Long studentId);

    // Recherche par date exacte
    List<Abscence> findByStudentIdAndDateAbsence(Long studentId, LocalDate dateAbsence);

    List<Abscence> findByStudentIdAndDateAbsenceBetween(
            Long studentId,
            LocalDate startDate,
            LocalDate endDate);

    // Recherche par période et statut de justification
    List<Abscence> findByStudentIdAndPeriodeIdAndJustifie(
            Long studentId,
            Long periodeId,
            boolean justifie);

    default List<Abscence> searchAbsences(SearchAbsenceCriteria criteria) {
        return findAll(AbscenceSpecifications.searchAbsences(
                criteria.studentId(),
                criteria.periodeId(),
                criteria.startDate(),
                criteria.endDate(),
                criteria.justifie(),
                criteria.motif()
        ));
    }

    boolean existsByStudentIdAndDateAbsence(Long studentId, LocalDate date);

    List<Abscence> findAll();

    Optional<Abscence> findById(Long id);

    Page<Abscence> findAll(Specification<Abscence> specification, Pageable pageable);

}
