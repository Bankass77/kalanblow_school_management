package com.kalanblow.school_management.infrastructure.persistence.classe;

import com.kalanblow.school_management.model.classe.SchoolClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface JpaSchoolClassRepository extends JpaRepository<SchoolClass, Long>, JpaSpecificationExecutor<SchoolClass> {
    Optional<SchoolClass> getSchoolClassesByName(String name);
    Optional<SchoolClass> getSchoolClassesBySchoolClassId(Long schoolClassId);
}
