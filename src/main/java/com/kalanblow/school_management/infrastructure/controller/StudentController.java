package com.kalanblow.school_management.infrastructure.controller;

import com.kalanblow.school_management.application.dto.PageResponse;
import com.kalanblow.school_management.application.dto.StudentRequest;
import com.kalanblow.school_management.application.dto.StudentResponse;
import com.kalanblow.school_management.application.mapper.PageMapper;
import com.kalanblow.school_management.application.mapper.StudentMapper;
import com.kalanblow.school_management.application.usecase.*;
import com.kalanblow.school_management.infrastructure.persistence.StudentSpecifications;
import com.kalanblow.school_management.model.Student;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    private final StudentMapper mapper;
    private final PageMapper pageMapper;
    private final SearchStudentsUseCase searchStudentsUseCase;
    private final CreateStudentUseCase createStudentUseCase;
    private final GetStudentUseCase getStudentUseCase;
    private final UpdateStudentUseCase updateStudentUseCase;
    private final DeleteStudentUseCase deleteStudentUseCase;

    public StudentController(StudentMapper mapper, PageMapper pageMapper, SearchStudentsUseCase searchStudentsUseCase, CreateStudentUseCase createStudentUseCase, GetStudentUseCase getStudentUseCase, UpdateStudentUseCase updateStudentUseCase, DeleteStudentUseCase deleteStudentUseCase) {
        this.mapper = mapper;
        this.pageMapper = pageMapper;
        this.searchStudentsUseCase = searchStudentsUseCase;
        this.createStudentUseCase = createStudentUseCase;
        this.getStudentUseCase = getStudentUseCase;
        this.updateStudentUseCase = updateStudentUseCase;
        this.deleteStudentUseCase = deleteStudentUseCase;
    }

    @PostMapping
    public ResponseEntity<StudentResponse> create(@RequestBody @Valid StudentRequest request) {
        Student student = mapper.toEntity(request);
        Student created = createStudentUseCase.execute(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(created));
    }

    @RequestMapping("/{id}")
    public ResponseEntity<StudentResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toResponse(getStudentUseCase.execute(id)));
    }

    @GetMapping
    public ResponseEntity<PageResponse<StudentResponse>> getAll(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) Long classId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        page = Math.max(page, 0);
        size = Math.max(Math.max(size, 1), 50);
        Pageable pageable = PageRequest.of(page, size);

        Specification<Student> spec;
        if (firstName != null || lastName != null || classId != null) {
            spec = Specification
                    .where(StudentSpecifications.firstNameContains(firstName))
                    .and(StudentSpecifications.lastNameContains(lastName))
                    .and(StudentSpecifications.classIdEquals(classId));
        } else {
            spec = Specification.where((root, query, cb) -> null);
        }
        StudentPageCacheKey cacheKey =
                new StudentPageCacheKey(
                        page,
                        size,
                        firstName,
                        lastName,
                        classId
                );

        Page<Student> students = searchStudentsUseCase.execute(cacheKey, pageable, spec);
        return ResponseEntity.ok(pageMapper.toPageResponse(students, mapper::toResponse));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentResponse> update(@PathVariable Long id, @RequestBody @Valid StudentRequest request) {
        Student student = mapper.toEntity(request);
        Student updated = updateStudentUseCase.execute(id, student);
        return ResponseEntity.ok(mapper.toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deleteStudentUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }


}
