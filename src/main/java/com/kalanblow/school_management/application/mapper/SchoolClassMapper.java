package com.kalanblow.school_management.application.mapper;

import com.kalanblow.school_management.application.dto.classe.SchoolClassRequest;
import com.kalanblow.school_management.application.dto.classe.SchoolClassResponse;
import com.kalanblow.school_management.model.classe.SchoolClass;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SchoolClassMapper {

    SchoolClass toEntity(SchoolClassRequest request);

    SchoolClassResponse toResponse(SchoolClass schoolClass);

}
