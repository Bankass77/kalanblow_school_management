package com.kalanblow.school_management.application.mapper;

import com.kalanblow.school_management.application.dto.anneescolaire.AnneeScolaireRequest;
import com.kalanblow.school_management.application.dto.anneescolaire.AnneeScolaireResponse;
import com.kalanblow.school_management.model.anneescolaire.AnneeScolaire;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AnneeScolaireMapper {

     AnneeScolaire toEntity(AnneeScolaireRequest request) ;

   AnneeScolaireResponse toResponse(AnneeScolaire entity);
}

