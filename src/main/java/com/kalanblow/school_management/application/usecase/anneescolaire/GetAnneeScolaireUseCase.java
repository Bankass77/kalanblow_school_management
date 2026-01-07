package com.kalanblow.school_management.application.usecase.anneescolaire;

import com.kalanblow.school_management.model.anneescolaire.AnneeScolaire;
import com.kalanblow.school_management.service.AnneeScolaireService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetAnneeScolaireUseCase {

    private final AnneeScolaireService anneeScolaireService;

    public GetAnneeScolaireUseCase(AnneeScolaireService anneeScolaireService) {
        this.anneeScolaireService = anneeScolaireService;
    }

    public AnneeScolaire execute(Long id) {

        return anneeScolaireService.findById(id);
    }
}
