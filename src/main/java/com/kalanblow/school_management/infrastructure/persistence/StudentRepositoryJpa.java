package com.kalanblow.school_management.infrastructure.persistence;

import com.kalanblow.school_management.model.Student;
import com.kalanblow.school_management.repository.StudentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

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
    public Page<Student> findAll(Pageable pageable) {
        return jpaStudentRepository.findAll(pageable);
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
}
