package com.kalanblow.school_management.application.usecase.sanction;

import com.kalanblow.school_management.application.dto.inscription.Sanction;
import com.kalanblow.school_management.service.SanctionService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ConsulterSanctionUseCase {

    private final SanctionService sanctionService;

    public Sanction getById(Long sanctionId) {
        return sanctionService.findById(sanctionId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Sanction not found with id: " + sanctionId)
                );
    }

    public List<Sanction> getByStudent(Long studentId) {
        return sanctionService.findByStudentIdOrderByDateSanctionDesc(studentId);
    }
}
