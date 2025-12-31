package com.kalanblow.school_management.application.usecase;

import com.kalanblow.school_management.model.SchoolClass;
import com.kalanblow.school_management.repository.SchoolClassRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class GetSchoolClassUseCase {
    private final SchoolClassRepository schoolClassRepository;

    public GetSchoolClassUseCase(SchoolClassRepository schoolClassRepository) {
        this.schoolClassRepository = schoolClassRepository;
    }

    public SchoolClass execute(Long id) {

        return schoolClassRepository.findSchoolClassById(id).orElseThrow(() -> new EntityNotFoundException("School Class not found with id " + id));
    }

    public SchoolClass findName(String name) {

        return schoolClassRepository.findSchoolClassByName(name).orElseThrow(() -> new EntityNotFoundException("School Class not found with name " + name));
    }
}
