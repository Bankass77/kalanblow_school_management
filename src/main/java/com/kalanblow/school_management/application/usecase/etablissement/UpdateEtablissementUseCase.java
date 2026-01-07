package com.kalanblow.school_management.application.usecase.etablissement;

import com.kalanblow.school_management.model.etablissement.Etablissement;
import com.kalanblow.school_management.service.EtablissementService;
import org.springframework.stereotype.Service;

@Service
public class UpdateEtablissementUseCase {

    private final EtablissementService etablissementService;

    public UpdateEtablissementUseCase(EtablissementService etablissementService) {
        this.etablissementService = etablissementService;
    }

    public Etablissement execute(Long id, Etablissement etablissement) {

        Etablissement etablissement1 = etablissementService.findById(id);

        if (etablissement1 != null) {
            etablissement1 = etablissementService.update(id, etablissement);

        }

        return etablissement1;
    }
}
