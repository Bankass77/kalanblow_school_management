package com.kalanblow.school_management.repository;

import com.kalanblow.school_management.model.etablissement.ChefEtablissement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

public interface ChefEtablissementRepository {

    Optional<ChefEtablissement> findById(Long id);

    ChefEtablissement save(ChefEtablissement chef);

    Page<ChefEtablissement> search(Specification<ChefEtablissement> chefEtablissementSpecification, Pageable pageable);
}
