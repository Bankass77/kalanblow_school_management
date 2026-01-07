package com.kalanblow.school_management.infrastructure.persistence.etablissement;

import com.kalanblow.school_management.model.etablissement.ChefEtablissement;
import com.kalanblow.school_management.repository.ChefEtablissementRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ChefEtablissementRepositoryJpa implements ChefEtablissementRepository {

    private  final JpaChefEtablissementRepository jpaChefEtablissementRepository;

    public ChefEtablissementRepositoryJpa(JpaChefEtablissementRepository jpaChefEtablissementRepository) {
        this.jpaChefEtablissementRepository = jpaChefEtablissementRepository;
    }


    @Override
    public Optional<ChefEtablissement> findById(Long id) {

        return jpaChefEtablissementRepository.findById(id);
    }

    @Override
    public ChefEtablissement save(ChefEtablissement chef) {
        return jpaChefEtablissementRepository.save(chef);
    }

    @Override
    public Page<ChefEtablissement> search(Specification<ChefEtablissement> chefEtablissementSpecification, Pageable pageable) {
        return jpaChefEtablissementRepository.findAll(chefEtablissementSpecification, pageable);
    }
}
