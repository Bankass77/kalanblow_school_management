package com.kalanblow.school_management.infrastructure.persistence.student;

import com.kalanblow.school_management.model.student.Student;
import org.springframework.data.jpa.domain.Specification;

public class StudentSpecifications {
    public static Specification<Student> firstNameContains(String name) {

        return (root, query, criteriaBuilder) -> name == null ? null : criteriaBuilder.like(
                criteriaBuilder.lower(root.get("firstName")), "%" + name.toLowerCase() + "%"
        );
    }

    public static Specification<Student> lastNameContains(String lastName) {
        return ((root, query, criteriaBuilder) -> lastName == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), "%" + lastName.toLowerCase() + "%"));
    }

    public static Specification<Student> classIdEquals(Long classId) {

        return ((root, query, criteriaBuilder) -> classId == null ? null : criteriaBuilder.equal(root.get("schoolClass").get("schoolClassId"), classId));
    }
}
