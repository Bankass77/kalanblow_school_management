package com.kalanblow.school_management.model.anneescolaire;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.kalanblow.school_management.model.classe.SchoolClass;
import com.kalanblow.school_management.model.student.Student;
import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import lombok.*;

import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "annees_scolaires")
@Getter
@Setter
@NoArgsConstructor
public class AnneeScolaire {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long anneeScolaireId;

    @Column(name = "annee_debut", nullable = false)
    @NotNull(message = "L'année de début est obligatoire")
    @Min(value = 2000, message = "L'année doit être >= 2000")
    private Integer anneeDebut;

    @Column(name = "annee_fin", nullable = false)
    @NotNull(message = "L'année de fin est obligatoire")
    @Min(value = 2000, message = "L'année doit être >= 2000")
    private Integer anneeFin;

    @Column(name = "est_active")
    private boolean estActive = false;

    @Column(name = "date_debut_inscription")
    private LocalDate dateDebutInscription;

    @Column(name = "date_fin_inscription")
    private LocalDate dateFinInscription;

    @Column(name = "date_debut_cours")
    private LocalDate dateDebutCours;

    @Column(name = "date_fin_cours")
    private LocalDate dateFinCours;

    @Column(name = "instance_id")
    private String instanceId;

    @Column(name = "date_cloture")
    LocalDate dateCloture;


    // Validation métier
    @AssertTrue(message = "L'année de fin doit être supérieure à l'année de début")
    public boolean isAnneeValide() {
        return anneeFin > anneeDebut;
    }

    // Méthode utilitaire
    public String getLibelle() {
        return String.format("%d-%d", anneeDebut, anneeFin);
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "anneeScolaire")
    private Set<SchoolClass> classes = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "annee_eleve")
    private Set<Student> eleves = new HashSet<>();

    @AssertTrue(message = "L'année de fin doit être supérieure à l'année de début")
    public boolean isAnneeScolaireValid() {
        if (anneeDebut == null || anneeFin == null) {
            return true;
        }
        return isAnneeValide();
    }

}
