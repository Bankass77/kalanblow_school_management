package com.kalanblow.school_management.application.usecase.note;

import com.kalanblow.school_management.application.dto.note.BulletinCommand;
import com.kalanblow.school_management.model.anneescolaire.Bulletin;
import com.kalanblow.school_management.model.anneescolaire.PeriodeScolaire;
import com.kalanblow.school_management.model.student.Student;
import com.kalanblow.school_management.service.BulletinService;
import com.kalanblow.school_management.service.PeriodeScolaireService;
import com.kalanblow.school_management.service.StudentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@Transactional
public class CreerBulletinUseCase {

    private final BulletinService bulletinService;
    private final StudentService studentService;
    private final PeriodeScolaireService periodeScolaireService;

    public Bulletin execute(BulletinCommand command) {

        Student student = studentService.getStudentById(command.studentId());

        PeriodeScolaire periode = periodeScolaireService.findById(command.periodeId())
                .orElseThrow(() -> new EntityNotFoundException("Période introuvable"));

        Bulletin bulletin = new Bulletin();
        bulletin.setStudent(student);
        bulletin.setPeriode(periode);
        bulletin.setDateEdition(LocalDate.now());
        bulletin.setMoyenneGenerale(command.moyenneGenerale());
        bulletin.setClassement(command.classement());
        bulletin.setAppreciations(command.appreciations());

        return bulletinService.save(bulletin);
    }
}
