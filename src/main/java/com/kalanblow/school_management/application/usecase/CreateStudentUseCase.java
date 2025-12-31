package com.kalanblow.school_management.application.usecase;

import com.kalanblow.school_management.model.Student;
import com.kalanblow.school_management.repository.StudentRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateStudentUseCase {

    private final StudentRepository studentRepository;

    public CreateStudentUseCase(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @CacheEvict(value = "students-pages", allEntries = true)
    public Student execute(Student student) {

        return studentRepository.save(student);
    }
}
