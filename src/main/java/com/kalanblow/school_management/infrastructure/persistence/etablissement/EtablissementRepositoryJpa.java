package com.kalanblow.school_management.infrastructure.persistence.etablissement;

import com.kalanblow.school_management.model.etablissement.Etablissement;
import com.kalanblow.school_management.repository.EtablissementRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class EtablissementRepositoryJpa implements EtablissementRepository {

    private final JpaEtablissementRepository jpaEtablissementRepository;

    public EtablissementRepositoryJpa(JpaEtablissementRepository jpaEtablissementRepository) {
        this.jpaEtablissementRepository = jpaEtablissementRepository;
    }

    @Override
    public Page<Etablissement> search(Specification<Etablissement> spec, Pageable pageable) {
        return jpaEtablissementRepository.findAll(spec, pageable);
    }

    @Override
    public Optional<Etablissement> findById(Long id) {
        return jpaEtablissementRepository.findById(id);
    }

    @Override
    public Etablissement save(Etablissement etablissement) {

        return jpaEtablissementRepository.save(etablissement);
    }


    @Override
    public boolean existEtablissement(Long id) {
        return jpaEtablissementRepository.existsById(id);
    }
}
