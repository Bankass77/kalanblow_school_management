package com.kalanblow.school_management.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kalanblow.school_management.model.enums.EtatEleve;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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
    @ManyToOne(fetch = FetchType.LAZY)
    private SchoolClass schoolClass;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "eleve_parent",
            joinColumns = @JoinColumn(name = "eleveId"),
            inverseJoinColumns = @JoinColumn(name = "parentId")
    )
    private Set<Parent> parents = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "etablisementScolaireId", nullable = false, referencedColumnName = "etablisementScolaireId")
    @JsonIgnore
    private Etablissement etablissement;

    @Column
    private LocalDateTime dateInscription = LocalDateTime.now();

    @Column
    @Enumerated(EnumType.STRING)
    @NotNull(message = "{notnull.message}")
    private EtatEleve etat;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "eleve_anneehistorique")
    private Set<AnneeScolaire> historiqueScolaires;

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getIneNumber() {
        return ineNumber;
    }

    public void setIneNumber(String ineNumber) {
        this.ineNumber = ineNumber;
    }

    public LocalDate getDateDeNaissance() {
        return dateDeNaissance;
    }

    public void setDateDeNaissance(LocalDate dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public SchoolClass getSchoolClass() {
        return schoolClass;
    }

    public void setSchoolClass(SchoolClass schoolClass) {
        this.schoolClass = schoolClass;
    }

    public Set<Parent> getParents() {
        return parents;
    }

    public void setParents(Set<Parent> parents) {
        this.parents = parents;
    }

    public Etablissement getEtablissement() {
        return etablissement;
    }

    public void setEtablissement(Etablissement etablissement) {
        this.etablissement = etablissement;
    }

    public LocalDateTime getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(LocalDateTime dateInscription) {
        this.dateInscription = dateInscription;
    }

    public EtatEleve getEtat() {
        return etat;
    }

    public void setEtat(EtatEleve etat) {
        this.etat = etat;
    }

    public Set<AnneeScolaire> getHistoriqueScolaires() {
        return historiqueScolaires;
    }

    public void setHistoriqueScolaires(Set<AnneeScolaire> historiqueScolaires) {
        this.historiqueScolaires = historiqueScolaires;
    }
}
