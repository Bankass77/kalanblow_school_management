package com.kalanblow.school_management.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "classes")
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

    @Transient
    private Long etablissementId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "anneeScolaireId", nullable = true)
    @JsonIgnore
    private AnneeScolaire anneeScolaire;

    public Long getSchoolClassId() {
        return schoolClassId;
    }

    public void setSchoolClassId(Long schoolClassId) {
        this.schoolClassId = schoolClassId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public AnneeScolaire getAnneeScolaire() {
        return anneeScolaire;
    }

    public void setAnneeScolaire(AnneeScolaire anneeScolaire) {
        this.anneeScolaire = anneeScolaire;
    }
}