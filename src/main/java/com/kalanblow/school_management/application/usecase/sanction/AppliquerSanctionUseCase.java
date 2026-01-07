package com.kalanblow.school_management.application.usecase.sanction;

import com.kalanblow.school_management.application.dto.inscription.Sanction;
import com.kalanblow.school_management.infrastructure.web.dto.StudentCreateDTO;
import com.kalanblow.school_management.model.shared.enums.StatutSanction;
import com.kalanblow.school_management.model.student.Student;
import com.kalanblow.school_management.service.SanctionService;
import com.kalanblow.school_management.service.StudentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AppliquerSanctionUseCase {

    private final SanctionService sanctionService;
    private final StudentService studentService;

    public Sanction execute(Long sanctionId) {

        Sanction sanction = sanctionService.findById(sanctionId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Sanction not found with id: " + sanctionId)
                );

        if (sanction.getStatut() != StatutSanction.EN_ATTENTE) {
            throw new IllegalStateException("Cette sanction a déjà été traitée");
        }

        sanction.setStatut(StatutSanction.APPLIQUEE);
        sanction.setAppliquee(true);
        sanction.setDateDebut(LocalDate.now());

        appliquerConsequences(sanction);

        return sanctionService.save(sanction);
    }

    private void appliquerConsequences(Sanction sanction) {
        switch (sanction.getType()) {

            case AVERTISSEMENT_ECRIT, AVERTISSEMENT_ORAL -> notifierParents(sanction);

            case RETENUE -> programmerRetenue(sanction);

            case EXCLUSION_TEMPORAIRE -> mettreAJourDisponibilite(sanction, false);

            case EXCLUSION_DEFINITIVE -> initierProcedureExclusion(sanction);
        }
    }

    private void notifierParents(Sanction sanction) {
        // à brancher sur un port NotificationPort plus tard
        log.info("Notification parents pour sanction {}", sanction.getId());
    }

    private void programmerRetenue(Sanction sanction) {
        log.info("Retenue programmée à partir du {}", sanction.getDateDebut());
    }

    private void mettreAJourDisponibilite(Sanction sanction, boolean disponible) {
        sanction = sanctionService.getSanction(sanction.getStudentId());

        Student student = studentService.getStudentById(sanction.getStudentId());

        // TODO: à finir
        StudentCreateDTO studentCreateDTO = new StudentCreateDTO();
        studentCreateDTO.setEtat(student.getEtat());
        studentService.createNewStudent(studentCreateDTO);
    }

    private void initierProcedureExclusion(Sanction sanction) {
        log.warn("Procédure exclusion initiée pour l'élève {}", sanction.getStudentId());
    }
}
