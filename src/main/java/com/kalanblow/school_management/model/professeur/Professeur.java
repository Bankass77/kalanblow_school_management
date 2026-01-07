package com.kalanblow.school_management.model.professeur;

import com.kalanblow.school_management.model.classe.SchoolClass;
import com.kalanblow.school_management.model.shared.User;
import com.kalanblow.school_management.model.shared.enums.StatutProfesseur;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "professeurs")
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(ProfesseurEntityListener.class)
public class Professeur implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @NotNull
    private User user; // Réutilisation de l'entité User existante

    @Column(name = "matiere_principale")
    private String matierePrincipale;

    @ElementCollection
    @CollectionTable(name = "professeur_matieres", joinColumns = @JoinColumn(name = "professeur_id"))
    @Column(name = "matiere")
    private Set<String> matieresEnseignees = new HashSet<>();

    @Column(name = "statut")
    @Enumerated(EnumType.STRING)
    private StatutProfesseur statut = StatutProfesseur.TITULAIRE;

    @Column(name = "temps_travail") // en pourcentage (ex: 80 pour 80%)
    private Integer tempsTravail = 100;

    @Column(name = "date_embauche")
    private LocalDate dateEmbauche;

    @Column(name = "date_depart")
    private LocalDate dateDepart;

    @Column(name = "est_disponible")
    private boolean disponible = true;

    @Column(name = "charge_max_hebdo") // heures par semaine
    private Integer chargeMaxHebdo = 20;

    @Column(name = "charge_actuelle_hebdo")
    private Integer chargeActuelleHebdo = 0;

    // Relation avec les classes dont il est professeur principal
    @OneToOne(mappedBy = "professeurPrincipal", fetch = FetchType.LAZY)
    private SchoolClass classePrincipale;

    // Relation avec les créneaux horaires (emploi du temps)
    @OneToMany(mappedBy = "professeur", fetch = FetchType.LAZY)
    private List<CreneauHoraire> creneaux;

    // Relation avec l'instance
    @Column(name = "instance_id")
    private String instanceId;

    // Méthodes utilitaires
    public boolean peutEnseigner(String matiere) {
        return matieresEnseignees.contains(matiere);
    }

    public boolean aCapacite(Integer heuresSupplementaires) {
        return chargeActuelleHebdo + heuresSupplementaires <= chargeMaxHebdo;
    }
}


