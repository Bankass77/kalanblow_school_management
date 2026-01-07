package com.kalanblow.school_management.application.usecase.student;

import com.kalanblow.school_management.service.StudentService;
import org.springframework.stereotype.Service;

@Service
public class DeleteStudentUseCase {

    private final StudentService studentService;

    public DeleteStudentUseCase(StudentService studentService) {
        this.studentService = studentService;
    }

    public void execute(Long id) {
        studentService.deleteStudentById(id);
    }
}
