package com.kalanblow.school_management.infrastructure.persistence.student;

import com.kalanblow.school_management.model.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface JpaStudentRepository extends JpaRepository<Student,Long> , JpaSpecificationExecutor<Student> {

    Optional<Student> getStudentsByStudentId(long studentId);

    boolean existsStudentByStudentId(long studentId);
}
