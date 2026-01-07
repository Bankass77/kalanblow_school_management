package com.kalanblow.school_management.model.etablissement;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "app.kalanblow")
@Component
@Getter
@Setter
public class SchoolConfiguration {
    private String id;
    private String name;
    private String region;
    private String address;
    private String logoUrl;
    private Integer capacity;
    private String contactEmail;
    private String phoneNumber;
}
