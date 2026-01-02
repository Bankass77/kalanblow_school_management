package com.kalanblow.school_management.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Entity
@Table(name = "anneeScolaire")
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class AnneeScolaire {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long anneeScolaireId;

    @NotNull(message = "L'année scolaire de début est obligatoire")
    @Min(value = 2000, message = "L'année scolaire doit être supérieure ou égale à 2000")
    private Integer anneeScolaireDebut;

    @NotNull(message = "L'année scolaire de fin est obligatoire")
    @Min(value = 2000, message = "L'année scolaire doit être supérieure ou égale à 2000")
    private Integer anneeScolaireFin;


    @AssertTrue(message = "L'année de fin doit être supérieure à l'année de début")
    public boolean isAnneeScolaireValid() {
        if (anneeScolaireDebut == null || anneeScolaireFin == null) {
            return true;
        }
        return anneeScolaireFin > anneeScolaireDebut;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "anneeScolaire")
    private Set<SchoolClass> classes = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "annee_eleve")
    private Set<Student> eleves = new HashSet<>();
}
