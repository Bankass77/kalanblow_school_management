package com.kalanblow.school_management.infrastructure.persistence.parents;

import com.kalanblow.school_management.model.parent.Parent;
import com.kalanblow.school_management.repository.ParentRepository;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

@Repository
public class ParentRepositoryJpa implements ParentRepository {

    private  final  JpaParentRepository jpaParentRepository;

    public ParentRepositoryJpa(JpaParentRepository jpaParentRepository) {
        this.jpaParentRepository = jpaParentRepository;
    }

    @Override
    public Set<Parent> findAllById(Set<Long> parentIds) {

        return new HashSet<>(jpaParentRepository.findAllById(parentIds));
    }
}
