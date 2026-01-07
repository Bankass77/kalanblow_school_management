package com.kalanblow.school_management.infrastructure.web.controller;

import com.kalanblow.school_management.application.dashboard.CapacityResponse;
import com.kalanblow.school_management.application.dashboard.ClassDetailResponse;
import com.kalanblow.school_management.model.dashboard.SchoolDashboard;
import com.kalanblow.school_management.service.SchoolDashboardService;
import lombok.*;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final SchoolDashboardService dashboardService;

    @GetMapping("/capacity")
    @Cacheable(value = "schoolCapacity", key = "'capacity'")
    public ResponseEntity<CapacityResponse> getCapacity() {
        SchoolDashboard dashboard = dashboardService.getDashboard();
        CapacityResponse response = new CapacityResponse(
                dashboard.getTotalCapacity(),
                Math.toIntExact(dashboard.getCurrentStudents()),
                dashboard.getOccupancyRate(),
                LocalDateTime.now()
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/classes")
    @Cacheable(value = "classDetails", key = "'classes'")
    public ResponseEntity<List<ClassDetailResponse>> getClassDetails() {
        List<ClassDetailResponse> classes = dashboardService.getDashboard().getClassDetails()
                .stream()
                .map(detail -> new ClassDetailResponse(
                        detail.getClassName(),
                        detail.getCapacity(),
                        Math.toIntExact(detail.getStudentsCount()),
                        detail.getOccupancy(),
                        (int) (detail.getCapacity() - detail.getStudentsCount())
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(classes);
    }
}
