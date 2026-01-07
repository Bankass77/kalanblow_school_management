package com.kalanblow.school_management.application.usecase.classe;

import com.kalanblow.school_management.service.SchoolClassService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DeleteSchoolClassUseCase {

    private final SchoolClassService schoolClassService;

    public DeleteSchoolClassUseCase(SchoolClassService schoolClassService) {
        this.schoolClassService = schoolClassService;
    }

    public void execute(Long id) {
        if (!schoolClassService.existSchoolClass(id)) {

            throw new EntityNotFoundException("School Class not found with id " + id);
        }
        schoolClassService.deleteSchoolClassById(id);
    }

}
