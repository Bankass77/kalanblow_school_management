package com.kalanblow.school_management.service;

import com.kalanblow.school_management.model.parent.Parent;
import com.kalanblow.school_management.repository.ParentRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ParentService {

    private final ParentRepository parentRepository;

    public ParentService(ParentRepository parentRepository) {
        this.parentRepository = parentRepository;
    }
    public Set<Parent> findAllById(Set<Long> parentIds){

        return  parentRepository.findAllById(parentIds);
    }
}
