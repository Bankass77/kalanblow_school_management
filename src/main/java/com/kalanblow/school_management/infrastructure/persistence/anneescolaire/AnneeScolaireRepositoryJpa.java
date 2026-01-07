package com.kalanblow.school_management.infrastructure.persistence.anneescolaire;

import com.kalanblow.school_management.model.anneescolaire.AnneeScolaire;
import com.kalanblow.school_management.repository.AnneeScolaireRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public class AnneeScolaireRepositoryJpa implements AnneeScolaireRepository {

    private final JpaAnneeScolaireRepository anneeScolaireRepository;

    public AnneeScolaireRepositoryJpa(JpaAnneeScolaireRepository anneeScolaireRepository) {
        this.anneeScolaireRepository = anneeScolaireRepository;
    }

    @Override
    public AnneeScolaire save(AnneeScolaire annee) {
        return anneeScolaireRepository.save(annee);
    }

    @Override
    public Optional<AnneeScolaire> findById(Long id) {
        return anneeScolaireRepository.findById(id);
    }

    @Override
    public Optional<AnneeScolaire> findByAnneeScolaireDebut(Integer debut) {
        if (anneeScolaireRepository.existsByAnneeDebut(debut)){
            return anneeScolaireRepository.findByAnneeDebut(debut);
        }

        return Optional.empty();
    }

    @Override
    public Page<AnneeScolaire> search(Specification<AnneeScolaire> specification, Pageable pageable) {
        return anneeScolaireRepository.findAll(specification, pageable);
    }

    @Override
    public boolean existsByAnneeScolaireDebut(Integer debut) {
        return anneeScolaireRepository.existsByAnneeDebut(debut);
    }

    @Override
    public Optional<AnneeScolaire> findByAnneeFin(Integer fin) {
        return anneeScolaireRepository.findByAnneeFin(fin);
    }

    @Override
    public Set<AnneeScolaire> findAllById(Set<Long> anneeScolaireIds) {
        return Set.of();
    }
}

