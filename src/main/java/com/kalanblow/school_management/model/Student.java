package com.kalanblow.school_management.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "students")
@Getter
@Setter
public class Student implements Serializable {
    private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long studentId;

    @Embedded
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private SchoolClass schoolClass;

    @Column(name = "ine_number")
    @NotNull(message = "{notnull.message}")
    private String ineNumber;

    @NotNull(message = "{notnull.message}")
    @Column(name = "birthDate")
    @Past
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateDeNaissance;

    @Column(name = "age")
    @NotNull(message = "{notnull.message}")
    private int age;

}
