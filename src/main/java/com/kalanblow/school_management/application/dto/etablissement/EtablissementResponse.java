package com.kalanblow.school_management.application.dto.etablissement;

public record EtablissementResponse(
        Long id,
        String nom,
        String identifiant,
        String email,
        String city,
        String country,
        Long chefEtablissementId
) {}
