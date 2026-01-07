package com.kalanblow.school_management.model.anneescolaire;

import com.kalanblow.school_management.model.matiere.Matiere;
import com.kalanblow.school_management.model.student.Student;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

@Entity
@Table(name = "evaluations")
@Getter
@Setter
public class Evaluation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "periode_id", nullable = false)
    private PeriodeScolaire periode; // À quel trimestre/semestre appartient l'évaluation

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matiere_id", nullable = false)
    private Matiere matiere;

    private Double note;
    private Double coefficient;
    private String commentaire;

    @Column(name = "date_evaluation")
    private LocalDate dateEvaluation;
}


