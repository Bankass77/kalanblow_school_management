package com.kalanblow.school_management.model.etablissement;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "app.kalanblow")
@Component
@Getter
@Setter
public class SchoolConfiguration {
    private String id;
    @Value("${app.school.instance.name}")
    private String name;
    @Value("${app.school.instance.region}")
    private String region;
    @Value("${app.school.instance.address}")
    private String address;
    @Value("${app.school.instance.logo-url}")
    private String logoUrl;
    @Value("${app.school.instance.capacity}")
    private Integer capacity;
    @Value("${app.school.instance.contact-email}")
    private String contactEmail;
    @Value("${app.school.instance.phone-number}")
    private String phoneNumber;
}
