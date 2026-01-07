package com.kalanblow.school_management.service;


import com.kalanblow.school_management.model.etablissement.ChefEtablissement;
import com.kalanblow.school_management.repository.ChefEtablissementRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ChefEtablissementService {

    private final ChefEtablissementRepository chefEtablissementRepository;

    public ChefEtablissementService(ChefEtablissementRepository chefEtablissementRepository) {
        this.chefEtablissementRepository = chefEtablissementRepository;
    }

    public ChefEtablissement create(ChefEtablissement chefEtablissement) {

        return chefEtablissementRepository.save(chefEtablissement);
    }


    public ChefEtablissement update(Long id, ChefEtablissement chefEtablissement) {

        ChefEtablissement existing = findById(id);
        if (existing != null) {

            existing.setPhoneNumber(chefEtablissement.getPhoneNumber());
            existing.setEmail(chefEtablissement.getEmail());
            existing.setUserName(chefEtablissement.getUserName());
            return chefEtablissementRepository.save(chefEtablissement);
        }

        return null;
    }


    public boolean existingChefEtablissement(Long idChef) {

        ChefEtablissement chefEtablissement = chefEtablissementRepository.findById(idChef).orElseThrow(() -> new EntityNotFoundException(" Le chef d'établissement avec cet id " + idChef + " est introuvable."));

        return chefEtablissement != null;
    }

    public Page<ChefEtablissement> search(Specification<ChefEtablissement> specification, Pageable pageable) {


        Specification<ChefEtablissement> chefEtablissementSpecification = specification == null ? Specification.where(((root, query, criteriaBuilder) -> null)) : specification;

        return chefEtablissementRepository.search(chefEtablissementSpecification, pageable);
    }

    public ChefEtablissement findById(Long id) {

        return chefEtablissementRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(" Le chef d'établissement avec cet id " + id + " est introuvable."));
    }
}
