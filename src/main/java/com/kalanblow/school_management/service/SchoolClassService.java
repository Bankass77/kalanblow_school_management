package com.kalanblow.school_management.service;

import com.kalanblow.school_management.model.SchoolClass;
import com.kalanblow.school_management.repository.SchoolClassRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SchoolClassService {
    private final SchoolClassRepository schoolClassRepository;

    public SchoolClassService(SchoolClassRepository schoolClassRepository) {
        this.schoolClassRepository = schoolClassRepository;
    }

    @Transactional(readOnly = true)
   public SchoolClass findSchoolClassById(Long id) {
        return schoolClassRepository.findSchoolClassById(id).orElseThrow(()-> new EntityNotFoundException(" School class not found with id " + id));
    }

    @Transactional(readOnly = true)
    public SchoolClass findSchoolClassByName(String name) {
        return schoolClassRepository.findSchoolClassByName(name).orElseThrow(()-> new EntityNotFoundException(" School class not found with name " + name));
    }

    public SchoolClass create(SchoolClass schoolClass) {
        return schoolClassRepository.create(schoolClass);
    }

    @Transactional(readOnly = true)
    public List<SchoolClass> findAllSchoolClasses() {
        return schoolClassRepository.findAllSchoolClasses();
    }

    public boolean existSchoolClass(Long id) {
        return schoolClassRepository.existSchoolClass(id);
    }

    public SchoolClass updatedSchoolClass(Long id, SchoolClass schoolClass) {

        SchoolClass updated = schoolClassRepository.findSchoolClassById(id).orElseThrow(() -> new EntityNotFoundException(" SchoolClass not found with " + id));

        if (updated != null) {

            updated.setCapacity(schoolClass.getCapacity());
            updated.setName(schoolClass.getName());
        }
        return schoolClassRepository.updatedSchoolClass(id, updated);
    }

    public void deleteSchoolClassById(Long id) {

        if (!existSchoolClass(id)) {

            throw new EntityNotFoundException("School classe not found with " + id);
        }
        schoolClassRepository.deleteSchoolClassById(id);
    }
}
