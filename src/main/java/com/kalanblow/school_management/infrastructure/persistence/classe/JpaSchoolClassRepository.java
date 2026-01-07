package com.kalanblow.school_management.infrastructure.persistence.classe;

import com.kalanblow.school_management.model.classe.SchoolClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface JpaSchoolClassRepository extends JpaRepository<SchoolClass, Long>, JpaSpecificationExecutor<SchoolClass> {
    Optional<SchoolClass> getSchoolClassesByName(String name);
    Optional<SchoolClass> getSchoolClassesBySchoolClassId(Long schoolClassId);
    @Query("SELECT COALESCE(SUM(c.capacity), 0) FROM SchoolClass c")
    Integer sumAllCapacities();

    @Query("""
        SELECT 
            c.name as className,
            c.capacity as capacity,
            COUNT(s) as studentCount,
            CASE 
                WHEN c.capacity > 0 THEN ROUND((COUNT(s) * 100.0 / c.capacity), 2)
                ELSE 0.0 
            END as occupancyRate
        FROM SchoolClass c
        LEFT JOIN c.anneeScolaire.eleves s
        GROUP BY c.schoolClassId, c.name, c.capacity
        ORDER BY c.name
        """)
    List<Object[]> getClassStatistics();
}
