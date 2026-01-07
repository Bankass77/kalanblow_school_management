package com.kalanblow.school_management.application.usecase.anneescolaire;

import com.kalanblow.school_management.model.anneescolaire.AnneeScolaire;
import com.kalanblow.school_management.service.AnneeScolaireService;
import org.springframework.stereotype.Service;

@Service
public class UpdateAnneeScolaireUseCase {

    private  final AnneeScolaireService anneeScolaireService;

    public UpdateAnneeScolaireUseCase(AnneeScolaireService anneeScolaireService) {
        this.anneeScolaireService = anneeScolaireService;
    }

    public AnneeScolaire execute(Long id, AnneeScolaire anneeScolaire){

        return anneeScolaireService.update(id, anneeScolaire);
    }
}
