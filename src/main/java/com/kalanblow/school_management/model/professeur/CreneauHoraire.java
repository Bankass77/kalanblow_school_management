package com.kalanblow.school_management.model.professeur;

import com.kalanblow.school_management.model.classe.Salle;
import com.kalanblow.school_management.model.classe.SchoolClass;
import com.kalanblow.school_management.model.shared.enums.JourSemaine;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "creneaux_horaires")
@Getter
@Setter
@NoArgsConstructor
public class CreneauHoraire implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_class_id", nullable = false)
    private SchoolClass schoolClass;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professeur_id", nullable = false)
    private Professeur professeur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "salle_id", nullable = false)
    private Salle salle;

    @Column(name = "jour_semaine")
    @Enumerated(EnumType.STRING)
    private JourSemaine jourSemaine;

    @Column(name = "heure_debut")
    private LocalTime heureDebut;

    @Column(name = "heure_fin")
    private LocalTime heureFin;

    @Column(name = "matiere")
    private String matiere;

    @Column(name = "est_regulier")
    private boolean regulier = true; // false pour les créneaux exceptionnels

    @Column(name = "date_specifique")
    private LocalDate dateSpecifique; // pour les créneaux non réguliers

    // Relation avec l'instance
    @Column(name = "instance_id")
    private String instanceId;

    /*@PrePersist
    public void prePersist() {
        this.instanceId = getCurrentInstanceId();
    }*/
}

