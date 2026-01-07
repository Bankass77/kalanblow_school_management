package com.kalanblow.school_management.repository;


import com.kalanblow.school_management.model.anneescolaire.AnneeScolaire;
import com.kalanblow.school_management.model.classe.SchoolClass;
import com.kalanblow.school_management.model.inscription.Inscription;
import com.kalanblow.school_management.model.inscription.StatutInscription;

import java.util.List;

public interface InscriptionRepository {

    Long countBySchoolClassAndAnneeScolaireAndStatut(
            SchoolClass schoolClass,
            AnneeScolaire anneeScolaire,
            StatutInscription statut);

    Inscription save(Inscription inscription);

    List<Inscription> findByAnneeScolaireAndStatut(AnneeScolaire annee, StatutInscription statutInscription);

    long countAbsencesNonJustifiees(Long studentId, Long anneeId);

    long countRetards(Long studentId, Long anneeId);
}
