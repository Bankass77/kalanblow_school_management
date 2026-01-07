package com.kalanblow.school_management.service;

import com.kalanblow.school_management.model.anneescolaire.AnneeScolaire;
import com.kalanblow.school_management.model.classe.SchoolClass;
import com.kalanblow.school_management.model.inscription.Inscription;
import com.kalanblow.school_management.model.inscription.StatutInscription;
import com.kalanblow.school_management.model.inscription.TypeAdmission;
import com.kalanblow.school_management.model.student.Student;
import com.kalanblow.school_management.repository.InscriptionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class InscriptionService {

    private final InscriptionRepository inscriptionRepository;

    public InscriptionService(InscriptionRepository inscriptionRepository) {
        this.inscriptionRepository = inscriptionRepository;
    }

    public Inscription inscrireEleve(Student student, AnneeScolaire annee,
                                     SchoolClass classe, TypeAdmission type) {

        // 1. Vérifier si l'élève est déjà inscrit cette année
        boolean dejaInscrit = student.getInscriptions().stream()
                .anyMatch(i -> i.getAnneeScolaire().equals(annee) &&
                        i.getStatut() != StatutInscription.EXCLU);

        if (dejaInscrit) {
            throw new IllegalStateException("Élève déjà inscrit pour cette année");
        }

        // 2. Vérifier la capacité de la classe
        if (classe.getEffectifActuel() >= classe.getCapacity()) {
            throw new IllegalStateException("Classe pleine");
        }

        // 3. Déterminer si c'est un redoublement
        boolean estRedoublant = determinerRedoublement(student, classe);

        // 4. Créer l'inscription
        Inscription inscription = new Inscription();
        inscription.setStudent(student);
        inscription.setAnneeScolaire(annee);
        inscription.setSchoolClass(classe);
        inscription.setTypeAdmission(type);
        inscription.setEstRedoublant(estRedoublant);
        inscription.setStatut(StatutInscription.EN_COURS);

        return inscriptionRepository.save(inscription);
    }

    private boolean determinerRedoublement(Student student, SchoolClass nouvelleClasse) {
        // Logique métier :
        // - Si l'élève était déjà dans cette classe l'année précédente
        // - Si la moyenne est insuffisante
        // - Si le conseil de classe a décidé le redoublement
        return false; // À implémenter
    }
}
