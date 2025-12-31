package com.kalanblow.school_management.application.mapper;

import com.kalanblow.school_management.application.dto.StudentRequest;
import com.kalanblow.school_management.application.dto.StudentResponse;
import com.kalanblow.school_management.model.Student;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    Student toEntity(StudentRequest request);

    StudentResponse toResponse(Student student);


}
