package com.kalanblow.school_management.application.usecase.etablissement;

import com.kalanblow.school_management.model.etablissement.Etablissement;
import com.kalanblow.school_management.service.EtablissementService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Service
public class CreateEtablissementUseCase {
    private final EtablissementService etablissementService;

    public CreateEtablissementUseCase(EtablissementService etablissementService) {
        this.etablissementService = etablissementService;
    }

    @CacheEvict(value = "etablissement-pages", allEntries = true)
    public Etablissement execute(Etablissement etablissement) {

        return etablissementService.save(etablissement);
    }
}
