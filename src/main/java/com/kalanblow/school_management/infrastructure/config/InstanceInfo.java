package com.kalanblow.school_management.infrastructure.config;


import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Getter
public class InstanceInfo {
    @Value("${school.instance.id}")
    private String currentInstanceId;
    private final String schoolName;
    private final LocalDateTime deploymentDate;

    public InstanceInfo(
            @Value("${app.school.id}") String currentInstanceId,
            @Value("${app.school.name}") String schoolName) {
        this.currentInstanceId = currentInstanceId;
        this.schoolName = schoolName;
        this.deploymentDate = LocalDateTime.now();
    }

    public String getInstanceInfo() {
        return String.format("Instance %s - %s", currentInstanceId, schoolName);
    }

    public String getCurrentInstanceId() {
        return currentInstanceId;
    }

}
