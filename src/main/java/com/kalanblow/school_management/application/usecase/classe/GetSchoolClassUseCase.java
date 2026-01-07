package com.kalanblow.school_management.application.usecase.classe;

import com.kalanblow.school_management.model.classe.SchoolClass;
import com.kalanblow.school_management.service.SchoolClassService;
import org.springframework.stereotype.Service;

@Service
public class GetSchoolClassUseCase {

    private final SchoolClassService schoolClassService;

    public GetSchoolClassUseCase(SchoolClassService schoolClassService) {
        this.schoolClassService = schoolClassService;
    }

    public SchoolClass execute(Long id) {

        return schoolClassService.findSchoolClassById(id);
    }

    public SchoolClass findName(String name) {

        return schoolClassService.findSchoolClassByName(name);
    }
}
