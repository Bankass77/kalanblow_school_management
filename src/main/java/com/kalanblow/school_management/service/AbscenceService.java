package com.kalanblow.school_management.service;

import com.kalanblow.school_management.application.dto.note.StatistiquesAbsences;
import com.kalanblow.school_management.application.usecase.abscence.AbscenceSpecifications;
import com.kalanblow.school_management.infrastructure.persistence.abscence.SearchAbsenceCriteria;
import com.kalanblow.school_management.model.anneescolaire.Abscence;
import com.kalanblow.school_management.model.anneescolaire.PeriodeScolaire;
import com.kalanblow.school_management.model.student.Student;
import com.kalanblow.school_management.repository.AbsenceRepository;
import com.kalanblow.school_management.repository.PeriodeScolaireRepository;
import com.kalanblow.school_management.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AbscenceService {
    private final AbsenceRepository absenceRepository;
    private final StudentRepository studentRepository;
    private final PeriodeScolaireRepository periodeRepository;

    public Abscence createAbsence(Long studentId, Long periodId, Abscence abscence) {
        Student student = studentRepository.findByStudentId(studentId).orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + studentId));
        PeriodeScolaire periodeScolaire = periodeRepository.findById(periodId).orElseThrow(() -> new EntityNotFoundException("Periode  not found with id: " + periodId));
        if (absenceRepository.existsByStudentAndDate(studentId, abscence.getDateAbsence())) {
            throw new IllegalArgumentException("Une abscence existe déjà pour cet étudiant ou élève à cette date : " + abscence.getDateAbsence());
        }
        abscence = new Abscence();
        abscence.setDateAbsence(abscence.getDateAbsence());
        abscence.setPeriode(periodeScolaire);
        abscence.setMotif(abscence.getMotif());
        abscence.setStudentId(student.getStudentId());
        abscence.setJustifie(abscence.isJustifie());
        return absenceRepository.save(abscence);
    }

    public Abscence updateAbscence(Long id, Abscence abscence) {
        abscence = absenceRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("absence not found with id: " + id));
        abscence.setJustifie(abscence.isJustifie());
        abscence.setDateAbsence(abscence.getDateAbsence());
        abscence.setMotif(abscence.getMotif());
        return abscence;
    }

    public void deleteAbscence(Long abscenceId) {
        Abscence abscence = absenceRepository.findById(abscenceId).orElseThrow(() -> new EntityNotFoundException("Absence not found with id: " + abscenceId));
        absenceRepository.deleteById(abscence.getId());
    }

    public Abscence getAbscenceById(Long id) {
        return absenceRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Abscence not found with id: " + id));
    }

    public Page<Abscence> search(Specification<Abscence> spec, Pageable pageable) {
        Specification<Abscence> specification = spec == null ? Specification.where((root, query, criteriaBuilder) -> null) : spec;
        return absenceRepository.search(specification, pageable);
    }

    public Integer countAbscenceNonJustifieeByStudentIdAndDateBetween(long studentId, LocalDate debutAnnee, LocalDate finAnnee) {
        return absenceRepository.countAbsencesNonJustifieesByStudentAndPeriode(studentId, debutAnnee, finAnnee);
    }

    public long countByStudentStudentIdAndJustifieFalse(Long studentId) {
        return absenceRepository.countByStudentStudentIdAndJustifieFalse(studentId);
    }

    public Map<Long, Integer> getAbscenceCountByStudent(List<Long> studentIs) {
        Map<Long, Integer> result = new HashMap<>();
        for (Long studentId : studentIs) {
            int count = absenceRepository.findByStudent(studentId).size();
            result.put(studentId, count);
        }
        return result;
    }

    public Abscence justifierAbscence(Long abscenceId, String motif) {
        Abscence abscence = getAbscenceById(abscenceId);
        abscence.setJustifie(true);
        abscence.setMotif(motif != null ? motif : abscence.getMotif());
        return absenceRepository.save(abscence);
    }

    public StatistiquesAbsences getStatistiquesByStudent(Long studentId) {

        List<Abscence> absences = absenceRepository.findByStudent(studentId);
        long[] counts = absences.stream()
                .collect(
                        () -> new long[2], // [justifiées, nonJustifiées]
                        (acc, absence) -> acc[absence.isJustifie() ? 0 : 1]++,
                        (a, b) -> {
                            a[0] += b[0];
                            a[1] += b[1];
                        }
                );

        long justifiees = counts[0];
        long nonJustifiees = counts[1];
        long total = justifiees + nonJustifiees;

        return new StatistiquesAbsences(studentId, total, justifiees, nonJustifiees);
    }

    public List<Abscence> getAbsencesByStudent(Long studentId) {

        return absenceRepository.findByStudent(studentId);
    }

    public List<Abscence> getAbsencesByStudentAndPeriode(Long studentId,
                                                         Long periodeId) {

        return absenceRepository.getAbsencesByStudentAndPeriode(studentId, periodeId);
    }

    public Page<Abscence> searchAbsences(SearchAbsenceCriteria criteria, Pageable pageable) {

        Specification<Abscence> spec = Specification.where((Specification<Abscence>) null);

        if (criteria.studentId() != null) {

            spec = spec.and(AbscenceSpecifications.studentIdEquals(criteria.studentId()));
        }

        if (criteria.periodeId() != null) {
            spec = spec.and(AbscenceSpecifications.periodeIdEquals(criteria.periodeId()));
        }

        if (criteria.startDate() != null || criteria.endDate() != null) {
            spec = spec.and(AbscenceSpecifications.dateAbscenceBetween(
                    criteria.startDate(), criteria.endDate()
            ));
        }

        if (criteria.justifie() != null) {
            spec = spec.and(AbscenceSpecifications.justifieEquals(criteria.justifie()));
        }

        if (StringUtils.hasText(criteria.motif())) {
            spec = spec.and(AbscenceSpecifications.motifContains(criteria.motif()));
        }

        return absenceRepository.search(spec,pageable);
    }
}
