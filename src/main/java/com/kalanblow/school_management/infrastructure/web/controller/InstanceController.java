package com.kalanblow.school_management.infrastructure.web.controller;

import com.kalanblow.school_management.application.dashboard.ContactInfo;
import com.kalanblow.school_management.application.dashboard.SchoolInfoResponse;
import com.kalanblow.school_management.infrastructure.config.InstanceInfo;
import com.kalanblow.school_management.model.etablissement.SchoolConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/info")
public class InstanceController {

    @Autowired
    private InstanceInfo instanceInfo;

    @Autowired
    private SchoolConfiguration schoolConfig;

    @GetMapping
    public SchoolInfoResponse getInstanceInfo() {
        return new SchoolInfoResponse(
                instanceInfo.getInstanceInfo(),
                instanceInfo.getDeploymentDate(),
                schoolConfig.getId(),
                schoolConfig.getName(),
                schoolConfig.getRegion(),
                schoolConfig.getAddress(),
                schoolConfig.getLogoUrl(),
                schoolConfig.getCapacity(),
                new ContactInfo(
                        schoolConfig.getContactEmail(),
                        schoolConfig.getPhoneNumber()
                )
        );
    }
}
