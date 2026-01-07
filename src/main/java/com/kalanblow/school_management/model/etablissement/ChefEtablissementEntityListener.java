package com.kalanblow.school_management.model.etablissement;

import com.kalanblow.school_management.infrastructure.config.InstanceInfo;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.springframework.stereotype.Component;

@Component
public class ChefEtablissementEntityListener {

    private InstanceInfo instanceInfo;
    @PrePersist
    @PreUpdate
    public void prePersistAndUpdate(ChefEtablissement chefEtablissement) {
        if (chefEtablissement.getInstanceId() == null) {
            chefEtablissement.setInstanceId(instanceInfo.getCurrentInstanceId());
        }
    }
}
