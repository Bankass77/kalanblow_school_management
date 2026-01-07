package com.kalanblow.school_management.infrastructure.persistence.classe;

import com.kalanblow.school_management.model.classe.SchoolClass;
import com.kalanblow.school_management.repository.SchoolClassRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SchoolClassRepositoryJpa implements SchoolClassRepository {

    private final JpaSchoolClassRepository classRepository;

    public SchoolClassRepositoryJpa(JpaSchoolClassRepository classRepository) {
        this.classRepository = classRepository;
    }


    @Override
    public Optional<SchoolClass> findSchoolClassById(Long id) {
        return classRepository.getSchoolClassesBySchoolClassId(id);
    }

    @Override
    public Optional<SchoolClass> findSchoolClassByName(String name) {
        return classRepository.getSchoolClassesByName(name);
    }

    @Override
    public SchoolClass create(SchoolClass schoolClass) {
        return classRepository.save(schoolClass);
    }

    @Override
    public List<SchoolClass> findAllSchoolClasses() {
        return classRepository.findAll();
    }

    @Override
    public boolean existSchoolClass(Long id) {
        return classRepository.existsById(id);
    }

    @Override
    public SchoolClass updatedSchoolClass(Long id, SchoolClass schoolClass) {

        SchoolClass updated = classRepository.getSchoolClassesBySchoolClassId(id).orElseThrow(() -> new EntityNotFoundException(" School class not found with id " + id));
        if (updated != null) {

            updated.setName(schoolClass.getName());
            updated.setCapacity(schoolClass.getCapacity());
            return classRepository.save(updated);
        }

        return null;
    }

    @Override
    public void deleteSchoolClassById(Long id) {

        if (!existSchoolClass(id)) {

            throw new EntityNotFoundException(" School class not found with id " + id);
        }

        classRepository.deleteById(id);
    }

    @Override
    public Page<SchoolClass> search(Specification<SchoolClass> specification, Pageable pageable) {
        return classRepository.findAll(specification,pageable);
    }
}
