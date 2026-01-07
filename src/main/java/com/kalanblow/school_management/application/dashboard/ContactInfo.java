package com.kalanblow.school_management.application.dashboard;

import org.springframework.beans.factory.annotation.Value;

public record ContactInfo(@Value("${app.school.instance.contact-email}")  String  email,
                          @Value("${app.school.instance.phone-number}")  String phone) {
}
