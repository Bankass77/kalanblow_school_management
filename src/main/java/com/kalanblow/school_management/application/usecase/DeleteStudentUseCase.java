package com.kalanblow.school_management.application.usecase;

import com.kalanblow.school_management.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DeleteStudentUseCase {
    private final StudentRepository repository;

    public DeleteStudentUseCase(StudentRepository repository) {
        this.repository = repository;
    }

    public void execute(Long id) {
        if (!repository.existsByStudentId(id)) {
            throw new EntityNotFoundException("Student not found with id " + id);
        }
        repository.deleteByStudentId(id);
    }
}
