package com.kalanblow.school_management.infrastructure.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Getter
@Setter
public class InstanceInfo {
    private String currentInstanceId;
    private final String schoolName;
    private final LocalDateTime deploymentDate;

    public InstanceInfo(
            @Value("${app.school.instance.id}") String currentInstanceId,
            @Value("${app.school.instance.name}") String schoolName) {
        this.currentInstanceId = currentInstanceId;
        this.schoolName = schoolName;
        this.deploymentDate = LocalDateTime.now();
    }

    public String getInstanceInfo() {
        return String.format("Instance %s - %s", currentInstanceId, schoolName);
    }


}
