package com.kalanblow.school_management.model.anneescolaire;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kalanblow.school_management.model.shared.enums.TypePeriode;
import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "periodes_scolaires")
@Getter
@Setter
@NoArgsConstructor
public class PeriodeScolaire implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom; // Ex: "Trimestre 1", "Semestre 1", "Vacances de Noël"

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypePeriode type; // TRIMESTRE, SEMESTRE, VACANCES, EXAMEN, etc.

    @Column(nullable = false)
    private LocalDate dateDebut;

    @Column(nullable = false)
    private LocalDate dateFin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "annee_scolaire_id", nullable = false)
    @JsonIgnore
    private AnneeScolaire anneeScolaire;

    @Column(name = "est_periode_cours")
    private boolean periodeCours = true;

    @Column(name = "ordre_affichage")
    private Integer ordre;

    @AssertTrue(message = "La date de fin doit être après la date de début")
    public boolean isDatesValides() {
        return dateFin.isAfter(dateDebut);
    }

    @AssertTrue(message = "La période chevauche une autre période")
    public boolean isPasDeChevauchement() {
        //TODO: Implémenter la logique de vérification
        return true;
    }
}


