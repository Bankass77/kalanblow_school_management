package com.kalanblow.school_management.infrastructure.persistence.inscription;

import com.kalanblow.school_management.model.anneescolaire.AnneeScolaire;
import com.kalanblow.school_management.model.classe.SchoolClass;
import com.kalanblow.school_management.model.inscription.Inscription;
import com.kalanblow.school_management.model.inscription.StatutInscription;
import com.kalanblow.school_management.repository.InscriptionRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InscriptionRepositoryJpa implements InscriptionRepository {

    private final JpaInscriptionRepository jpaInscriptionRepository;

    public InscriptionRepositoryJpa(JpaInscriptionRepository jpaInscriptionRepository) {
        this.jpaInscriptionRepository = jpaInscriptionRepository;
    }

    @Override
    public Long countBySchoolClassAndAnneeScolaireAndStatut(SchoolClass schoolClass, AnneeScolaire anneeScolaire, StatutInscription statut) {
        return jpaInscriptionRepository.count();
    }

    @Override
    public Inscription save(Inscription inscription) {
        return jpaInscriptionRepository.save(inscription);
    }

    @Override
    public List<Inscription> findByAnneeScolaireAndStatut(AnneeScolaire annee, StatutInscription statutInscription) {
        return List.of();
    }

    @Override
    public long countAbsencesNonJustifiees(Long studentId, Long anneeId) {
        return 0;
    }

    @Override
    public long countRetards(Long studentId, Long anneeId) {
        return 0;
    }
}
