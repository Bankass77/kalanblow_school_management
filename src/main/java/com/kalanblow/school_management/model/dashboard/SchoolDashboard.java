package com.kalanblow.school_management.model.dashboard;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class SchoolDashboard {
    private String schoolName;
    private Integer totalCapacity;
    private Long currentStudents;
    private Double occupancyRate;
    private List<ClassDetail> classDetails;
    private LocalDateTime lastUpdated;

    @Getter
    @Setter
    public static class ClassDetail {
        private String className;
        private Integer capacity;
        private Long studentsCount;
        private Double occupancy;
        private String teacherName;
    }
}
