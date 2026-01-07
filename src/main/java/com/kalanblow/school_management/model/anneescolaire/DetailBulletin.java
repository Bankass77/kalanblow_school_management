package com.kalanblow.school_management.model.anneescolaire;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "detail_bulletin")
@Getter
@Setter
@NoArgsConstructor
public class DetailBulletin implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bulletin_id", nullable = false)
    private Bulletin bulletin;

    // Exemple de champs pour un détail de bulletin
    @Column(name = "matiere")
    private String matiere;

    @Column(name = "note")
    private Double note;

    @Column(name = "coefficient")
    private Integer coefficient;

    @Column(name = "appreciation")
    private String appreciation;

    @Column(name = "rang_classe")
    private Integer rangClasse;

    @ManyToMany
    @JoinTable(
            name = "detail_bulletin_annee_scolaire",
            joinColumns = @JoinColumn(name = "detail_bulletin_id"),
            inverseJoinColumns = @JoinColumn(name = "annee_scolaire_id")
    )
    private Set<AnneeScolaire> anneesScolaires = new HashSet<>();


}