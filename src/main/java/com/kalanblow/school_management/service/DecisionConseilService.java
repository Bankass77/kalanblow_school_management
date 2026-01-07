package com.kalanblow.school_management.service;

import com.kalanblow.school_management.application.dto.inscription.Sanction;
import com.kalanblow.school_management.model.anneescolaire.Bulletin;
import com.kalanblow.school_management.model.shared.enums.GraviteSanction;
import com.kalanblow.school_management.model.shared.enums.StatutSanction;
import com.kalanblow.school_management.model.shared.enums.TypeSanction;
import com.kalanblow.school_management.model.student.Student;
import com.kalanblow.school_management.repository.AbsenceRepository;
import com.kalanblow.school_management.repository.BulletinRepository;
import com.kalanblow.school_management.repository.SanctionRepository;
import com.kalanblow.school_management.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DecisionConseilService {

    private final SanctionRepository sanctionRepository;
    private final StudentRepository studentRepository;
    private final BulletinRepository bulletinRepository;
    private final AbsenceRepository absenceRepository;

    public enum DecisionConseil {
        ADMISSION("Admission en classe supérieure"),
        REDOUBLEMENT("Redoublement"),
        EXCLUSION_TEMPORAIRE("Exclusion temporaire"),
        EXCLUSION_DEFINITIVE("Exclusion définitive"),
        CONSEIL_DE_DISCIPLINE("Saisine du conseil de discipline"),
        MAINTIEN_SOUS_CONDITION("Maintien sous conditions"),
        AUTRE("Autre décision");

        private final String libelle;

        DecisionConseil(String libelle) {
            this.libelle = libelle;
        }

        public String getLibelle() {
            return libelle;
        }
    }

    public DecisionConseil evaluerEleve(Long studentId, Integer anneeScolaire) {
        Student student = studentRepository.findByStudentId(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));
        return evaluerEleve(student, anneeScolaire);
    }

    public DecisionConseil evaluerEleve(Student eleve, Integer anneeScolaire) {
        // Vérifier les résultats académiques
        Double moyenne = calculerMoyenneGenerale(eleve, anneeScolaire);
        Integer absences = compterAbsencesNonJustifiees(eleve, anneeScolaire);

        // Vérifier les sanctions
        List<Sanction> sanctions = getSanctionsNonLevees(eleve.getStudentId(), anneeScolaire);

        // Logique de décision
        if (doitExclureDefinitivement(sanctions)) {
            return DecisionConseil.EXCLUSION_DEFINITIVE;
        }

        if (doitExclureTemporairement(sanctions)) {
            return DecisionConseil.EXCLUSION_TEMPORAIRE;
        }

        if (doitRedoubler(moyenne, absences, sanctions)) {
            return DecisionConseil.REDOUBLEMENT;
        }

        if (peutAdmettreAvecConditions(moyenne, absences)) {
            return DecisionConseil.MAINTIEN_SOUS_CONDITION;
        }

        if (doitSaisirConseilDiscipline(sanctions)) {
            return DecisionConseil.CONSEIL_DE_DISCIPLINE;
        }

        return DecisionConseil.ADMISSION;
    }

    private boolean doitExclureDefinitivement(List<Sanction> sanctions) {
        long sanctionsGraves = sanctions.stream()
                .filter(s -> s.getGravite().peutConduireAExclusion())
                .count();

        return sanctionsGraves >= 2 ||
                sanctions.stream().anyMatch(s ->
                        s.getType() == TypeSanction.EXCLUSION_DEFINITIVE);
    }

    private boolean doitExclureTemporairement(List<Sanction> sanctions) {
        return sanctions.stream()
                .anyMatch(s -> s.getType() == TypeSanction.EXCLUSION_TEMPORAIRE &&
                        s.getGravite().getNiveau() >= GraviteSanction.MOYENNE.getNiveau());
    }

    private boolean doitRedoubler(Double moyenne, Integer absences, List<Sanction> sanctions) {
        if (moyenne < 8.0) return true;
        if (absences > 30) return true;
        if (sanctions.size() > 5) return true;

        // Vérifier si l'étudiant a plus de 3 sanctions graves
        long sanctionsGraves = sanctions.stream()
                .filter(s -> s.getGravite().getNiveau() >= GraviteSanction.GRAVE.getNiveau())
                .count();

        return sanctionsGraves >= 3;
    }

    private boolean peutAdmettreAvecConditions(Double moyenne, Integer absences) {
        return moyenne >= 8.0 && moyenne < 10.0 && absences <= 15;
    }

    private boolean doitSaisirConseilDiscipline(List<Sanction> sanctions) {
        // Saisir le conseil de discipline si 3 sanctions moyennes ou plus
        long sanctionsMoyennes = sanctions.stream()
                .filter(s -> s.getGravite() == GraviteSanction.MOYENNE)
                .count();

        return sanctionsMoyennes >= 3;
    }

    private Double calculerMoyenneGenerale(Student eleve, Integer periodId) {

        Optional<Bulletin> bulletins = bulletinRepository.findByStudentStudentIdAndPeriodeId(
                eleve.getStudentId(), Long.valueOf(periodId));

        if (bulletins.isEmpty()) return 0.0;

        return bulletins.stream()
                .mapToDouble(Bulletin::getMoyenneGenerale)
                .average()
                .orElse(0.0);
    }

    private Integer compterAbsencesNonJustifiees(Student eleve, Integer anneeScolaire) {
        LocalDate debutAnnee = LocalDate.of(anneeScolaire, 9, 1);
        LocalDate finAnnee = LocalDate.of(anneeScolaire + 1, 7, 31);

        return absenceRepository.countAbsencesNonJustifieesByStudentAndPeriode(
                eleve.getStudentId(), debutAnnee, finAnnee);
    }

    private List<Sanction> getSanctionsNonLevees(Long studentId, Integer anneeScolaire) {
        LocalDate debutAnnee = LocalDate.of(anneeScolaire, 9, 1);
        LocalDate finAnnee = LocalDate.of(anneeScolaire + 1, 7, 31);

        return sanctionRepository.findByStudentIdAndStatut(
                        studentId, StatutSanction.APPLIQUEE).stream()
                .filter(s -> !s.estLevee() || s.getDateLevee() == null)
                .filter(s -> s.getDateSanction().isAfter(debutAnnee) &&
                        s.getDateSanction().isBefore(finAnnee))
                .collect(Collectors.toList());
    }
}
