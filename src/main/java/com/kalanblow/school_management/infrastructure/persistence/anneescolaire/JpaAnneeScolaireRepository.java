package com.kalanblow.school_management.infrastructure.persistence.anneescolaire;

import com.kalanblow.school_management.model.anneescolaire.AnneeScolaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface JpaAnneeScolaireRepository  extends JpaRepository<AnneeScolaire,Long> , JpaSpecificationExecutor<AnneeScolaire> {
    Optional<AnneeScolaire> findByAnneeDebut(Integer debut);
    Optional<AnneeScolaire> findByAnneeFin(Integer fin);
    boolean existsByAnneeDebut(Integer debut);
}
