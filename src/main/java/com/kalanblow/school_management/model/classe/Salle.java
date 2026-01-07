package com.kalanblow.school_management.model.classe;

import com.kalanblow.school_management.infrastructure.config.InstanceInfo;
import com.kalanblow.school_management.model.professeur.CreneauHoraire;
import com.kalanblow.school_management.model.shared.enums.TypeSalle;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "salles")
@Getter
@Setter
@NoArgsConstructor
public class Salle implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code; // Ex: "SALLE101"

    @Column(nullable = false)
    private String nom; // Ex: "Salle de Sciences"

    @Column(nullable = false)
    private Integer capacite; // Nombre d'élèves que la salle peut accueillir

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeSalle type; // Générale, Laboratoire, Informatique, etc.

    @Column(name = "equipements")
    private String equipements; // Description des équipements

    @Column(name = "est_disponible")
    private boolean disponible = true;

    @Column(name = "commentaires")
    private String commentaires;

    // Relation avec l'instance (multi-instance)
    @Column(name = "instance_id")
    private String instanceId;

    // Relation avec les créneaux horaires (emploi du temps)
    @OneToMany(mappedBy = "salle", fetch = FetchType.LAZY)
    private List<CreneauHoraire> creneaux;

  /*  @PrePersist
    public void prePersist() {
        this.instanceId = InstanceInfo.getCurrentInstanceId();
    }*/
}



