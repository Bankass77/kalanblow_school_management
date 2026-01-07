package com.kalanblow.school_management.repository;

import com.kalanblow.school_management.model.anneescolaire.AnneeScolaire;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.Set;

public interface AnneeScolaireRepository {
    AnneeScolaire save(AnneeScolaire annee);

    Optional<AnneeScolaire> findById(Long id);

    Optional<AnneeScolaire> findByAnneeScolaireDebut(Integer debut);

    Page<AnneeScolaire> search(Specification<AnneeScolaire> specification, Pageable pageable);

    boolean existsByAnneeScolaireDebut(Integer debut);

    Optional<AnneeScolaire> findByAnneeFin(Integer debut);

    Set<AnneeScolaire> findAllById(Set<Long> anneeScolaireIds);
}
