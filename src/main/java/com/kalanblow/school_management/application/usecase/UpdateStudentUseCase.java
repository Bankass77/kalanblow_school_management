package com.kalanblow.school_management.application.usecase;

import com.kalanblow.school_management.model.Student;
import com.kalanblow.school_management.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UpdateStudentUseCase {
    private final StudentRepository repository;

    public UpdateStudentUseCase(StudentRepository repository) {
        this.repository = repository;
    }

    public Student execute(Long id, Student updated) {
        Student existing = repository.findByStudentId(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        existing.setFirstName(updated.getFirstName());
        existing.setLastName(updated.getLastName());
        existing.setSchoolClass(updated.getSchoolClass());
        return repository.save(existing);
    }
}
