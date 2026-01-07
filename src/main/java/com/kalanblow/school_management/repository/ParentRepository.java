package com.kalanblow.school_management.repository;

import com.kalanblow.school_management.model.parent.Parent;

import java.util.Set;

public interface ParentRepository {
    Set<Parent> findAllById(Set<Long> parentIds);
}
