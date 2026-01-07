package com.kalanblow.school_management.application.usecase.etablissement;

import com.kalanblow.school_management.model.etablissement.Etablissement;
import com.kalanblow.school_management.service.EtablissementService;
import org.springframework.stereotype.Service;

@Service
public class GetEtablissementUsecase {

    private final EtablissementService etablissementService;


    public GetEtablissementUsecase(EtablissementService etablissementService) {
        this.etablissementService = etablissementService;
    }

    public Etablissement findById(Long id) {

        return   etablissementService.findById(id);
    }
}
