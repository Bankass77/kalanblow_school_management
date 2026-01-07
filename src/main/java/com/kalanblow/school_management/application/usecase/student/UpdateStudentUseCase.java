package com.kalanblow.school_management.application.usecase.student;

import com.kalanblow.school_management.model.student.Student;
import com.kalanblow.school_management.service.StudentService;
import org.springframework.stereotype.Service;

@Service
public class UpdateStudentUseCase {
    private final StudentService studentService;

    public UpdateStudentUseCase(StudentService studentService) {
        this.studentService = studentService;
    }

    public Student execute(Long id, Student updated) {
        Student existing = studentService.getStudentById(id);

        if (existing != null) {

            existing = studentService.updateStudent(id, existing);
        }

        return existing;
    }
}
