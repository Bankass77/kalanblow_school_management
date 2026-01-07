package com.kalanblow.school_management.service;

import com.kalanblow.school_management.application.dto.inscription.DemandeOrientation;
import com.kalanblow.school_management.application.dto.inscription.ProcesVerbalConseil;
import com.kalanblow.school_management.application.dto.inscription.Sanction;
import com.kalanblow.school_management.model.anneescolaire.AnneeScolaire;
import com.kalanblow.school_management.model.classe.SchoolClass;
import com.kalanblow.school_management.model.inscription.DecisionConseil;
import com.kalanblow.school_management.model.inscription.Inscription;
import com.kalanblow.school_management.model.inscription.StatutInscription;
import com.kalanblow.school_management.model.inscription.TypeAdmission;
import com.kalanblow.school_management.model.matiere.MatiereResultat;
import com.kalanblow.school_management.model.notification.NotificationMessage;
import com.kalanblow.school_management.model.shared.enums.EtatEleve;
import com.kalanblow.school_management.model.shared.enums.GraviteSanction;
import com.kalanblow.school_management.model.shared.enums.StatutDemande;
import com.kalanblow.school_management.model.shared.enums.TypeNotification;
import com.kalanblow.school_management.model.student.Student;
import com.kalanblow.school_management.repository.AnneeScolaireRepository;
import com.kalanblow.school_management.repository.InscriptionRepository;
import com.kalanblow.school_management.repository.NoteRepository;
import com.kalanblow.school_management.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class FinAnneeService {

    private final InscriptionRepository inscriptionRepository;
    private final AnneeScolaireRepository anneeScolaireRepository;
    private final NoteRepository noteRepository;
    private final StudentRepository studentRepository;
    private final BulletinService bulletinService;
    private final NotificationService notificationService;

    // Seuils de décision (configurables)
    private static final double SEUIL_ADMISSION = 10.0;
    private static final double SEUIL_REDOUBLEMENT = 8.0;
    private static final int SEUIL_ABSENCES_NON_JUSTIFIEES = 20;
    private static final int SEUIL_RETARDS = 30;

    public FinAnneeService(InscriptionRepository inscriptionRepository,
                           AnneeScolaireRepository anneeScolaireRepository,
                           NoteRepository noteRepository,
                           StudentRepository studentRepository,
                           BulletinService bulletinService,
                           NotificationService notificationService) {
        this.inscriptionRepository = inscriptionRepository;
        this.anneeScolaireRepository = anneeScolaireRepository;
        this.noteRepository = noteRepository;
        this.studentRepository = studentRepository;
        this.bulletinService = bulletinService;
        this.notificationService = notificationService;
    }

    /**
     * Traite la fin d'année pour tous les élèves d'une année scolaire
     */
    public void traiterFinAnnee(Long anneeScolaireId) {
        AnneeScolaire annee = anneeScolaireRepository.findById(anneeScolaireId)
                .orElseThrow(() -> new EntityNotFoundException("Année scolaire non trouvée"));

        // 1. Récupérer toutes les inscriptions de l'année
        List<Inscription> inscriptions = inscriptionRepository
                .findByAnneeScolaireAndStatut(annee, StatutInscription.EN_COURS);

        Map<DecisionConseil, List<Inscription>> decisionsParType = new HashMap<>();

        // 2. Évaluer chaque élève et appliquer les décisions
        for (Inscription inscription : inscriptions) {
            Student student = inscription.getStudent();

            // Évaluer l'élève et prendre une décision
            DecisionConseil decision = evaluerEleve(student, inscription, annee);

            // Stocker pour statistiques
            decisionsParType
                    .computeIfAbsent(decision, k -> new ArrayList<>())
                    .add(inscription);

            // Mettre à jour l'inscription avec la décision
            inscription.setDecisionConseil(decision.getLibelle());
            inscription.setDateDecision(LocalDate.now());

            // Appliquer le statut en fonction de la décision
            switch (decision) {
                case ADMISSION:
                case ADMISSION_SOUS_CONDITION:
                    inscription.setStatut(StatutInscription.ADMIS);
                    preparerPassageClasseSuperieure(student, inscription);
                    break;

                case REDOUBLEMENT:
                case REDOUBLEMENT_AVEC_DEROGATION:
                    inscription.setStatut(StatutInscription.REDOUBLANT);
                    preparerRedoublement(student, inscription);
                    break;

                case EXCLUSION_TEMPORAIRE:
                case EXCLUSION_DEFINITIVE:
                    inscription.setStatut(StatutInscription.EXCLU);
                    gererExclusion(student, inscription, decision);
                    break;

                case ORIENTATION:
                    gererOrientation(student, inscription);
                    break;

                default:
                    inscription.setStatut(StatutInscription.EN_ATTENTE);
            }

            // Sauvegarder l'inscription
            inscriptionRepository.save(inscription);

            // Générer le bulletin de fin d'année
            bulletinService.genererBulletinFinAnnee(student.getStudentId(), annee.getAnneeScolaireId(), decision);

            // Notifier les parents
            notifierDecisionAuxParents(student, inscription, decision);
        }

        // 3. Générer le procès-verbal du conseil de classe
        genererProcesVerbalConseil(annee, decisionsParType);

        // 4. Clôturer l'année scolaire
        annee.setEstActive(false);
        annee.setDateCloture(LocalDate.now());
        anneeScolaireRepository.save(annee);

        // 5. Préparer la nouvelle année scolaire
        preparerNouvelleAnnee(annee);
    }

    /**
     * Évalue un élève pour déterminer la décision du conseil
     */
    private DecisionConseil evaluerEleve(Student student, Inscription inscription, AnneeScolaire annee) {
        // Récupérer les statistiques de l'élève
        StatistiquesEleve stats = calculerStatistiquesEleve(student.getStudentId(), annee.getAnneeScolaireId());

        // Règles de décision basées sur les statistiques
        if (stats.getMoyenneGenerale() >= SEUIL_ADMISSION) {
            // Élève admissible
            if (stats.getAbsencesNonJustifiees() > SEUIL_ABSENCES_NON_JUSTIFIEES ||
                    stats.getRetards() > SEUIL_RETARDS) {
                return DecisionConseil.ADMISSION_SOUS_CONDITION;
            }
            return DecisionConseil.ADMISSION;

        } else if (stats.getMoyenneGenerale() >= SEUIL_REDOUBLEMENT) {
            // Cas limite - besoin d'examen complémentaire
            if (stats.getMatieresEnEchec().size() > 3) {
                return DecisionConseil.REDOUBLEMENT;
            }
            return DecisionConseil.RATTRAPAGE;

        } else if (stats.getMoyenneGenerale() < SEUIL_REDOUBLEMENT) {
            // Redoublement probable
            if (student.getEtat() == EtatEleve.REDOUBLANT) {
                // Déjà redoublant
                if (stats.getMatieresEnEchec().size() > 5) {
                    return DecisionConseil.EXCLUSION_TEMPORAIRE;
                }
                return DecisionConseil.REDOUBLEMENT_AVEC_DEROGATION;
            }
            return DecisionConseil.REDOUBLEMENT;

        } else if (stats.getAbsencesNonJustifiees() > SEUIL_ABSENCES_NON_JUSTIFIEES * 2) {
            // Absentéisme grave
            return DecisionConseil.EXCLUSION_TEMPORAIRE;

        } else if (student.getSanctions().stream()
                .anyMatch(s -> s.getGravite() == GraviteSanction.GRAVE)) {
            // Sanctions graves
            return DecisionConseil.EXCLUSION_DEFINITIVE;
        }

        // Par défaut, ajournement pour examen complémentaire
        return DecisionConseil.AJOURNEMENT;
    }

    /**
     * Calcule les statistiques d'un élève pour l'année
     */
    private StatistiquesEleve calculerStatistiquesEleve(Long studentId, Long anneeId) {
        StatistiquesEleve stats = new StatistiquesEleve();

        // Calculer la moyenne générale
        Double moyenneGenerale = noteRepository.calculerMoyenneGenerale(studentId, anneeId);
        stats.setMoyenneGenerale(moyenneGenerale != null ? moyenneGenerale : 0.0);

        // Récupérer les matières en échec (< 10/20)
        List<MatiereResultat> matieresResultats = noteRepository.getResultatsParMatiere(studentId, anneeId);
        List<String> matieresEnEchec = matieresResultats.stream()
                .filter(mr -> mr.getMoyenne() < 10.0)
                .map(MatiereResultat::getMatiere)
                .collect(Collectors.toList());
        stats.setMatieresEnEchec(matieresEnEchec);

        // Compter les absences et retards
        long absences = inscriptionRepository.countAbsencesNonJustifiees(studentId, anneeId);
        long retards = inscriptionRepository.countRetards(studentId, anneeId);
        stats.setAbsencesNonJustifiees((int) absences);
        stats.setRetards((int) retards);

        return stats;
    }

    /**
     * Prépare le passage en classe supérieure
     */
    private void preparerPassageClasseSuperieure(Student student, Inscription inscription) {

    }

    private String determinerNiveauSuivant(Object niveau) {
        return "";
    }

    private Optional<SchoolClass> findClasseDisponible(String niveauSuivant, @Min(value = 2000, message = "L'année doit être >= 2000") Integer prochaineAnnee) {
        return null;
    }

    /**
     * Prépare le redoublement d'un élève
     */
    private void preparerRedoublement(Student student, Inscription inscription) {
        // Marquer l'élève comme redoublant
        student.setEtat(EtatEleve.REDOUBLANT);
        studentRepository.save(student);

        // Créer une nouvelle inscription pour redoublement
        Inscription nouvelleInscription = new Inscription();
        nouvelleInscription.setStudent(student);
        nouvelleInscription.setSchoolClass(inscription.getSchoolClass());
        nouvelleInscription.setAnneeScolaire(inscription.getAnneeScolaire());
        nouvelleInscription.setStatut(StatutInscription.REDOUBLANT);
        nouvelleInscription.setTypeAdmission(TypeAdmission.REDOUBLEMENT);
        nouvelleInscription.setEstRedoublant(true);
        nouvelleInscription.setClassePrecedente(inscription.getSchoolClass().getName());
        nouvelleInscription.setDateDecision(LocalDate.now());

        inscriptionRepository.save(nouvelleInscription);
    }

    /**
     * Gère l'exclusion d'un élève
     */
    private void gererExclusion(Student student, Inscription inscription, DecisionConseil decision) {
        student.setEtat(EtatEleve.EXCLU);
        studentRepository.save(student);

        // Si exclusion définitive, archiver l'élève
        if (decision == DecisionConseil.EXCLUSION_DEFINITIVE) {
            student.setDateArchivage(LocalDate.now());
        }

        // Notifier les autorités académiques si nécessaire
        notificationService.notifierExclusion(student, decision);
    }

    /**
     * Gère l'orientation vers une autre filière
     */
    private void gererOrientation(Student student, Inscription inscription) {
        // Logique d'orientation vers une autre filière
        // À implémenter selon les besoins spécifiques
        student.setEtat(EtatEleve.EN_ORIENTATION);
        studentRepository.save(student);

        // Créer une demande d'orientation
        DemandeOrientation demande = new DemandeOrientation();
        demande.setStudent(student);
        demande.setDateDemande(LocalDate.now());
        demande.setStatut(StatutDemande.EN_ATTENTE);

    }

    /**
     * Génère le procès-verbal du conseil de classe
     */
    private void genererProcesVerbalConseil(AnneeScolaire annee,
                                            Map<DecisionConseil, List<Inscription>> decisions) {
        ProcesVerbalConseil procesVerbal = new ProcesVerbalConseil();
        procesVerbal.setAnneeScolaire(annee);
        procesVerbal.setDateConseil(LocalDate.now());
        procesVerbal.setDecisions(decisions);

        // Calculer les statistiques
        Map<String, Object> statistiques = new HashMap<>();
        decisions.forEach((decision, inscriptions) -> {
            statistiques.put(decision.name(), inscriptions.size());
        });
        procesVerbal.setStatistiques(statistiques);

        // Générer le document PDF (via un service de génération)
        byte[] document = genererDocumentProcesVerbal(procesVerbal);
        procesVerbal.setDocumentPdf(document);

        // Sauvegarder le procès-verbal
        // procesVerbalRepository.save(procesVerbal);
    }

    private byte[] genererDocumentProcesVerbal(ProcesVerbalConseil procesVerbal) {
        return null;
    }

    /**
     * Prépare la nouvelle année scolaire
     */
    private void preparerNouvelleAnnee(AnneeScolaire anneePrecedente) {
        AnneeScolaire nouvelleAnnee = new AnneeScolaire();
        nouvelleAnnee.setAnneeDebut(anneePrecedente.getAnneeFin() + 1);
        nouvelleAnnee.setAnneeFin(anneePrecedente.getAnneeFin() + 2);
        nouvelleAnnee.setEstActive(true);
        nouvelleAnnee.setDateDebutInscription(LocalDate.now().plusDays(30));
        nouvelleAnnee.setDateFinInscription(LocalDate.now().plusDays(60));

        anneeScolaireRepository.save(nouvelleAnnee);

        // Dupliquer les structures (classes, etc.) de l'année précédente
        dupliquerStructuresAnnee(anneePrecedente, nouvelleAnnee);
    }

    private void dupliquerStructuresAnnee(AnneeScolaire anneePrecedente, AnneeScolaire nouvelleAnnee) {
    }

    /**
     * Notifie la décision aux parents
     */
    private void notifierDecisionAuxParents(Student student, Inscription inscription,
                                            DecisionConseil decision) {
        NotificationMessage message = new NotificationMessage();
        message.setStudent(student);
        message.setTitre("Décision du conseil de classe");
        message.setContenu(String.format(
                "Le conseil de classe a pris la décision suivante pour votre enfant %s : %s",
                student.getUser().getUserName().getFullName(),
                decision.getLibelle()
        ));
        message.setType(TypeNotification.DECISION_CONSEIL);
        message.setImportant(true);

        notificationService.envoyerNotificationAuxParents(student.getStudentId(), message);
    }

    // Classes internes pour les statistiques
    @Data
    private static class StatistiquesEleve {
        private double moyenneGenerale;
        private List<String> matieresEnEchec;
        private int absencesNonJustifiees;
        private int retards;
        private List<Sanction> sanctionsGraves;
    }

}