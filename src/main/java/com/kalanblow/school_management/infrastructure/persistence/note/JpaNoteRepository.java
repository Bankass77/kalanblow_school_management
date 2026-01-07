package com.kalanblow.school_management.infrastructure.persistence.note;

import com.kalanblow.school_management.model.note.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface JpaNoteRepository extends JpaRepository<Note,Long>, JpaSpecificationExecutor<Note> {
    Double getNoteByIdAndId(Long id, Long id1);
}
