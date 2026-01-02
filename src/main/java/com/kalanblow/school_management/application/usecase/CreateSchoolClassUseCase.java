package com.kalanblow.school_management.application.usecase;

import com.kalanblow.school_management.model.SchoolClass;
import com.kalanblow.school_management.service.SchoolClassService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Service
public class CreateSchoolClassUseCase {

    private final SchoolClassService schoolClassService;

    public CreateSchoolClassUseCase(SchoolClassService schoolClassService) {
        this.schoolClassService = schoolClassService;
    }

    @CacheEvict(value = "schoolClass-pages", allEntries = true)
    public SchoolClass execute(SchoolClass schoolClass){

        return schoolClassService.create(schoolClass);
    }
}
