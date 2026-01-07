package com.kalanblow.school_management.application.usecase.anneescolaire;

public record AnneeScolairePageCacheKey (int page,
                                         int size,
                                         Integer debut,
                                         Integer fin,
                                         Long anneeScolaireId){
}
