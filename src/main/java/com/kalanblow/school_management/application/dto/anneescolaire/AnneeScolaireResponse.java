package com.kalanblow.school_management.application.dto.anneescolaire;

public record AnneeScolaireResponse(
        Long id,
        Integer anneeDebut,
        Integer anneeFin,
        boolean active
) {}

