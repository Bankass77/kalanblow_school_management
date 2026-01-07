package com.kalanblow.school_management.infrastructure.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class InstanceInfo {
    @Value("${school.instance.id}")
    private String currentInstanceId;

    public String getCurrentInstanceId() {
        return currentInstanceId;
    }
}
