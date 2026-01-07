package com.kalanblow.school_management.application.usecase.abscence;


import com.kalanblow.school_management.service.AbscenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DeleteAbscenceUseCase {
    private final AbscenceService abscenceService;

    public void deleteAbscence(Long abscenceId) {

        abscenceService.deleteAbscence(abscenceId);
    }
}
