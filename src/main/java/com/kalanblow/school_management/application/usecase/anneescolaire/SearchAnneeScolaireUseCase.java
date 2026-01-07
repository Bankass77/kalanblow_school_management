package com.kalanblow.school_management.application.usecase.anneescolaire;

import com.kalanblow.school_management.infrastructure.persistence.anneescolaire.AnneeScolaireSpecifications;
import com.kalanblow.school_management.model.anneescolaire.AnneeScolaire;
import com.kalanblow.school_management.service.AnneeScolaireService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class SearchAnneeScolaireUseCase {

    private final AnneeScolaireService anneeScolaireService;

    public SearchAnneeScolaireUseCase(AnneeScolaireService anneeScolaireService) {
        this.anneeScolaireService = anneeScolaireService;
    }

    @Cacheable(value = "anneescolaires-pages", key = "#key.toString()")
    public Page<AnneeScolaire> execute(AnneeScolairePageCacheKey key, Pageable pageable, Specification<AnneeScolaire> spec) {
        // Construire la spécification à partir de la clé
        spec = buildSpecification(key);
        pageable = PageRequest.of(key.page(), key.size());
        return anneeScolaireService.search(spec, pageable);
    }

    private Specification<AnneeScolaire> buildSpecification(AnneeScolairePageCacheKey key) {
        return Specification.where(AnneeScolaireSpecifications.eanneeScolaireIdContains(key.anneeScolaireId()).or(
                        AnneeScolaireSpecifications.anneeScolaireDebutContains(key.debut()))
                .or(AnneeScolaireSpecifications.anneeScolaireFinContains(key.fin())));
    }
}
