package com.kalanblow.school_management.application.usecase.classe;

import com.kalanblow.school_management.infrastructure.persistence.classe.SchoolClassSpecifications;
import com.kalanblow.school_management.model.classe.SchoolClass;
import com.kalanblow.school_management.service.SchoolClassService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public Page<SchoolClass> execute(SchoolClassePageCacheKey key, Pageable pageable, Specification<SchoolClass> specification) {

      specification= buildSpecification(key);
      pageable= PageRequest.of(key.page(), key.size());
        return schoolClassService.search(specification, pageable);
    }

    private Specification<SchoolClass> buildSpecification(SchoolClassePageCacheKey key) {
     return Specification.where(SchoolClassSpecifications.nameContains(key.name()));
    }
}
