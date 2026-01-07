package com.kalanblow.school_management.repository;

import com.kalanblow.school_management.model.anneescolaire.PeriodeScolaire;
import com.kalanblow.school_management.model.shared.enums.TypePeriode;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PeriodeScolaireRepository {

    List<PeriodeScolaire> findByAnnee(Long anneeId);

    List<PeriodeScolaire> findByAnneeAndType(Long anneeId, TypePeriode type);

    boolean hasChevauchement(Long anneeId, LocalDate debut, LocalDate fin);

    PeriodeScolaire save(PeriodeScolaire periode);

    Optional<PeriodeScolaire> findById(Long id);
}
