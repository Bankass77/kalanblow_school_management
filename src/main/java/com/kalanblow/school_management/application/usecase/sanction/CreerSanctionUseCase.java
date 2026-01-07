package com.kalanblow.school_management.application.usecase.sanction;

import com.kalanblow.school_management.application.dto.inscription.Sanction;
import com.kalanblow.school_management.application.dto.sanction.SanctionCommand;
import com.kalanblow.school_management.model.shared.enums.GraviteSanction;
import com.kalanblow.school_management.model.shared.enums.StatutSanction;
import com.kalanblow.school_management.model.shared.enums.TypeSanction;
import com.kalanblow.school_management.model.student.Student;
import com.kalanblow.school_management.service.SanctionService;
import com.kalanblow.school_management.service.StudentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@Transactional
public class CreerSanctionUseCase {

    private final SanctionService sanctionService;
    private final StudentService studentService;

    public Sanction execute(SanctionCommand command) {

        Student student = studentService.getStudentById(command.studentId());

        Sanction sanction = new Sanction();
        sanction.setStudentId(student.getStudentId());
        sanction.setDateSanction(
                command.dateSanction() != null ? command.dateSanction() : LocalDate.now()
        );

        sanction.setAuteurSanctionId(command.auteurSanction());
        sanction.setAppliquee(command.appliquee());
        sanction.setContestee(command.contestee());
        sanction.setGravite(GraviteSanction.valueOf(command.gravite()));
        sanction.setDescription(command.description());
        sanction.setMotif(command.motif());
        sanction.setDateFin(command.dateFin());
        sanction.setDateDebut(command.dateDebut());
        sanction.setDateLevee(command.dateLevee());
        sanction.setType(TypeSanction.valueOf(command.typeSanction()));
        sanction.setDateContestation(command.dateContestation());
        sanction.setNotifierParents(command.notifierParents());
        sanction.setSignataireId(command.signataire());
        sanction.setRapportIncident(command.description());
        sanction.setStatut(StatutSanction.EN_ATTENTE);

        return sanctionService.save(sanction);
    }
}
