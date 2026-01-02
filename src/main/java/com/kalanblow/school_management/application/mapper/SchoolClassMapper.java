package com.kalanblow.school_management.application.mapper;

import com.kalanblow.school_management.application.dto.SchoolClassRequest;
import com.kalanblow.school_management.application.dto.SchoolClassResponse;
import com.kalanblow.school_management.model.SchoolClass;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SchoolClassMapper {

    SchoolClass toEntity(SchoolClassRequest request);

    SchoolClassResponse toResponse(SchoolClass schoolClass);
}
