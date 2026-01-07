package com.kalanblow.school_management.infrastructure.persistence.parents;

import com.kalanblow.school_management.model.parent.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface JpaParentRepository extends JpaRepository<Parent, Long>, JpaSpecificationExecutor<Parent> {
}
