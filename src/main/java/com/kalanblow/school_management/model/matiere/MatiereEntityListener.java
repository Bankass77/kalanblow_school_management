package com.kalanblow.school_management.model.matiere;

import com.kalanblow.school_management.infrastructure.config.InstanceInfo;
import com.kalanblow.school_management.model.professeur.Professeur;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MatiereEntityListener {
    @Autowired
    private InstanceInfo instanceInfo;

    @PrePersist
    @PreUpdate
    public void prePersist(Matiere matiere) {
        matiere.setInstanceId(instanceInfo.getCurrentInstanceId());
    }
}
