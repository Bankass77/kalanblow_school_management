package com.kalanblow.school_management.application.dashboard;

import java.time.LocalDateTime;

public record SchoolInfoResponse(String instanceId
        , LocalDateTime deploymentDate
        , String schoolId
        , String schoolName
        , String region
        , String address
        , String logoUrl
        , Integer capacity
        , ContactInfo contact) {
}
