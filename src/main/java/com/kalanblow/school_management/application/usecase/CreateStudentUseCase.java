package com.kalanblow.school_management.application.usecase;

import com.kalanblow.school_management.model.Student;
import com.kalanblow.school_management.service.StudentService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Service
public class CreateStudentUseCase {

    private final StudentService studentService;

    public CreateStudentUseCase(StudentService studentService) {
        this.studentService = studentService;
    }

    @CacheEvict(value = "students-pages", allEntries = true)
    public Student execute(Student student) {

        return studentService.createNewStudent(student);
    }
}
