package com.kalanblow.school_management.application.dto.inscription;

import com.kalanblow.school_management.model.shared.enums.StatutDemande;
import com.kalanblow.school_management.model.student.Student;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DemandeOrientation {

    private Student student;
    private LocalDate dateDemande;
    private StatutDemande statut;
}
