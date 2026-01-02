package com.kalanblow.school_management.application.usecase;

import com.kalanblow.school_management.model.Student;
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
        existing.setAge(updated.getAge());
        existing.setDateDeNaissance(updated.getDateDeNaissance());
        existing.setUser(updated.getUser());
        existing.setSchoolClass(updated.getSchoolClass());
        return studentService.updateStudent(id, existing);
    }
}
