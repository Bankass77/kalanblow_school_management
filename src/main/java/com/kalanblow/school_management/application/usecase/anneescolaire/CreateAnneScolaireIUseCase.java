package com.kalanblow.school_management.application.usecase.anneescolaire;

import com.kalanblow.school_management.model.anneescolaire.AnneeScolaire;
import com.kalanblow.school_management.service.AnneeScolaireService;
import org.springframework.stereotype.Service;

@Service
public class CreateAnneScolaireIUseCase {

    private final AnneeScolaireService anneeScolaireService;

    public CreateAnneScolaireIUseCase(AnneeScolaireService anneeScolaireService) {
        this.anneeScolaireService = anneeScolaireService;
    }

    public AnneeScolaire execute(AnneeScolaire anneeScolaire) {

        if (anneeScolaire.isAnneeScolaireValid()) {
            return anneeScolaireService.save(anneeScolaire);
        }

        return anneeScolaire;
    }
}
