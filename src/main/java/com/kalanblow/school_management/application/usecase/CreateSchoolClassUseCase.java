package com.kalanblow.school_management.application.usecase;

import com.kalanblow.school_management.model.SchoolClass;
import com.kalanblow.school_management.repository.SchoolClassRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateSchoolClassUseCase {

    private final SchoolClassRepository schoolClassRepository;

    public CreateSchoolClassUseCase(SchoolClassRepository schoolClassRepository) {
        this.schoolClassRepository = schoolClassRepository;
    }

    @CacheEvict(value = "schoolClass-pages", allEntries = true)
    public SchoolClass execute(SchoolClass schoolClass){

        return schoolClassRepository.create(schoolClass);
    }
}
