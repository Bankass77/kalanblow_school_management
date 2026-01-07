package com.kalanblow.school_management.model.note;

import com.kalanblow.school_management.model.anneescolaire.Evaluation;
import com.kalanblow.school_management.model.student.Student;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Entity
@Table(name = "notes")
@Getter
@Setter
public class Note implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluation_id", nullable = false)
    private Evaluation evaluation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Column(name = "valeur_note")
    @DecimalMin(value = "0.0", message = "La note ne peut pas être négative")
    @DecimalMax(value = "20.0", message = "La note ne peut pas dépasser 20")
    private Double valeurNote;

    @Column(name = "est_abscent")
    private boolean estAbscent = false;

    @Column(name = "est_dispense")
    private boolean estDispense = false;

    @Column(name = "commentaire")
    private String commentaire;
}
