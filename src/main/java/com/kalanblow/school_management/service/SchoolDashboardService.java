package com.kalanblow.school_management.service;

import com.kalanblow.school_management.model.dashboard.SchoolDashboard;
import com.kalanblow.school_management.model.etablissement.SchoolConfiguration;
import com.kalanblow.school_management.repository.SchoolClassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Cacheable("schoolDashboard")
@RequiredArgsConstructor
public class SchoolDashboardService {
    private final SchoolConfiguration schoolConfig;
    private final SchoolClassRepository schoolClassRepository;
    private final SchoolStatisticsService statisticsService;


    @Scheduled(fixedRate = 300000) // Actualisation toutes les 5 minutes
    @CacheEvict(value = "schoolDashboard", allEntries = true)
    public void refreshCache() {
        // Vide le cache périodiquement
    }

    public SchoolDashboard getDashboard() {
        SchoolDashboard dashboard = new SchoolDashboard();
        dashboard.setSchoolName(schoolConfig.getName());
        dashboard.setTotalCapacity(statisticsService.getTotalCapacity());
        dashboard.setCurrentStudents(statisticsService.getCurrentStudentsCount());
        dashboard.setOccupancyRate(statisticsService.getOccupancyRate());
        dashboard.setLastUpdated(LocalDateTime.now());

        // Détails par classe
        List<SchoolDashboard.ClassDetail> details = schoolClassRepository.getClassStatistics().
                stream()
                .map(row -> {
                    SchoolDashboard.ClassDetail detail = new SchoolDashboard.ClassDetail();
                    detail.setClassName((String) row[0]);
                    detail.setCapacity((Integer) row[1]);
                    detail.setStudentsCount((long) ((Long) row[2]).intValue());
                    detail.setOccupancy((Double) row[3]);
                    return detail;
                })
                .collect(Collectors.toList());

        dashboard.setClassDetails(details);
        return dashboard;

    }

    private Double calculateOccupancy(SchoolDashboard.ClassDetail detail) {
        return Optional.ofNullable(detail).filter(d -> d.getCapacity() != null && d.getCapacity() > 0)
                .filter(d -> d.getStudentsCount() != null)
                .map(d -> (double) Math.round(((float) (d.getStudentsCount() * 100) / d.getCapacity()) * 100) / 100).orElse(0.0);
    }
}
