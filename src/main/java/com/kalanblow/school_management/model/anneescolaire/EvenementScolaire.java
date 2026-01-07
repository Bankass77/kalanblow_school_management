package com.kalanblow.school_management.model.anneescolaire;

import com.kalanblow.school_management.model.shared.enums.TypeEvenement;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "evenements_scolaires")
@Getter
@Setter
public class EvenementScolaire implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;
    private String description;

    @Enumerated(EnumType.STRING)
    private TypeEvenement type; // CONSEIL, EXAMEN, FETE, REUNION, etc.

    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "periode_id")
    private PeriodeScolaire periode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "annee_scolaire_id")
    private AnneeScolaire anneeScolaire;

    private boolean important; // Pour mise en avant
}
