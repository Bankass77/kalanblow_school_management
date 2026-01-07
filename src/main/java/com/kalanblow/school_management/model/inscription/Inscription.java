package com.kalanblow.school_management.model.inscription;

import com.kalanblow.school_management.infrastructure.config.InstanceInfo;
import com.kalanblow.school_management.model.anneescolaire.AnneeScolaire;
import com.kalanblow.school_management.model.classe.SchoolClass;
import com.kalanblow.school_management.model.student.Student;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "inscriptions")
@Getter
@Setter
@NoArgsConstructor
public class Inscription implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    @NotNull(message = "L'élève est obligatoire")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "annee_scolaire_id", nullable = false)
    @NotNull(message = "L'année scolaire est obligatoire")
    private AnneeScolaire anneeScolaire;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_class_id")
    private SchoolClass schoolClass;

    @Column(name = "date_inscription")
    private LocalDateTime dateInscription = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false)
    @NotNull(message = "Le statut de l'inscription est obligatoire")
    private StatutInscription statut = StatutInscription.EN_COURS;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_admission")
    private TypeAdmission typeAdmission;

    @Column(name = "est_redoublant")
    private boolean estRedoublant = false;

    @Column(name = "classe_precedente")
    private String classePrecedente;

    @Column(name = "moyenne_generale")
    private Double moyenneGenerale;

    @Column(name = "decision_conseil")
    private String decisionConseil;

    @Column(name = "date_decision")
    private LocalDate dateDecision;

    // Relation avec l'établissement (instance)
    @Column(name = "instance_id")
    private String instanceId;

    @PrePersist
    public void prePersist() {
        this.instanceId = new InstanceInfo().getCurrentInstanceId();
    }
}



