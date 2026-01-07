package com.kalanblow.school_management.application.mapper;

import com.kalanblow.school_management.application.dto.note.AbscenceRequest;
import com.kalanblow.school_management.application.dto.note.AbscenceResponse;
import com.kalanblow.school_management.model.anneescolaire.Abscence;
import jakarta.validation.Valid;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AbscenceMapper {
    Abscence toEntity(@Valid AbscenceRequest abscenceRequest);
    AbscenceResponse toResponse(Abscence abscence);
}
