package com.kalanblow.school_management.infrastructure.persistence.note;

import com.kalanblow.school_management.model.matiere.MatiereResultat;
import com.kalanblow.school_management.repository.NoteRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NoteRepositoryJpa implements NoteRepository {

    private final JpaNoteRepository jpaNoteRepository;

    public NoteRepositoryJpa(JpaNoteRepository jpaNoteRepository) {
        this.jpaNoteRepository = jpaNoteRepository;
    }

    @Override
    public Double calculerMoyenneGenerale(Long studentId, Long anneeId) {
        return jpaNoteRepository.getNoteByIdAndId(studentId, anneeId);
    }

    @Override
    public List<MatiereResultat> getResultatsParMatiere(Long studentId, Long anneeId) {
        MatiereResultat matiereResultat = new MatiereResultat();
        matiereResultat.setMoyenne(jpaNoteRepository.getNoteByIdAndId(studentId, anneeId));
        return List.of(matiereResultat);
    }
}
