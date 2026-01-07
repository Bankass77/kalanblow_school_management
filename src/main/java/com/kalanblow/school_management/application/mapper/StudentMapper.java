package com.kalanblow.school_management.application.mapper;

import com.kalanblow.school_management.application.dto.student.StudentRequest;
import com.kalanblow.school_management.application.dto.student.StudentResponse;
import com.kalanblow.school_management.model.student.Student;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    Student toEntity(StudentRequest request);

    StudentResponse toResponse(Student student);

}
