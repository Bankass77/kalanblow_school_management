package com.kalanblow.school_management.application.usecase.classe;

import com.kalanblow.school_management.model.anneescolaire.AnneeScolaire;
import com.kalanblow.school_management.model.classe.SchoolClass;
import com.kalanblow.school_management.model.etablissement.Etablissement;
import com.kalanblow.school_management.service.AnneeScolaireService;
import com.kalanblow.school_management.service.EtablissementService;
import com.kalanblow.school_management.service.SchoolClassService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Service
public class CreateSchoolClassUseCase {

    private final SchoolClassService schoolClassService;
    private final EtablissementService etablissementService;
    private final AnneeScolaireService anneeScolaireService;

    public CreateSchoolClassUseCase(SchoolClassService schoolClassService, EtablissementService etablissementService, AnneeScolaireService anneeScolaireService) {
        this.schoolClassService = schoolClassService;
        this.etablissementService = etablissementService;
        this.anneeScolaireService = anneeScolaireService;
    }

    @CacheEvict(value = "schoolClass-pages", allEntries = true)
    public SchoolClass execute(SchoolClass schoolClass){

        Etablissement etablissement= etablissementService.findById(schoolClass.getEtablissement().getEtablisementScolaireId());
        AnneeScolaire anneeScolaire= null;
        if(schoolClass.getSchoolClassId() !=null){
            anneeScolaire= anneeScolaireService.findById(schoolClass.getAnneeScolaire().getAnneeScolaireId());

        }
        schoolClass.setAnneeScolaire(anneeScolaire);
        schoolClass.setEtablissement(etablissement);
        return schoolClassService.create(schoolClass);
    }
}
