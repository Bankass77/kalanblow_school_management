package com.kalanblow.school_management.infrastructure.persistence.student;

import com.kalanblow.school_management.model.student.Student;
import com.kalanblow.school_management.repository.StudentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class StudentRepositoryJpa implements StudentRepository {

    private final JpaStudentRepository jpaStudentRepository;

    public StudentRepositoryJpa(JpaStudentRepository jpaStudentRepository) {
        this.jpaStudentRepository = jpaStudentRepository;
    }

    @Override
    public Student save(Student student) {
        return jpaStudentRepository.save(student);
    }

    @Override
    public Optional<Student> findByStudentId(Long id) {
        return jpaStudentRepository.getStudentsByStudentId(id);
    }

    @Override
    public boolean existsByStudentId(Long id) {
        return jpaStudentRepository.existsStudentByStudentId(id);
    }

    @Override
    public void deleteByStudentId(Long id) {
        jpaStudentRepository.deleteById(id);
    }

    @Override
    public Page<Student> search(Specification<Student> specification, Pageable pageable) {
        return jpaStudentRepository.findAll(specification, pageable);
    }

    @Override
    public boolean existsByIneNumber(String ineNumber) {
        return false;
    }

    @Override
    public List<Student> findByClassId(String classId) {
        return List.of();
    }

    @Override
    public Long count() {
        return (long) jpaStudentRepository.findAll().size();
    }

    @Override
    public Long countBySchoolClass(Object cls) {
        return 0L;
    }
}
