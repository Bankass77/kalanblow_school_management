package com.kalanblow.school_management.application.usecase.abscence;

import com.kalanblow.school_management.application.dto.note.AbscenceRequest;
import com.kalanblow.school_management.model.anneescolaire.Abscence;
import com.kalanblow.school_management.model.anneescolaire.PeriodeScolaire;
import com.kalanblow.school_management.model.student.Student;
import com.kalanblow.school_management.repository.AbsenceRepository;
import com.kalanblow.school_management.repository.PeriodeScolaireRepository;
import com.kalanblow.school_management.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CreateAbsenceUseCase {

    private final AbsenceRepository absenceRepository;
    private final StudentRepository studentRepository;
    private final PeriodeScolaireRepository periodeRepository;

    public Abscence create(AbscenceRequest abscenceRequest) {
        // Vérifier que l'étudiant existe
        Student student = studentRepository.findByStudentId(abscenceRequest.studentId()).orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + abscenceRequest.studentId()));
        // Vérifier que la période existe
        PeriodeScolaire periodeScolaire = periodeRepository.findById(abscenceRequest.periodeId()).orElseThrow(() -> new EntityNotFoundException("Periode not found with id: " + abscenceRequest.periodeId()));

        // On verifie si une abscence existe pour cette date
        if (absenceRepository.existsByStudentAndDate(abscenceRequest.studentId(), abscenceRequest.dateAbsence())) {
            throw new IllegalArgumentException("Une abscence existe déjà pour cet étudiant à cette date " + abscenceRequest.dateAbsence());
        }
        Abscence abscence = new Abscence();
        abscence.setStudentId(student.getStudentId());
        abscence.setDateAbsence(abscenceRequest.dateAbsence());
        abscence.setJustifie(abscenceRequest.justifie());
        abscence.setMotif(abscenceRequest.motif());
        abscence.setPeriode(periodeScolaire);
        return absenceRepository.save(abscence);
    }
}

