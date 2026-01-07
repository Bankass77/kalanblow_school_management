package com.kalanblow.school_management.repository;

import com.kalanblow.school_management.model.classe.SchoolClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface SchoolClassRepository {
    Optional<SchoolClass> findSchoolClassById(Long id);

    Optional<SchoolClass> findSchoolClassByName(String name);

    SchoolClass create(SchoolClass schoolClass);

    List<SchoolClass> findAllSchoolClasses();

    boolean existSchoolClass(Long id);

    SchoolClass updatedSchoolClass(Long id, SchoolClass schoolClass);

    void deleteSchoolClassById(Long id);

    Page<SchoolClass> search(Specification<SchoolClass> specification, Pageable pageable);

    @Query("SELECT SUM(sc.capacity) FROM SchoolClass sc")
    Integer sumAllCapacities();

    @Query("""
        SELECT sc.name as className, 
               sc.capacity as capacity,
               COUNT(s) as students
        FROM SchoolClass sc
        LEFT JOIN sc.etablissement.eleves s
        GROUP BY sc.schoolClassId, sc.name, sc.capacity
        """)
    List<Object[]> getClassStatistics();

}
