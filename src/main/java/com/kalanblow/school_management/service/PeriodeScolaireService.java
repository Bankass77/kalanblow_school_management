package com.kalanblow.school_management.service;

import com.kalanblow.school_management.model.anneescolaire.PeriodeScolaire;
import com.kalanblow.school_management.repository.PeriodeScolaireRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PeriodeScolaireService {

    private final PeriodeScolaireRepository repo;

    public PeriodeScolaireService(PeriodeScolaireRepository repo) {
        this.repo = repo;
    }

    public PeriodeScolaire create(PeriodeScolaire p) {

        if (repo.hasChevauchement(p.getAnneeScolaire().getAnneeScolaireId(), p.getDateDebut(), p.getDateFin())) {
            throw new IllegalArgumentException("Cette période chevauche une autre période !");
        }

        return repo.save(p);
    }

    public Optional<PeriodeScolaire> findById(Long aLong) {
        return  repo.findById(aLong);
    }
}
