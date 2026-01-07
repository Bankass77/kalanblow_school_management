package com.kalanblow.school_management.infrastructure.web.controller.classe;

import com.kalanblow.school_management.application.dto.page.PageResponse;
import com.kalanblow.school_management.application.dto.classe.SchoolClassRequest;
import com.kalanblow.school_management.application.dto.classe.SchoolClassResponse;
import com.kalanblow.school_management.application.mapper.PageMapper;
import com.kalanblow.school_management.application.mapper.SchoolClassMapper;
import com.kalanblow.school_management.application.usecase.classe.*;
import com.kalanblow.school_management.application.usecase.etablissement.GetEtablissementUsecase;
import com.kalanblow.school_management.infrastructure.persistence.classe.SchoolClassSpecifications;
import com.kalanblow.school_management.model.classe.SchoolClass;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/classes")
public class SchoolClassController {

    private final CreateSchoolClassUseCase createSchoolClassUseCase;
    private final DeleteSchoolClassUseCase deleteSchoolClassUseCase;
    private final UpdateSchoolClassUseCase updatedSchoolClass;
    private final GetSchoolClassUseCase getSchoolClassUseCase;
    private final SearchSchoolClassUseCase searchSchoolClassUseCase;
    private final SchoolClassMapper mapper;
    private final PageMapper pageMapper;
    private final GetEtablissementUsecase getEtablissementUsecase;

    public SchoolClassController(CreateSchoolClassUseCase createSchoolClassUseCase, DeleteSchoolClassUseCase deleteSchoolClassUseCase, UpdateSchoolClassUseCase updatedSchoolClass, GetSchoolClassUseCase getSchoolClassUseCase, SearchSchoolClassUseCase searchSchoolClassUseCase, SchoolClassMapper mapper, PageMapper pageMapper, GetEtablissementUsecase getEtablissementUsecase) {
        this.createSchoolClassUseCase = createSchoolClassUseCase;
        this.deleteSchoolClassUseCase = deleteSchoolClassUseCase;
        this.updatedSchoolClass = updatedSchoolClass;
        this.getSchoolClassUseCase = getSchoolClassUseCase;
        this.searchSchoolClassUseCase = searchSchoolClassUseCase;
        this.mapper = mapper;
        this.pageMapper = pageMapper;
        this.getEtablissementUsecase = getEtablissementUsecase;
    }

    @PostMapping
    public ResponseEntity<SchoolClassResponse> create(@RequestBody @Valid SchoolClassRequest request) {
        SchoolClass schoolClass = mapper.toEntity(request);
        SchoolClass created = createSchoolClassUseCase.execute(schoolClass);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(created));
    }

    @RequestMapping("/{name}")
    public ResponseEntity<SchoolClassResponse> getSchoolClassByName(@PathVariable String name) {

        SchoolClass schoolClass = getSchoolClassUseCase.findName(name);
        return ResponseEntity.ok(mapper.toResponse(schoolClass));
    }

    @RequestMapping("/{id}")
    public ResponseEntity<SchoolClassResponse> getSchoolClassById(@PathVariable Long id) {
        SchoolClass schoolClass = getSchoolClassUseCase.execute(id);
        return ResponseEntity.ok(mapper.toResponse(schoolClass));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SchoolClassResponse> updatedClass(@PathVariable Long id, @RequestBody @Valid SchoolClassRequest request) {
        SchoolClass schoolClass = mapper.toEntity(request);
        SchoolClass updated = updatedSchoolClass.execute(id, schoolClass);
        updated.setCapacity(request.capacity());
        updated.setName(request.name());
        return ResponseEntity.ok(mapper.toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchoolClass(@PathVariable Long id) {
        deleteSchoolClassUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<PageResponse<SchoolClassResponse>> getAllSchoolClasses(@RequestParam(required = false) String name,
                                                                                 @RequestParam(defaultValue = "0") int page,
                                                                                 @RequestParam(required = false) Long classId,
                                                                                 @RequestParam(defaultValue = "10") int size) {

        page = Math.max(page, 0);
        size = Math.max(Math.max(size, 1), 50);
        Pageable pageable = PageRequest.of(page, size);
        Specification<SchoolClass> specification;

        if (name != null) {
            specification = Specification.where(SchoolClassSpecifications.nameContains(name));
        } else {
            specification = Specification.where(((root, query, criteriaBuilder) -> null));
        }

        SchoolClassePageCacheKey cacheKey = new SchoolClassePageCacheKey(page, size, name,classId);

        Page<SchoolClass> schoolClassPage = searchSchoolClassUseCase.execute(cacheKey, pageable, specification);
        return ResponseEntity.ok(pageMapper.toPageResponse(schoolClassPage, mapper::toResponse));
    }

}
