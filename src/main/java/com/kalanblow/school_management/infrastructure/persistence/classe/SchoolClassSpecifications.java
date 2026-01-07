package com.kalanblow.school_management.infrastructure.persistence.classe;

import com.kalanblow.school_management.model.classe.SchoolClass;
import org.springframework.data.jpa.domain.Specification;

public class SchoolClassSpecifications {

    public static Specification<SchoolClass> nameContains(String name) {

        return ((root, query, criteriaBuilder) ->
                name == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
    }

    public static Specification<SchoolClass> classIdEquals(Long classId) {

        return ((root, query, criteriaBuilder) -> classId == null ? null : criteriaBuilder.equal(root.get("schoolClass").get("schoolClassId"), classId));

    }
}
