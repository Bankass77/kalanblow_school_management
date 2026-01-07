package com.kalanblow.school_management.application.mapper;

import com.kalanblow.school_management.application.dto.etablissement.ChefEtablissementResponse;
import com.kalanblow.school_management.model.etablissement.ChefEtablissement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChefEtablissementMapper {

    @Mapping(source="userName", target = "userName")
    ChefEtablissementResponse toResponse(ChefEtablissement chef);
}

