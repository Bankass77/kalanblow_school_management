package com.kalanblow.school_management.infrastructure.persistence.note;

import com.kalanblow.school_management.model.anneescolaire.PeriodeScolaire;
import com.kalanblow.school_management.model.shared.enums.TypePeriode;
import com.kalanblow.school_management.repository.PeriodeScolaireRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class PeriodeScolaireRepositoryJpa implements PeriodeScolaireRepository {

    private final JpaPeriodeScolaireRepository repo;

    public PeriodeScolaireRepositoryJpa(JpaPeriodeScolaireRepository repo) {
        this.repo = repo;
    }
    @Override
    public List<PeriodeScolaire> findByAnnee(Long anneeId) {
        List<PeriodeScolaire> allPeriodesScolaires= repo.findAll();
        return allPeriodesScolaires.stream().filter(p->p.getAnneeScolaire().getAnneeScolaireId().equals(anneeId)).collect(Collectors.toList());
    }

    @Override
    public List<PeriodeScolaire> findByAnneeAndType(Long anneeId, TypePeriode type) {
        return List.of();
    }

    @Override
    public boolean hasChevauchement(Long anneeId, LocalDate debut, LocalDate fin) {
        return repo.existsByAnneeScolaire_AnneeScolaireIdAndDateDebutLessThanEqualAndDateFinGreaterThanEqual(
                anneeId, fin, debut
        );
    }

    @Override
    public PeriodeScolaire save(PeriodeScolaire periode) {
        return null;
    }

    @Override
    public Optional<PeriodeScolaire> findById(Long id) {
        return Optional.empty();
    }
}
