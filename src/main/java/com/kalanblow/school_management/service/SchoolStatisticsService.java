package com.kalanblow.school_management.service;

import com.kalanblow.school_management.repository.SchoolClassRepository;
import com.kalanblow.school_management.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class SchoolStatisticsService {

    private final StudentRepository studentRepository;
    private final SchoolClassRepository schoolClassRepository;

    public SchoolStatisticsService(StudentRepository studentRepository,
                                   SchoolClassRepository schoolClassRepository) {
        this.studentRepository = studentRepository;
        this.schoolClassRepository = schoolClassRepository;
    }

    // Capacité totale = somme des capacités des classes
    public Integer getTotalCapacity() {
        return schoolClassRepository.sumAllCapacities();
    }

    // Effectif actuel = nombre d'élèves inscrits
    public Long getCurrentStudentsCount() {
        return studentRepository.count();
    }

    // Taux d'occupation en pourcentage
    public Double getOccupancyRate() {
        Integer capacity = getTotalCapacity();
        Long students = getCurrentStudentsCount();

        if (capacity == null || capacity == 0) return 0.0;
        return (students.doubleValue() / capacity) * 100;
    }

    // Statistiques par classe
    public Map<String, Object> getClassStatistics() {
        List<Object[]> stats = schoolClassRepository.getClassStatistics();
        return stats.stream()
                .collect(Collectors.toMap(
                        arr -> (String) arr[0],  // Nom de la classe
                        arr -> Map.of(
                                "capacity", arr[1],
                                "students", arr[2],
                                "occupancy", (Integer) arr[2] * 100.0 / (Integer) arr[1]
                        )
                ));
    }
}
