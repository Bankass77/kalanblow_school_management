package com.kalanblow.school_management.infrastructure.persistence.note;

import com.kalanblow.school_management.model.anneescolaire.PeriodeScolaire;
import com.kalanblow.school_management.model.shared.enums.TypePeriode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.util.List;

public interface JpaPeriodeScolaireRepository extends JpaRepository<PeriodeScolaire, Long> , JpaSpecificationExecutor<PeriodeScolaire> {

    List<PeriodeScolaire> findByAnneeScolaire_AnneeScolaireId(Long anneeScolaire_anneeScolaireId);

    List<PeriodeScolaire> findByTypeAndAnneeScolaire_AnneeScolaireIdOrderByOrdre(TypePeriode type, Long anneeId);

    boolean existsByAnneeScolaire_AnneeScolaireIdAndDateDebutLessThanEqualAndDateFinGreaterThanEqual(
            Long anneeId,
            LocalDate dateFin,
            LocalDate dateDebut
    );
}

