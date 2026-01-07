package com.kalanblow.school_management.repository;


import com.kalanblow.school_management.model.matiere.MatiereResultat;
import com.kalanblow.school_management.service.FinAnneeService;

import java.util.List;

public interface NoteRepository {
    Double calculerMoyenneGenerale(Long studentId, Long anneeId);

    List<MatiereResultat> getResultatsParMatiere(Long studentId, Long anneeId);
}
