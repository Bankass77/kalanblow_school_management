package com.kalanblow.school_management.service;

import com.kalanblow.school_management.model.Student;
import com.kalanblow.school_management.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    /**
     *
     * @param student
     * @return
     */
    public Student createNewStudent(Student student) {

        return studentRepository.save(student);
    }

    /**
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public Student getStudentById(Long id) {
        return studentRepository.findByStudentId(id).orElseThrow(() -> new EntityNotFoundException("Student not found with id " + id));
    }

    /**
     *
     * @return
     */
    @Transactional(readOnly = true)
    public Page<Student> getAllStudents(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }

    /**
     *
     * @param id
     * @param updated
     * @return
     */
    public Student updateStudent(Long id, Student updated) {
        Student existing = studentRepository.findByStudentId(id).orElseThrow(() -> new EntityNotFoundException("Student not found with id " + id));
        existing.setFirstName(updated.getFirstName());
        existing.setLastName(updated.getLastName());
        return studentRepository.save(existing);
    }

    /**
     *
     * @param id
     */
    public void deleteStudentById(Long id) {
        if (!studentRepository.existsByStudentId(id)) {
            throw new EntityNotFoundException("Student not found with id " + id);
        }
        studentRepository.deleteByStudentId(id);
    }
}
