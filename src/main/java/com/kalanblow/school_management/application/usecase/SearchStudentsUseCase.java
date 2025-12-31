package com.kalanblow.school_management.application.usecase;

import com.kalanblow.school_management.model.Student;
import com.kalanblow.school_management.repository.StudentRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class SearchStudentsUseCase {

    private final StudentRepository studentRepository;

    public SearchStudentsUseCase(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Cacheable(value = "student-pages", key = "root.methodName+ '_' + #key")
    public Page<Student> execute(StudentPageCacheKey key, Pageable pageable,Specification<Student> spec) {
        return studentRepository.search(spec, pageable);
    }
}
