package com.kalanblow.school_management.infrastructure.web.controller.etablissement;

import com.kalanblow.school_management.application.dto.etablissement.EtablissementRequest;
import com.kalanblow.school_management.application.dto.etablissement.EtablissementResponse;
import com.kalanblow.school_management.application.dto.page.PageResponse;
import com.kalanblow.school_management.application.mapper.EtablissementMapper;
import com.kalanblow.school_management.application.mapper.PageMapper;
import com.kalanblow.school_management.application.usecase.etablissement.*;
import com.kalanblow.school_management.infrastructure.persistence.etablissement.EtablissementSpecifications;
import com.kalanblow.school_management.model.etablissement.Etablissement;
import com.kalanblow.school_management.model.shared.Address;
import com.kalanblow.school_management.model.shared.util.StringNormalizer;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/etablissement")
public class EtablissementController {

    private final GetEtablissementUsecase etablissementUsecase;

    private final EtablissementMapper etablissementMapper;
    private final PageMapper pageMapper;

    private final CreateEtablissementUseCase createEtablissementUseCase;

    private final SearchEtablissementUseCase searchEtablissementUseCase;
    private final UpdateEtablissementUseCase updateEtablissementUseCase;
    private final GetEtablissementUsecase getEtablissementUsecase;

    public EtablissementController(GetEtablissementUsecase etablissementUsecase, EtablissementMapper etablissementMapper, PageMapper pageMapper, CreateEtablissementUseCase createEtablissementUseCase, SearchEtablissementUseCase searchEtablissementUseCase, UpdateEtablissementUseCase updateEtablissementUseCase, GetEtablissementUsecase getEtablissementUsecase) {
        this.etablissementUsecase = etablissementUsecase;
        this.etablissementMapper = etablissementMapper;
        this.pageMapper = pageMapper;
        this.createEtablissementUseCase = createEtablissementUseCase;
        this.searchEtablissementUseCase = searchEtablissementUseCase;
        this.updateEtablissementUseCase = updateEtablissementUseCase;
        this.getEtablissementUsecase = getEtablissementUsecase;
    }


    @PostMapping
    public ResponseEntity<EtablissementResponse> createEtablissement(@RequestBody @Valid EtablissementRequest etablissementRequest) {

        Etablissement etablissement = etablissementMapper.toEntity(etablissementRequest);
        Etablissement created = createEtablissementUseCase.execute(etablissement);
        return ResponseEntity.status(HttpStatus.CREATED).body(etablissementMapper.toResponse(created));
    }


    @RequestMapping("/{id}")
    public ResponseEntity<EtablissementResponse> findEtablissementById(@PathVariable Long id) {

        Etablissement etablissement = getEtablissementUsecase.findById(id);

        return ResponseEntity.ok(etablissementMapper.toResponse(etablissement));
    }


    @PutMapping("/{id}")
    public ResponseEntity<EtablissementResponse> update(@PathVariable Long id, @RequestBody @Valid EtablissementRequest request) {

        Etablissement update = etablissementMapper.toEntity(request);
        Etablissement etablissement = updateEtablissementUseCase.execute(id, update);

        return ResponseEntity.ok(etablissementMapper.toResponse(etablissement));
    }

    @RequestMapping
    public ResponseEntity<PageResponse<EtablissementResponse>> getAll(@RequestParam(required = false) long identifiant,
                                                                      @RequestParam(required = false) String email,
                                                                      @RequestParam(required = false) String nom,
                                                                      @RequestParam(required = false) String phoneNumber,
                                                                      @RequestParam(required = false) String address,
                                                                      @RequestParam(defaultValue = "0") int page,
                                                                      @RequestParam(defaultValue = "10") int size) {


        page = Math.max(page, 0);
        size = Math.max(Math.min(size, 1), 20);
        Pageable pageable = PageRequest.of(page, size);

        Specification<Etablissement> specification = null;

        if (identifiant != 0 || email != null || phoneNumber != null || address != null || nom != null) {

            specification = Specification.where(EtablissementSpecifications.nometablissementContains(nom))
                    .or(EtablissementSpecifications.phoneNumberContains(phoneNumber)).or(EtablissementSpecifications
                            .addresseContains(address)).or(EtablissementSpecifications.etablissementEmailContains(email));
        }

        EtablissementPageCacheKey key = new EtablissementPageCacheKey(StringNormalizer.normalize(nom), StringNormalizer.normalize(email),
                StringNormalizer.normalize(address), identifiant, StringNormalizer.normalize(phoneNumber), page, size);

        Page<Etablissement> etablissementPage = searchEtablissementUseCase.execute(key, pageable, specification);

        return ResponseEntity.ok(pageMapper.toPageResponse(etablissementPage, etablissementMapper::toResponse));
    }
}
