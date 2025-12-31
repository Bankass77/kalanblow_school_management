package com.kalanblow.school_management.application.usecase;

import com.kalanblow.school_management.model.SchoolClass;
import com.kalanblow.school_management.repository.SchoolClassRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UpdateSchoolClassUseCase {
    private final SchoolClassRepository schoolClassRepository;

    public UpdateSchoolClassUseCase(SchoolClassRepository schoolClassRepository) {
        this.schoolClassRepository = schoolClassRepository;
    }

    public SchoolClass execute(Long id, SchoolClass schoolClass) {

        SchoolClass existing = schoolClassRepository.findSchoolClassById(id).orElseThrow(() -> new EntityNotFoundException("School Class not found with id " + id));
        existing.setName(schoolClass.getName());
        existing.setCapacity(schoolClass.getCapacity());
        return schoolClassRepository.updatedSchoolClass(id, existing);
    }
}
