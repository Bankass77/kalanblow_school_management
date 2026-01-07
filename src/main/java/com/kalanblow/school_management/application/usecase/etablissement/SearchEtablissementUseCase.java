package com.kalanblow.school_management.application.usecase.etablissement;

import com.kalanblow.school_management.infrastructure.persistence.etablissement.EtablissementSpecifications;
import com.kalanblow.school_management.model.etablissement.Etablissement;
import com.kalanblow.school_management.service.EtablissementService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class SearchEtablissementUseCase {

    private final EtablissementService etablissementService;


    public SearchEtablissementUseCase(EtablissementService etablissementService) {
        this.etablissementService = etablissementService;
    }



   public   Page<Etablissement> execute(EtablissementPageCacheKey key, Pageable pageable, Specification<Etablissement> etablissementSpecification) {

        etablissementSpecification = buildSpecification(key);

        pageable = PageRequest.of(key.page(), key.size());

        return etablissementService.search(etablissementSpecification, pageable);
    }

    private Specification<Etablissement> buildSpecification(EtablissementPageCacheKey key) {

        return Specification.where(EtablissementSpecifications.nometablissementContains(key.nom()))
                .or(EtablissementSpecifications.etablissementEmailContains(key.email()))
                .or(EtablissementSpecifications.addresseContains(key.address()))
                .or(EtablissementSpecifications.identifiantEtablissementEquals(key.identifiant()))
                .or(EtablissementSpecifications.phoneNumberContains(key.phoneNumber()));
    }
}
