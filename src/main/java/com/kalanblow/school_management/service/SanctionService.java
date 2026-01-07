package com.kalanblow.school_management.service;

import com.kalanblow.school_management.application.dto.inscription.Sanction;
import com.kalanblow.school_management.application.dto.sanction.DecisionConseilResponse;
import com.kalanblow.school_management.application.dto.sanction.SanctionCommand;
import com.kalanblow.school_management.application.dto.sanction.SanctionResume;
import com.kalanblow.school_management.model.shared.enums.GraviteSanction;
import com.kalanblow.school_management.model.shared.enums.StatutSanction;
import com.kalanblow.school_management.model.shared.enums.TypeSanction;
import com.kalanblow.school_management.model.student.Student;
import com.kalanblow.school_management.repository.SanctionRepository;
import com.kalanblow.school_management.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class SanctionService {

    private final SanctionRepository sanctionRepository;
    private final StudentRepository studentRepository;
    private final DecisionConseilService decisionConseilService;

    public Sanction creerSanction(SanctionCommand command) {
        Student student = studentRepository.findByStudentId(command.studentId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Student not found with id: " + command.studentId()
                ));

        var sanction = new Sanction();
        sanction.setStudentId(student.getStudentId());
        sanction.setDateSanction(
                command.dateSanction() != null ? command.dateSanction() : LocalDate.now()
        );

        sanction.setAuteurSanctionId(command.auteurSanction());
        sanction.setAppliquee(command.appliquee());
        sanction.setContestee(command.contestee());
        sanction.setGravite(GraviteSanction.valueOf(command.gravite()));
        sanction.setDescription(command.description());
        sanction.setMotif(command.motif());
        sanction.setDateFin(command.dateFin());
        sanction.setDateDebut(command.dateDebut());
        sanction.setDateLevee(command.dateLevee());
        sanction.setType(TypeSanction.valueOf(command.typeSanction()));
        sanction.setDateContestation(command.dateContestation());
        sanction.setNotifierParents(command.notifierParents());
        sanction.setSignataireId(command.signataire());
        sanction.setRapportIncident(command.description());
        sanction.setStatut(StatutSanction.EN_ATTENTE);

        return sanctionRepository.save(sanction);
    }


    public Sanction getSanction(Long sanctionId) {
        return sanctionRepository.findById(sanctionId)
                .orElseThrow(() -> new EntityNotFoundException("Sanction not found with id: " + sanctionId));
    }

    public List<Sanction> getSanctionsByStudent(Long studentId) {
        return sanctionRepository.findByStudentIdOrderByDateSanctionDesc(studentId);
    }

    public Sanction appliquerSanction(Long sanctionId) {
        Sanction sanction = getSanction(sanctionId);

        if (sanction.getStatut() != StatutSanction.EN_ATTENTE) {
            throw new IllegalStateException("Cette sanction a déjà été traitée");
        }

        sanction.setStatut(StatutSanction.APPLIQUEE);
        sanction.setDateDebut(LocalDate.now());

        // Ajouter la logique métier pour l'application de la sanction
        appliquerConsequences(sanction);

        return sanctionRepository.save(sanction);
    }

    public DecisionConseilResponse evaluerSanctionsPourDecision(Long studentId) {
        List<Sanction> sanctions = getSanctionsByStudent(studentId);

        // Filtrer les sanctions significatives
        List<Sanction> sanctionsSignificatives = sanctions.stream()
                .filter(s -> s.getStatut() == StatutSanction.APPLIQUEE)
                .filter(s -> s.getDateSanction().isAfter(LocalDate.now().minusMonths(6)))
                .collect(Collectors.toList());

        // Calculer la gravité totale
        int graviteTotale = sanctionsSignificatives.stream()
                .mapToInt(s -> s.getGravite().getNiveau())
                .sum();

        // Utiliser la méthode correcte de DecisionConseilService
        // Créer un Student temporaire pour l'évaluation
        Student student = studentRepository.findByStudentId(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        DecisionConseilService.DecisionConseil decision =
                decisionConseilService.evaluerEleve(student, LocalDate.now().getYear());

        // Convertir les sanctions en résumés
        List<SanctionResume> sanctionResumes = sanctionsSignificatives.stream()
                .map(this::toSanctionResume)
                .collect(Collectors.toList());

        return new DecisionConseilResponse(
                studentId,
                sanctionsSignificatives.size(),
                graviteTotale,
                decision.name(),
                sanctionResumes
        );
    }

    private void appliquerConsequences(Sanction sanction) {
        switch (sanction.getType()) {
            case AVERTISSEMENT_ECRIT:
                // Envoyer notification aux parents
                notifierParents(sanction);
                break;
            case AVERTISSEMENT_ORAL:
                // Envoyer notification aux parents
                notifierParents(sanction);
                break;
            case RETENUE:
                // Programmer une retenue
                programmerRetenue(sanction);
                break;
            case EXCLUSION_TEMPORAIRE:
                // Mettre à jour la disponibilité de l'étudiant
                mettreAJourDisponibilite(sanction, false);
                break;
            case EXCLUSION_DEFINITIVE:
                // Procédure d'exclusion
                initierProcedureExclusion(sanction);
                break;
        }
    }

    private SanctionResume toSanctionResume(Sanction sanction) {
        return new SanctionResume(
                sanction.getId(),
                sanction.getType(),
                sanction.getDateSanction(),
                sanction.getGravite().getNiveau(),
                sanction.getMotif()
        );
    }

    // Méthodes auxiliaires (à implémenter selon vos besoins)
    private void notifierParents(Sanction sanction) {
        // Implémentation de la notification
        // Exemple : envoyer un email ou SMS aux parents
        System.out.println("Notification envoyée aux parents pour la sanction : " + sanction.getId());
    }

    private void programmerRetenue(Sanction sanction) {
        // Programmation d'une retenue
        // Exemple : créer un événement dans le calendrier
        System.out.println("Retenue programmée pour le " + sanction.getDateDebut());
    }

    private void mettreAJourDisponibilite(Sanction sanction, boolean disponible) {
        studentRepository.findByStudentId(sanction.getStudentId())
                .ifPresent(student -> {
                    student.setDisponible(disponible);
                    studentRepository.save(student);
                });
    }


    private void initierProcedureExclusion(Sanction sanction) {
        // Initier procédure d'exclusion
        // Exemple : notifier l'administration, préparer les documents
        log.debug("Procédure d'exclusion initiée pour : " +
                sanction.getStudentId());
    }

    public Sanction save(Sanction sanction) {

        return sanctionRepository.save(sanction);
    }

    public Optional<Sanction> findById(Long id) {

        return sanctionRepository.findById(id);
    }

    public List<Sanction> findSanctionsNonLevees(Long studentId) {
        return sanctionRepository.findSanctionsNonLevees(studentId);
    }

    public List<Sanction> findSanctionsGraves(Long studentId) {
        return sanctionRepository.findSanctionsGraves(studentId);
    }

    public List<Sanction> findByStudentIdOrderByDateSanctionDesc(Long studentId) {

        return sanctionRepository.findByStudentIdOrderByDateSanctionDesc(studentId);
    }

    public List<Sanction> findByStudentIdAndStatut(Long studentId, StatutSanction statut) {
        return sanctionRepository.findByStudentIdAndStatut(studentId, statut);
    }

    public int countByStudentIdAndStatutAndDateSanctionAfter(
            Long studentId,
            StatutSanction statut,
            LocalDate date
    ) {
        return sanctionRepository.countByStudentIdAndStatutAndDateSanctionAfter(studentId, statut, date);
    }
}