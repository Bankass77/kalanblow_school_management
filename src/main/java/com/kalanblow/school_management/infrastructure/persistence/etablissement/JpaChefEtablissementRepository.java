package com.kalanblow.school_management.infrastructure.persistence.etablissement;

import com.kalanblow.school_management.model.etablissement.ChefEtablissement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface JpaChefEtablissementRepository extends JpaRepository<ChefEtablissement,Long>, JpaSpecificationExecutor<ChefEtablissement> {
   // Page<ChefEtablissement> search(Specification<ChefEtablissement> chefEtablissementSpecification, Pageable pageable);

    @Override
    Optional<ChefEtablissement> findById(Long aLong);

    @Override
    boolean existsById(Long aLong);
}
