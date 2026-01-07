package com.kalanblow.school_management.infrastructure.web;

import com.kalanblow.school_management.infrastructure.config.InstanceInfo;
import com.kalanblow.school_management.model.etablissement.SchoolConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/info")
public class InstanceController {

    @Autowired
    private InstanceInfo instanceInfo;

    @Autowired
    private SchoolConfiguration schoolConfig;

    @GetMapping
    public Map<String, Object> getInstanceInfo() {
        return Map.of(
                "instanceId", instanceInfo.getInstanceInfo(),
                "schoolName", schoolConfig.getName(),
                "deploymentDate", instanceInfo.getDeploymentDate(),
                "capacity", schoolConfig.getCapacity(),
                "contact", Map.of(
                        "email", schoolConfig.getContactEmail(),
                        "phone", schoolConfig.getPhoneNumber()
                )
        );
    }
}
