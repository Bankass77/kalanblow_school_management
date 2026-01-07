package com.kalanblow.school_management.infrastructure.persistence.abscence;

import com.kalanblow.school_management.model.anneescolaire.Abscence;
import com.kalanblow.school_management.repository.AbsenceRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class AbsenceRepositoryJpa implements AbsenceRepository {

    private final JpaAbsenceRepository jpa;

    public AbsenceRepositoryJpa(JpaAbsenceRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public Integer countAbsencesNonJustifieesByStudentAndPeriode(long studentId, LocalDate debutAnnee, LocalDate finAnnee) {
        return jpa.countAbsencesNonJustifieesByStudentAndPeriode(studentId, debutAnnee, finAnnee);
    }


    @Override
    public Abscence save(Abscence abscence) {
        return jpa.save(abscence);
    }

    @Override
    public List<Abscence> findByStudent(Long studentId) {
        return jpa.findByStudentId(studentId);
    }

    @Override
    public long countByStudentStudentIdAndJustifieFalse(Long studentId) {

        return jpa.countByStudentIdAndJustifieFalse(studentId);
    }

    @Override
    public boolean existsByStudentAndDate(Long studentId, LocalDate date) {
        return jpa.existsByStudentIdAndDateAbsence(studentId, date);
    }

    @Override
    public void deleteById(Long absenceId) {
        if (findById(absenceId).isPresent()) {
            jpa.deleteById(absenceId);
        }

    }

    @Override
    public Optional<Abscence> findById(Long id) {
        return Optional.ofNullable(jpa.findById(id).orElseThrow(() -> new EntityNotFoundException("Abscence avec cet id " + id + " n'a pas été trouvé")));
    }

    @Override
    public Page<Abscence> search(Specification<Abscence> specification, Pageable pageable) {
        return jpa.findAll(specification, pageable);
    }

    @Override
    public List<Abscence> getAbsencesByStudentAndPeriode(Long studentId, Long periodeId) {
        return jpa.findByStudentIdAndPeriode_Id(studentId, periodeId);
    }

    @Override
    public List<Abscence> findByStudentStudentIdAndDateAbsence(Long studentId, LocalDate dateAbsence) {
        return jpa.findByStudentIdAndDateAbsence(studentId, dateAbsence);
    }

    @Override
    public List<Abscence> findByStudentStudentIdAndDateAbsenceBetween(Long studentId, LocalDate startDate, LocalDate endDate) {
        return jpa.findByStudentIdAndDateAbsenceBetween(studentId, startDate, endDate);
    }

    @Override
    public List<Abscence> findByStudentStudentIdAndPeriodeIdAndJustifie(Long studentId, Long periodeId, boolean justifie) {
        return jpa.findByStudentIdAndPeriodeIdAndJustifie(studentId, periodeId, justifie);
    }

    @Override
    public List<Abscence> searchAbsences(SearchAbsenceCriteria criteria) {
        return jpa.searchAbsences(criteria);
    }
}

