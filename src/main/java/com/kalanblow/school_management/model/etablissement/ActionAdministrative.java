package com.kalanblow.school_management.model.etablissement;

import com.kalanblow.school_management.model.shared.enums.NiveauImportance;
import com.kalanblow.school_management.model.shared.enums.StatutAction;
import com.kalanblow.school_management.model.shared.enums.TypeAction;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "actions_administratives")
public class ActionAdministrative {
    @Id
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chef_etablissement_id", nullable = false)
    private ChefEtablissement chefEtablissement;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private TypeAction type;

    @Column(nullable = false, length = 500)
    private String description;

    @Column(length = 100)
    private String reference;

    @Column(name = "date_action", nullable = false)
    private LocalDateTime dateAction;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatutAction statut;

    @Column(columnDefinition = "TEXT")
    private String commentaire;

    @Column(name = "donnees_json", columnDefinition = "TEXT")
    private String donneesJson;

    // Pour multi-instance
    @Column(name = "instance_id", nullable = false)
    private String instanceId;

    @Column(name = "utilisateur_id")
    private String utilisateurId; // Qui a effectué l'action

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "user_agent")
    private String userAgent;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
