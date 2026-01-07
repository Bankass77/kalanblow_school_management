package com.kalanblow.school_management.model.matiere;

import com.kalanblow.school_management.model.shared.enums.NiveauMatiere;
import com.kalanblow.school_management.model.shared.enums.TypeMatiere;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "matieres")
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(MatiereEntityListener.class)
public class Matiere  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long matiereId;

    @Column
    private String code; // Ex: "MATHS-6EME", unique
    @Column
    private String nom;  // Ex: "Mathématiques"
    @Column
    private String description;
    @Column
    private TypeMatiere type;
    @Column
    private NiveauMatiere niveau;
    @Column
    private Integer coefficient;
    @Column
    private Integer volumeHoraireAnnuel; // en heures
    @Column
    private Integer volumeHoraireHebdo;  // en heures
    @Column(name = "instance_id")
    private String instanceId;
    // Relations
    private Set<Long> prerequisMatiereIds = new HashSet<>();
    private Set<Long> matieresAssociees = new HashSet<>(); // Matières liées
    private String programmeId; // Référence au programme officiel

    // Métadonnées
    private String colorCode; // Pour l'affichage (ex: "#FF5733")
    private String iconName;  // Pour l'UI (ex: "calculator", "book")
    private boolean active = true;
    private LocalDateTime dateCreation = LocalDateTime.now();
    private LocalDateTime dateMiseAJour = LocalDateTime.now();

    // Factory Method Principale
    public static Matiere creer(
            String code,
            String nom,
            TypeMatiere type,
            NiveauMatiere niveau,
            Integer coefficient,
            Integer volumeHoraireAnnuel) {

        Matiere matiere = new Matiere();
        matiere.code = code;
        matiere.nom = nom;
        matiere.type = type;
        matiere.niveau = niveau;
        matiere.coefficient = coefficient;
        matiere.volumeHoraireAnnuel = volumeHoraireAnnuel;

        // Calcul automatique du volume hebdo (sur 36 semaines)
        matiere.volumeHoraireHebdo = (int) Math.ceil(volumeHoraireAnnuel / 36.0);

        // Valeurs par défaut
        matiere.colorCode = generateDefaultColor(matiere.type);
        matiere.iconName = generateDefaultIcon(matiere.type);

        // Validation métier
        matiere.valider();

        // Événement de domaine
        matiere.registerEvent(new MatiereCreeeEvent(matiere.getMatiereId(), matiere.nom, matiere.type));

        return matiere;
    }

    private void registerEvent(MatiereCreeeEvent matiereCreeeEvent) {
    }
    private void registerEvent(MatiereDesactiveeEvent matiereCreeeEvent) {
    }
    private void registerEvent(MatiereReactiveeEvent matiereCreeeEvent) {
    }

    // Factory pour matières fondamentales
    public static Matiere creerMatiereFondamentale(
            String code,
            String nom,
            NiveauMatiere niveau,
            Integer volumeHoraireAnnuel) {

        return creer(
                code,
                nom,
                TypeMatiere.FONDAMENTALE,
                niveau,
                (int) TypeMatiere.FONDAMENTALE.getCoefficientBase(),
                volumeHoraireAnnuel
        );
    }

    // Méthodes métier
    private void valider() {
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("Le code de la matière est obligatoire");
        }

        if (nom == null || nom.trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom de la matière est obligatoire");
        }

        if (coefficient == null || coefficient <= 0) {
            throw new IllegalArgumentException("Le coefficient doit être positif");
        }

        if (volumeHoraireAnnuel == null || volumeHoraireAnnuel <= 0) {
            throw new IllegalArgumentException("Le volume horaire annuel doit être positif");
        }

        // Validation spécifique par type
        if (type == TypeMatiere.FONDAMENTALE && coefficient < 3) {
            throw new IllegalArgumentException("Les matières fondamentales doivent avoir un coefficient ≥ 3");
        }
    }

    public void ajouterPrerequis(Long prerequisId) {
        if (prerequisId.equals(this.matiereId)) {
            throw new IllegalArgumentException("Une matière ne peut pas être son propre prérequis");
        }
        prerequisMatiereIds.add(prerequisId);
        dateMiseAJour = LocalDateTime.now();
    }

    public void ajouterMatiereAssociee(Long matiereId) {
        matieresAssociees.add(matiereId);
        dateMiseAJour = LocalDateTime.now();
    }

    public void mettreAJourCoefficient(Integer nouveauCoefficient) {
        if (type.estCoefficientFixe() && !nouveauCoefficient.equals(this.coefficient)) {
            throw new IllegalArgumentException(
                    "Le coefficient des matières " + type.getLibelle() + " ne peut pas être modifié"
            );
        }

        this.coefficient = nouveauCoefficient;
        dateMiseAJour = LocalDateTime.now();

        registerEvent(new MatiereCoefficientModifieEvent(matiereId, nom, coefficient));
    }

    private void registerEvent(MatiereCoefficientModifieEvent matiereCoefficientModifieEvent) {
    }

    public void desactiver() {
        this.active = false;
        dateMiseAJour = LocalDateTime.now();
        registerEvent(new MatiereDesactiveeEvent(String.valueOf(matiereId), nom));
    }

    public void reactiver() {
        this.active = true;
        dateMiseAJour = LocalDateTime.now();
        registerEvent(new MatiereReactiveeEvent(String.valueOf(matiereId), nom));
    }

    // Méthodes de calcul
    public double calculerPoidsDansMoyenne() {
        double poids = coefficient;

        // Ajustement selon le type
        switch (type) {
            case FONDAMENTALE:
                poids *= 1.5;
                break;
            case PROJET:
                poids *= 1.2;
                break;
        }

        return poids;
    }

    public boolean estDisponiblePourNiveau(NiveauMatiere niveauEleve) {
        // Logique pour vérifier si la matière est adaptée au niveau
        return this.niveau == niveauEleve ||
                (this.niveau == NiveauMatiere.COLLEGE && niveauEleve == NiveauMatiere.LYCEE_GENERAL);
    }

    // Méthodes utilitaires privées
    private static String generateDefaultColor(TypeMatiere type) {
        switch (type) {
            case FONDAMENTALE:
                return "#FF6B6B";
            case OBLIGATOIRE:
                return "#4ECDC4";
            case OPTIONNELLE:
                return "#FFD166";
            case COMPLEMENTAIRE:
                return "#06D6A0";
            case PROJET:
                return "#118AB2";
            case SPORT:
                return "#EF476F";
            case ARTISTIQUE:
                return "#9D4EDD";
            default:
                return "#6C757D";
        }
    }

    private static String generateDefaultIcon(TypeMatiere type) {
        switch (type) {
            case FONDAMENTALE:
                return "star";
            case OBLIGATOIRE:
                return "book";
            case OPTIONNELLE:
                return "options";
            case COMPLEMENTAIRE:
                return "plus-circle";
            case PROJET:
                return "project";
            case SPORT:
                return "sport";
            case ARTISTIQUE:
                return "palette";
            default:
                return "book-open";
        }
    }
}

