package com.kalanblow.school_management.application.usecase;

import com.kalanblow.school_management.infrastructure.persistence.StudentSpecifications;
import com.kalanblow.school_management.model.Student;
import com.kalanblow.school_management.service.StudentService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class SearchStudentsUseCase {

    private final StudentService studentService;

    public SearchStudentsUseCase(StudentService studentService) {
        this.studentService = studentService;
    }

    @Cacheable(value = "student-pages", key = "#key.toString()")
    public Page<Student> execute(StudentPageCacheKey key, Pageable pageable, Specification<Student> spec) {
        // Construire la spécification à partir de la clé
        spec = buildSpecification(key);
        pageable = PageRequest.of(key.page(), key.size());
        return studentService.search(spec, pageable);
    }

    private Specification<Student> buildSpecification(StudentPageCacheKey key) {
        return Specification.where(StudentSpecifications.firstNameContains(key.firstName()))
                .and(StudentSpecifications.lastNameContains(key.lastName()))
                .and(StudentSpecifications.classIdEquals(key.classId()));
    }
}
