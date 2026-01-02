package com.kalanblow.school_management.application.usecase;

import com.kalanblow.school_management.model.SchoolClass;
import com.kalanblow.school_management.service.SchoolClassService;
import org.springframework.stereotype.Service;

@Service
public class UpdateSchoolClassUseCase {
    private final SchoolClassService schoolClassService;

    public UpdateSchoolClassUseCase(SchoolClassService schoolClassService) {
        this.schoolClassService = schoolClassService;
    }

    public SchoolClass execute(Long id, SchoolClass schoolClass) {

        SchoolClass existing = schoolClassService.findSchoolClassById(id);
        existing.setName(schoolClass.getName());
        existing.setCapacity(schoolClass.getCapacity());
        return schoolClassService.updatedSchoolClass(id, existing);
    }
}
