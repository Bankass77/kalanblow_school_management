package com.kalanblow.school_management.application.usecase.student;

import com.kalanblow.school_management.model.student.Student;
import com.kalanblow.school_management.service.StudentService;
import org.springframework.stereotype.Service;

@Service
public class GetStudentUseCase {
    private final StudentService studentService;

    public GetStudentUseCase(StudentService studentService) {
        this.studentService = studentService;
    }

    public Student execute(Long id) {

        return studentService.getStudentById(id);
    }
}
