package com.kalanblow.school_management.infrastructure.web.controller.anneescolaire;

import com.kalanblow.school_management.application.dto.anneescolaire.AnneeScolaireRequest;
import com.kalanblow.school_management.application.dto.anneescolaire.AnneeScolaireResponse;
import com.kalanblow.school_management.application.dto.page.PageResponse;
import com.kalanblow.school_management.application.mapper.AnneeScolaireMapper;
import com.kalanblow.school_management.application.mapper.PageMapper;
import com.kalanblow.school_management.application.usecase.anneescolaire.*;
import com.kalanblow.school_management.infrastructure.persistence.anneescolaire.AnneeScolaireSpecifications;
import com.kalanblow.school_management.model.anneescolaire.AnneeScolaire;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ann")
public class AnneeScolaireController {

    private final CreateAnneScolaireIUseCase createAnneScolaireIUseCase;
    private final GetAnneeScolaireUseCase getAnneeScolaireUseCase;
    private final SearchAnneeScolaireUseCase searchAnneeScolaireUseCase;
    private final UpdateAnneeScolaireUseCase updateAnneeScolaireUseCase;

    private final PageMapper pageMapper;
    private final AnneeScolaireMapper anneeScolaireMapper;

    public AnneeScolaireController(CreateAnneScolaireIUseCase createAnneScolaireIUseCase, GetAnneeScolaireUseCase getAnneeScolaireUseCase, SearchAnneeScolaireUseCase searchAnneeScolaireUseCase, UpdateAnneeScolaireUseCase updateAnneeScolaireUseCase, PageMapper pageMapper, AnneeScolaireMapper anneeScolaireMapper) {
        this.createAnneScolaireIUseCase = createAnneScolaireIUseCase;
        this.getAnneeScolaireUseCase = getAnneeScolaireUseCase;
        this.searchAnneeScolaireUseCase = searchAnneeScolaireUseCase;
        this.updateAnneeScolaireUseCase = updateAnneeScolaireUseCase;
        this.pageMapper = pageMapper;
        this.anneeScolaireMapper = anneeScolaireMapper;
    }

    @PostMapping
    private ResponseEntity<AnneeScolaireResponse> create(@RequestBody @Valid AnneeScolaireRequest request) {

        AnneeScolaire anneeScolaire = anneeScolaireMapper.toEntity(request);
        AnneeScolaire created = createAnneScolaireIUseCase.execute(anneeScolaire);
        return ResponseEntity.status(HttpStatus.CREATED).body(anneeScolaireMapper.toResponse(created));
    }

    @RequestMapping("{id}")
    public ResponseEntity<AnneeScolaireResponse> getAnneeScolaireById(@PathVariable Long id) {
        return ResponseEntity.ok(anneeScolaireMapper.toResponse(getAnneeScolaireUseCase.execute(id)));
    }

    @RequestMapping("/search/anneescolaire")
    public ResponseEntity<PageResponse<AnneeScolaireResponse>> getAllAnneeScolaire(@RequestParam(required = false) Long anneeScolaireId, @RequestParam(required = false) Integer debut, @RequestParam(required = false) Integer fin, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {

        page = Math.max(page, 0);
        size = Math.max(Math.min(size, 1), 20);
        Pageable pageable = PageRequest.of(page, size);
        Specification<AnneeScolaire> spec;
        if (debut != 0 || fin != 0) {
            spec = Specification.where(AnneeScolaireSpecifications.anneeScolaireDebutContains(debut)).or(AnneeScolaireSpecifications.anneeScolaireFinContains(fin)).or(AnneeScolaireSpecifications.eanneeScolaireIdContains(anneeScolaireId));
        } else {
            spec = Specification.where(((root, query, criteriaBuilder) -> null));
        }
        AnneeScolairePageCacheKey key = new AnneeScolairePageCacheKey(page, size, debut, fin, anneeScolaireId);
        Page<AnneeScolaire> anneeScolaires = searchAnneeScolaireUseCase.execute(key, pageable, spec);

        return ResponseEntity.ok(pageMapper.toPageResponse(anneeScolaires, anneeScolaireMapper::toResponse));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnneeScolaireResponse> updateAnneeScolaire(@PathVariable Long id, @RequestBody @Valid AnneeScolaireRequest request) {

        AnneeScolaire anneeScolaire = getAnneeScolaireUseCase.execute(id);
        if (anneeScolaire != null) {

            return updateAnneeScolaire(id, request);
        }
        return null;
    }

}
