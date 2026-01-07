package com.kalanblow.school_management.application.usecase.etablissement;

public record EtablissementPageCacheKey(
        String nom,
        String email,
        String address,
        Long identifiant,
        String phoneNumber,
        int page,
        int size
) {}
