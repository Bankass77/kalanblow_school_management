package com.kalanblow.school_management.application.usecase.abscence;

import com.kalanblow.school_management.application.dto.note.StatistiquesAbsences;
import com.kalanblow.school_management.application.usecase.student.StudentPageCacheKey;
import com.kalanblow.school_management.model.anneescolaire.Abscence;
import com.kalanblow.school_management.service.AbscenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
@Transactional
public class GetAbsenceUseCase {

    private final AbscenceService abscenceService;

    @Transactional(readOnly = true)
    public Abscence getAbscenceById(long abscenceId) {

        return abscenceService.getAbscenceById(abscenceId);
    }

    public List<Abscence> getAbsencesByStudent(Long studentId) {
        return abscenceService.getAbsencesByStudent(studentId);
    }

    public List<Abscence> getAbsencesByStudentAndPeriode(Long studentId,
                                                         Long periodeId) {
        return abscenceService.getAbsencesByStudentAndPeriode(studentId, periodeId);
    }

    public StatistiquesAbsences getStatistiques(Long studentId) {
        return abscenceService.getStatistiquesByStudent(studentId);
    }

    public Abscence justifierAbsence(Long id, String motif) {

        return abscenceService.justifierAbscence(id, motif);
    }

    @Cacheable(value = "abscences-pages", key = "#key.toString()")
    public Page<Abscence> execute(AbscencePageCacheKey cacheKey, Pageable pageable, Specification<Abscence> spec) {
        spec = buildSpecification(cacheKey);
        pageable = PageRequest.of(cacheKey.page(), cacheKey.size());
        return abscenceService.search(spec, pageable);
    }

    private Specification<Abscence> buildSpecification(AbscencePageCacheKey cacheKey) {
        return Specification.where(AbscenceSpecifications.periodeIdEquals(cacheKey.periodId()))
                .and(AbscenceSpecifications.studentIdEquals(cacheKey.studentId()))
                .and(AbscenceSpecifications.dateAbscenceEquals(cacheKey.endDate()))
                .and(AbscenceSpecifications.motifContains(cacheKey.motif()))
                .and(AbscenceSpecifications.dateAbscenceBefore(cacheKey.endDate()))
                .and(AbscenceSpecifications.dateAbscenceAfter(cacheKey.startDate()))
                .and(AbscenceSpecifications.dateAbscenceBetween(cacheKey.startDate(), cacheKey.endDate()));
    }

    public Integer countAbscenceNonJustifieeByStudentIdAndDateBetween(long studentId, LocalDate debutAnnee, LocalDate finAnnee) {

        return abscenceService.countAbscenceNonJustifieeByStudentIdAndDateBetween(studentId, debutAnnee, finAnnee);
    }

    public long countByStudentStudentIdAndJustifieFalse(Long studentId) {
        return abscenceService.getAbsencesByStudent(studentId).stream().filter(abscence -> !abscence.isJustifie()).count();
    }
}
