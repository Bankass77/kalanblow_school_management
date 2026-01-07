package com.kalanblow.school_management.application.mapper;

import com.kalanblow.school_management.application.dto.inscription.Sanction;
import com.kalanblow.school_management.application.dto.sanction.SanctionRequest;
import com.kalanblow.school_management.application.dto.sanction.SanctionResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SanctionMapper {
    Sanction toEntity(SanctionRequest request);
    SanctionResponse toResponse(Sanction sanction);
}
