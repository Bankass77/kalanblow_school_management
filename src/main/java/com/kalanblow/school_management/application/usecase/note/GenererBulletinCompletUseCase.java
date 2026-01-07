package com.kalanblow.school_management.application.usecase.note;

import com.kalanblow.school_management.model.anneescolaire.Bulletin;
import com.kalanblow.school_management.model.anneescolaire.PeriodeScolaire;
import com.kalanblow.school_management.model.student.Student;
import com.kalanblow.school_management.repository.AbsenceRepository;
import com.kalanblow.school_management.repository.BulletinRepository;
import com.kalanblow.school_management.repository.PeriodeScolaireRepository;
import com.kalanblow.school_management.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Transactional
public class GenererBulletinCompletUseCase {

    private final BulletinRepository bulletinRepository;
    private final AbsenceRepository absenceRepository;
    private final StudentRepository studentRepository;
    private final PeriodeScolaireRepository periodeRepository;

    public Bulletin execute(Long studentId, Long periodeId) {

        Student student = studentRepository.findByStudentId(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Élève introuvable"));

        PeriodeScolaire periode = periodeRepository.findById(periodeId)
                .orElseThrow(() -> new EntityNotFoundException("Période introuvable"));

        // Vérifier si un bulletin existe déjà
        Optional<Bulletin> existing = bulletinRepository.findByStudentStudentIdAndPeriodeId(studentId, periodeId);
        if (existing.isPresent()) {
            return existing.get();
        }

        Bulletin bulletin = new Bulletin();
        bulletin.setStudent(student);
        bulletin.setPeriode(periode);
        bulletin.setDateEdition(LocalDate.now());

        // Calcul absence
        long absencesNonJustifiees = absenceRepository.countByStudentStudentIdAndJustifieFalse(studentId);
        bulletin.setAppreciations("Absences non justifiées : " + absencesNonJustifiees);

        // La moyenne générale sera calculée plus tard (détails du bulletin)
        bulletin.setMoyenneGenerale(0.0);

        return bulletinRepository.save(bulletin);
    }

    private Double calculerMoyenneGenerale(Student eleve, Integer anneeScolaire) {

        // Récuperer toutes les périodes de l'année scolaire
        List<Long> periodesIds= periodeRepository.findByAnnee(Long.valueOf(anneeScolaire)).stream().map(PeriodeScolaire::getId).collect(Collectors.toList());

        // Récupérer tous les bulletins de l'étudiants pour ces périodes
        List<Bulletin> bulletins = bulletinRepository.findByStudentStudentIdAndPeriodeIdin(
                eleve.getStudentId(), periodesIds);

        if (bulletins.isEmpty()) return 0.0;

        return bulletins.stream()
                .mapToDouble(Bulletin::getMoyenneGenerale)
                .average()
                .orElse(0.0);
    }
}

