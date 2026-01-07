package com.kalanblow.school_management.model.etablissement;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.kalanblow.school_management.model.shared.UserName;
import com.kalanblow.school_management.model.shared.Email;
import com.kalanblow.school_management.model.shared.PhoneNumber;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Value;

@Entity
@Table(name = "chef_etablissement")
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@EntityListeners(ChefEtablissementEntityListener.class)
public class ChefEtablissement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chefEtablissementId;

    @Value("${app.school.instance.id}")
    private String currentInstanceId;

    @Embedded
    @NotNull(message = "Le nom du chef est obligatoire")
    private UserName userName;

    @Embedded
    @NotNull(message = "L'email du chef est obligatoire")
    private Email email;

    @Embedded
    private PhoneNumber phoneNumber;

    @Column(name = "date_nomination")
    @NotNull(message = "La date de nomination est obligatoire")
    private LocalDate dateNomination;

    @Column(name = "date_fin_mandat")
    private LocalDate dateFinMandat;

    @Column(name = "est_actif")
    private boolean estActif = true;

    @Column(name = "numero_contrat", unique = true)
    private String numeroContrat;


    @Column(name = "instance_id")
    private String instanceId; // ID de l'instance/schoolId

    // Historique des actions
    @OneToMany(mappedBy = "chefEtablissement", cascade = CascadeType.ALL)
    private List<ActionAdministrative> actions = new ArrayList<>();


}

