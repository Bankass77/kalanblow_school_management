package com.kalanblow.school_management.application.dto.etablissement;

public record EtablissementRequest( String nom,
                                    String identifiant,
                                    String email,
                                    String city,
                                    String country) {
}
