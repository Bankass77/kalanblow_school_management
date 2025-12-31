package com.kalanblow.school_management.application.usecase;

import com.kalanblow.school_management.repository.SchoolClassRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DeleteSchoolClassUseCase {

    private  final SchoolClassRepository schoolClassRepository;

    public DeleteSchoolClassUseCase(SchoolClassRepository schoolClassRepository) {
        this.schoolClassRepository = schoolClassRepository;
    }
    public void execute(Long id) {
        if (!schoolClassRepository.existSchoolClass(id)){

            throw  new EntityNotFoundException("School Class not found with id " + id);
        }
        schoolClassRepository.deleteSchoolClassById(id);
    }

}
