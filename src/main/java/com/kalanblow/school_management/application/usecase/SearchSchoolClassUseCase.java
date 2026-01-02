package com.kalanblow.school_management.application.usecase;

import com.kalanblow.school_management.model.SchoolClass;
import com.kalanblow.school_management.service.SchoolClassService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Service
public class SearchSchoolClassUseCase {
    private final SchoolClassService schoolClassService;

    public SearchSchoolClassUseCase(SchoolClassService schoolClassService) {
        this.schoolClassService = schoolClassService;
    }

    @Cacheable(value = "schoolClass-pages", key = "root.methodName+ '_' + #key")
    public Page<SchoolClass> execute(SchoolPageCaheKey key, Pageable pageable, Specification<SchoolClass> specification) {
        return schoolClassService.search(specification, pageable);
    }
}
