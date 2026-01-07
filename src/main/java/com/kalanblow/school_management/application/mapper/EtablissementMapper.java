package com.kalanblow.school_management.application.mapper;

import com.kalanblow.school_management.application.dto.etablissement.EtablissementRequest;
import com.kalanblow.school_management.application.dto.etablissement.EtablissementResponse;
import com.kalanblow.school_management.model.etablissement.Etablissement;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EtablissementMapper {

    EtablissementResponse toResponse(Etablissement etablissement);

    Etablissement toEntity(EtablissementRequest request);
}
