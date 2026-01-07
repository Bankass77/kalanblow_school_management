package com.kalanblow.school_management.repository;

import com.kalanblow.school_management.model.student.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface StudentRepository {
    Student save(Student student);

    Optional<Student> findByStudentId(Long id);

    boolean existsByStudentId(Long id);

    void deleteByStudentId(Long id);

    Page<Student> search(Specification<Student> specification, Pageable pageable);

    boolean existsByIneNumber(String ineNumber);
    List<Student> findByClassId(String classId);

    Long count();

    Long countBySchoolClass(Object cls);
}
