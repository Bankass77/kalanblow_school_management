package com.kalanblow.school_management.application.dto.inscription;

import com.kalanblow.school_management.model.shared.enums.GraviteSanction;
import com.kalanblow.school_management.model.shared.enums.StatutSanction;
import com.kalanblow.school_management.model.shared.enums.TypeSanction;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "sanctions")
@Getter
@Setter
public class Sanction implements Serializable {
   @Id
   private Long id;

   @Column(name = "student_id", nullable = false)
   private Long studentId;

   @Enumerated(EnumType.STRING)
   @Column(nullable = false)
   private TypeSanction type;

   @Enumerated(EnumType.STRING)
   @Column(nullable = false)
   private GraviteSanction gravite;

   @Column(nullable = false, length = 500)
   private String motif;

   @Column(columnDefinition = "TEXT")
   private String description;

   @Column(name = "date_sanction", nullable = false)
   private LocalDate dateSanction;

   @Column(name = "date_debut")
   private LocalDate dateDebut;

   @Column(name = "date_fin")
   private LocalDate dateFin;

   @Column(name = "date_levee")
   private LocalDate dateLevee;

   @Column(name = "notifier_parents")
   private boolean notifierParents;

   @Column(name = "mesures_accompagnement")
   private String mesuresAccompagnement;

   @Column(name = "rapport_incident", columnDefinition = "TEXT")
   private String rapportIncident;

   @ElementCollection
   @CollectionTable(name = "sanction_pieces_jointes",
           joinColumns = @JoinColumn(name = "sanction_id"))
   @Column(name = "fichier_id")
   private Set<String> piecesJointes = new HashSet<>();

   @Column(name = "auteur_sanction_id")
   private String auteurSanctionId;

   @Column(name = "signataire_id")
   private String signataireId;

   @Column(name = "appliquee")
   private boolean appliquee;

   @Column(name = "contestee")
   private boolean contestee;

   @Column(name = "motif_contestation")
   private String motifContestation;

   @Column(name = "date_contestation")
   private LocalDateTime dateContestation;

   @Enumerated(EnumType.STRING)
   @Column(nullable = false)
   private StatutSanction statut;

   public boolean estEnCours() {
      return this.appliquee &&
              this.dateDebut != null &&
              this.dateFin != null &&
              LocalDate.now().isAfter(this.dateDebut) &&
              LocalDate.now().isBefore(this.dateFin);
   }

   public boolean estLevee() {
      return this.dateLevee != null &&
              LocalDate.now().isAfter(this.dateLevee);
   }
}
