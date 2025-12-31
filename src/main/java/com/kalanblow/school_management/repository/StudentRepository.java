package com.kalanblow.school_management.repository;

import com.kalanblow.school_management.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;


import java.util.List;
import java.util.Optional;


public interface StudentRepository {
    Student save(Student student);

    Optional<Student> findByStudentId(Long id);

    Page<Student> findAll(Pageable pageable);

    boolean existsByStudentId(Long id);

    void deleteByStudentId(Long id);

    Page<Student> search(Specification<Student> specification, Pageable pageable);
}
