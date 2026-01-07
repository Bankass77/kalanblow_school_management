package com.kalanblow.school_management.service;

import com.kalanblow.school_management.model.etablissement.Etablissement;
import com.kalanblow.school_management.repository.EtablissementRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class EtablissementService {

    private final EtablissementRepository etablissementRepository;

    public EtablissementService(EtablissementRepository etablissementRepository) {
        this.etablissementRepository = etablissementRepository;
    }

    @Transactional(readOnly = true)
    public Page<Etablissement> search(
            Specification<Etablissement> spec,
            Pageable pageable) {

        Specification<Etablissement> specification = spec == null ? Specification.where(((root, query, criteriaBuilder) -> null)) : spec;
        return etablissementRepository.search(specification, pageable);
    }

    @Transactional(readOnly = true)
    public Etablissement findById(Long id) {

        return etablissementRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(" Etablissement with this is id " + id + " not found"));
    }

    public Etablissement save(Etablissement etablissement) {

        return etablissementRepository.save(etablissement);
    }

    public Etablissement update(Long id, Etablissement etablissement) {

        Etablissement etab = findById(id);
        if (etablissementRepository.existEtablissement(etab.getEtablisementScolaireId())) {

            etab.setClasses(etablissement.getClasses());
            etab.setEleves(etablissement.getEleves());
            etab.setAddress(etablissement.getAddress());
            etab.setLogo(etablissement.getLogo());
            etab.setCreatedDate(etablissement.getCreatedDate());
            etab.setChefEtablissement(etablissement.getChefEtablissement());
            etab.setEtablissementEmail(etablissement.getEtablissementEmail());
            etab.setNomEtablissement(etablissement.getNomEtablissement());
            etab.setIdentiantEtablissement(etablissement.getIdentiantEtablissement());
            etab.setLastModifiedDate(etablissement.getLastModifiedDate());
            etab.setPhoneNumber(etablissement.getPhoneNumber());
        }
        return etab;
    }


}
