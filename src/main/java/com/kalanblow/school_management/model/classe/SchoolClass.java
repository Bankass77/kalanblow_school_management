package com.kalanblow.school_management.model.classe;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kalanblow.school_management.model.anneescolaire.AnneeScolaire;
import com.kalanblow.school_management.model.etablissement.Etablissement;
import com.kalanblow.school_management.model.inscription.Inscription;
import com.kalanblow.school_management.model.inscription.StatutInscription;
import com.kalanblow.school_management.model.professeur.CreneauHoraire;
import com.kalanblow.school_management.model.professeur.Professeur;
import com.kalanblow.school_management.model.shared.enums.TypeSalle;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "school_classes")
public class SchoolClass implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long schoolClassId;

    @Column(unique = true)
    private String name;

    @Column(precision = 2)
    @NotNull(message = "La capacité ne peut pas être nulle")
    @Positive(message = "La capacité doit être positif")
    private int capacity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "etablisementScolaireId", nullable = false)
    @NotNull(message = "{notnull.message}")
    @JsonIgnore
    private Etablissement etablissement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "anneeScolaireId", nullable = true)
    @JsonIgnore
    private AnneeScolaire anneeScolaire;

    @OneToMany(mappedBy = "schoolClass", fetch = FetchType.LAZY)
    private List<Inscription> inscriptions = new ArrayList<>();

    // Nouveaux champs pour les ressources
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "salle_attachee_id")
    private Salle salleAttachee; // Salle attitrée (si applicable)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professeur_principal_id")
    private Professeur professeurPrincipal;

    @Column(name = "besoin_salle_specifique")
    private boolean besoinSalleSpecifique = false;

    @Column(name = "type_salle_requis")
    @Enumerated(EnumType.STRING)
    private TypeSalle typeSalleRequis = TypeSalle.GENERALE;

    // Relation avec les créneaux horaires
    @OneToMany(mappedBy = "schoolClass", fetch = FetchType.LAZY)
    @OrderBy("jourSemaine, heureDebut")
    private List<CreneauHoraire> emploiDuTemps = new ArrayList<>();

    // Méthode pour vérifier si la classe a une salle assignée
    public boolean aSalleAssignee() {
        return salleAttachee != null && salleAttachee.isDisponible();
    }

    // Méthode pour vérifier si la classe a un professeur principal
    public boolean aProfesseurPrincipal() {
        return professeurPrincipal != null && professeurPrincipal.isDisponible();
    }

    @Transient
    public Integer getEffectifActuel() {
        return (int) inscriptions.stream()
                .filter(i -> i.getStatut() == StatutInscription.EN_COURS)
                .count();
    }

    @Transient
    public Integer getTauxOccupation() {
        return (getEffectifActuel() * 100) / capacity;
    }


}