package com.kalanblow.school_management.service;

import com.kalanblow.school_management.model.Student;
import com.kalanblow.school_management.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    /**
     *
     * @param student to be created
     * @return a new student
     */
    @CacheEvict(value = "student-pages", allEntries = true)
    @Transactional
    public Student createNewStudent(Student student) {

        return studentRepository.save(student);
    }

    /**
     *
     * @param id , student id
     * @return a student othewise thow an EntityNotFoundException
     */
    @CacheEvict(value = "student-pages", allEntries = true)
    @Transactional
    public Student getStudentById(Long id) {
        return studentRepository.findByStudentId(id).orElseThrow(() -> new EntityNotFoundException("Student not found with id " + id));
    }

    /**
     *
     * @param id student's id
     * @param updated ,student updated
     * @return a student update
     */
    @CacheEvict(value = "student-pages", allEntries = true)
    @Transactional
    public Student updateStudent(Long id, Student updated) {
        Student existing = studentRepository.findByStudentId(id).orElseThrow(() -> new EntityNotFoundException("Student not found with id " + id));
        existing.getUser().getUserName().setFirstName(updated.getUser().getUserName().getFirstName());
        existing.getUser().getUserName().setLastName(updated.getUser().getUserName().getLastName());
        return studentRepository.save(existing);
    }

    /**
     *
     * @param id student id to be deleted
     */
    @CacheEvict(value = "student-pages", allEntries = true)
    @Transactional
    public void deleteStudentById(Long id) {
        if (!studentRepository.existsByStudentId(id)) {
            throw new EntityNotFoundException("Student not found with id " + id);
        }
        studentRepository.deleteByStudentId(id);
    }

    public Page<Student> search(Specification<Student> spec, Pageable pageable) {
        // Si spec est null, on le remplace par une spécification qui ne filtre rien
        Specification<Student> specification = spec==null? Specification.where((root, query, cb)-> null):spec;
        return studentRepository.search(specification,pageable);
    }
}
