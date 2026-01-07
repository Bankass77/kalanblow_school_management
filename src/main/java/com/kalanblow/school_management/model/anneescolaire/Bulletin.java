package com.kalanblow.school_management.model.anneescolaire;

import com.kalanblow.school_management.model.student.Student;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bulletins")
@Getter
@Setter
public class Bulletin implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "periode_id", nullable = false)
    private PeriodeScolaire periode;

    @Column(name = "moyenne_generale")
    private Double moyenneGenerale;

    @Column(name = "classement")
    private Integer classement;

    @Column(name = "appreciations")
    @Lob
    private String appreciations;

    @Column(name = "date_edition")
    private LocalDate dateEdition;

    @OneToMany(mappedBy = "bulletin", cascade = CascadeType.ALL)
    private List<DetailBulletin> details = new ArrayList<>();
}