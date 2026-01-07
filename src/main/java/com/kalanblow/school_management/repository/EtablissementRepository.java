package com.kalanblow.school_management.repository;

import com.kalanblow.school_management.model.etablissement.Etablissement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

public interface EtablissementRepository {

    Page<Etablissement> search(
            Specification<Etablissement> spec,
            Pageable pageable
    );

    Optional<Etablissement> findById(Long id);

    Etablissement save(Etablissement etablissement);


    boolean existEtablissement(Long id);
}
