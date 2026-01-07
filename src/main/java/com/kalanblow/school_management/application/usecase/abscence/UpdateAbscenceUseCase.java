package com.kalanblow.school_management.application.usecase.abscence;

import com.kalanblow.school_management.model.anneescolaire.Abscence;
import com.kalanblow.school_management.service.AbscenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UpdateAbscenceUseCase {

    private final AbscenceService abscenceService;

    public Abscence updateAbscence(Long abscenceId, Abscence abscence) {
        return abscenceService.updateAbscence(abscenceId, abscence);
    }
}
