package com.kalanblow.school_management.infrastructure.controller;

import com.kalanblow.school_management.application.dto.SchoolClassRequest;
import com.kalanblow.school_management.application.dto.SchoolClassResponse;
import com.kalanblow.school_management.application.mapper.SchoolClassMapper;
import com.kalanblow.school_management.application.usecase.*;
import com.kalanblow.school_management.model.SchoolClass;
import com.kalanblow.school_management.service.SchoolClassService;
import jakarta.validation.Valid;
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

    public SchoolClassController(CreateSchoolClassUseCase createSchoolClassUseCase, DeleteSchoolClassUseCase deleteSchoolClassUseCase, UpdateSchoolClassUseCase updatedSchoolClass, GetSchoolClassUseCase getSchoolClassUseCase, SearchSchoolClassUseCase searchSchoolClassUseCase, SchoolClassMapper mapper) {
        this.createSchoolClassUseCase = createSchoolClassUseCase;
        this.deleteSchoolClassUseCase = deleteSchoolClassUseCase;
        this.updatedSchoolClass = updatedSchoolClass;
        this.getSchoolClassUseCase = getSchoolClassUseCase;
        this.searchSchoolClassUseCase = searchSchoolClassUseCase;
        this.mapper = mapper;
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

}
