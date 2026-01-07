package com.kalanblow.school_management.application.usecase.note;

import com.kalanblow.school_management.model.anneescolaire.Bulletin;
import com.kalanblow.school_management.repository.BulletinRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ConsulterBulletinUseCase {

    private final BulletinRepository bulletinRepository;

    public Bulletin getById(Long id) {
        return bulletinRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bulletin introuvable"));
    }

    public List<Bulletin> getByStudent(Long studentId) {
        return bulletinRepository.findByStudentStudentIdOrderByDateEditionDesc(studentId);
    }
}

