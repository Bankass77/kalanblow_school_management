package com.kalanblow.school_management.repository;

import com.kalanblow.school_management.model.SchoolClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

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
}
