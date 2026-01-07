package com.kalanblow.school_management.application.usecase.sanction;

import com.kalanblow.school_management.application.dto.inscription.Sanction;
import com.kalanblow.school_management.application.dto.sanction.DecisionConseilResponse;
import com.kalanblow.school_management.application.dto.sanction.SanctionResume;
import com.kalanblow.school_management.model.shared.enums.StatutSanction;
import com.kalanblow.school_management.model.student.Student;
import com.kalanblow.school_management.service.DecisionConseilService;
import com.kalanblow.school_management.service.SanctionService;
import com.kalanblow.school_management.service.StudentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EvaluerSanctionsPourDecisionUseCase {

    private final SanctionService sanctionService;
    private final StudentService studentRepository;
    private final DecisionConseilService decisionConseilService;

    public DecisionConseilResponse execute(Long studentId) {

        List<Sanction> sanctions = sanctionService
                .findByStudentIdOrderByDateSanctionDesc(studentId);

        List<Sanction> sanctionsSignificatives = sanctions.stream()
                .filter(s -> s.getStatut() == StatutSanction.APPLIQUEE)
                .filter(s -> s.getDateSanction()
                        .isAfter(LocalDate.now().minusMonths(6)))
                .toList();

        int graviteTotale = sanctionsSignificatives.stream()
                .mapToInt(s -> s.getGravite().getNiveau())
                .sum();

        Student student = studentRepository.getStudentById(studentId);

        DecisionConseilService.DecisionConseil decision =
                decisionConseilService.evaluerEleve(
                        student,
                        LocalDate.now().getYear()
                );

        List<SanctionResume> resumes = sanctionsSignificatives.stream()
                .map(this::toResume)
                .toList();

        return new DecisionConseilResponse(
                studentId,
                sanctionsSignificatives.size(),
                graviteTotale,
                decision.name(),
                resumes
        );
    }

    private SanctionResume toResume(Sanction sanction) {
        return new SanctionResume(
                sanction.getId(),
                sanction.getType(),
                sanction.getDateSanction(),
                sanction.getGravite().getNiveau(),
                sanction.getMotif()
        );
    }
}

