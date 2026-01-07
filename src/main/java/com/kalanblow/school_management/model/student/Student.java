package com.kalanblow.school_management.model.student;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kalanblow.school_management.application.dto.inscription.Sanction;
import com.kalanblow.school_management.model.anneescolaire.AnneeScolaire;
import com.kalanblow.school_management.model.classe.SchoolClass;
import com.kalanblow.school_management.model.etablissement.Etablissement;
import com.kalanblow.school_management.model.inscription.Inscription;
import com.kalanblow.school_management.model.inscription.StatutInscription;
import com.kalanblow.school_management.model.inscription.TypeAdmission;
import com.kalanblow.school_management.model.parent.Parent;
import com.kalanblow.school_management.model.shared.User;
import com.kalanblow.school_management.model.shared.enums.EtatEleve;
import com.kalanblow.school_management.model.shared.util.CalculateUserAge;
import com.kalanblow.school_management.model.shared.util.KaladewnUtility;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "students")
@Getter
@Setter
public class Student implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long studentId;

    @Column(name = "instance_id")
    private String instanceId;

    @Embedded
    private User user;

    @Column(name = "ine_number")
    @NotNull(message = "{notnull.message}")
    private String ineNumber =KaladewnUtility.generatingandomAlphaNumericStringWithFixedLength();

    @NotNull(message = "{notnull.message}")
    @Column(name = "birthDate")
    @Past
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateDeNaissance;

    @NotNull(message = "{notnull.message}")
    @Column(name = "dateArchivage")
    @Past
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateArchivage;

    @Column(name = "age")
    @NotNull(message = "{notnull.message}")
    private int age = CalculateUserAge.calculateAge(dateDeNaissance);

    @ManyToOne(fetch = FetchType.LAZY)
    private SchoolClass schoolClass;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "student_parent",
            joinColumns = @JoinColumn(name = "studentId"),
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
    @JoinColumn(name = "student_anneehistorique")
    private Set<AnneeScolaire> historiqueScolaires;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Inscription> inscriptions = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "student_sanctions",
            joinColumns = @JoinColumn(name = "studentId"),
            inverseJoinColumns = @JoinColumn(name = "parentId")
    )
    private List<Sanction>  sanctions = new ArrayList<>();

   private  boolean disponible;

    @Transient
    public Inscription getInscriptionActive() {
        return inscriptions.stream()
                .filter(i -> i.getStatut() == StatutInscription.EN_COURS)
                .findFirst()
                .orElse(null);
    }

    @Transient
    public boolean estRedoublant() {
        return inscriptions.stream()
                .anyMatch(i -> i.isEstRedoublant() &&
                        i.getAnneeScolaire().isEstActive());
    }

    // Méthode pour ajouter une nouvelle inscription
    public void addInscription(AnneeScolaire annee, SchoolClass classe,
                               TypeAdmission type, boolean redoublant) {
        Inscription inscription = new Inscription();
        inscription.setStudent(this);
        inscription.setAnneeScolaire(annee);
        inscription.setSchoolClass(classe);
        inscription.setTypeAdmission(type);
        inscription.setEstRedoublant(redoublant);
        inscription.setStatut(StatutInscription.EN_COURS);

        // Désactiver l'ancienne inscription active
        inscriptions.stream()
                .filter(i -> i.getStatut() == StatutInscription.EN_COURS)
                .forEach(i -> i.setStatut(StatutInscription.TRANSFERE));

        inscriptions.add(inscription);
    }
}
