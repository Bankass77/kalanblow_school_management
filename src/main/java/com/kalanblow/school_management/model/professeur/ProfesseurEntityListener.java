package com.kalanblow.school_management.model.professeur;

import com.kalanblow.school_management.infrastructure.config.InstanceInfo;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProfesseurEntityListener {

    @Autowired
    private InstanceInfo instanceInfo;

    @PrePersist
    @PreUpdate
    public void prePersistAndUpdate(Professeur professeur) {
        // Si l'instanceId n'est pas déjà défini, on le définit (pour la création)
        // Pour la mise à jour, on ne change pas l'instanceId s'il est déjà défini.
        if (professeur.getInstanceId() == null) {
            professeur.setInstanceId(instanceInfo.getCurrentInstanceId());
        }
    }
}
