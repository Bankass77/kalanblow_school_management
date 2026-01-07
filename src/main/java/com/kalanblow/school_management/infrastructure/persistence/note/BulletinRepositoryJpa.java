package com.kalanblow.school_management.infrastructure.persistence.note;

import com.kalanblow.school_management.model.anneescolaire.Bulletin;
import com.kalanblow.school_management.repository.BulletinRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BulletinRepositoryJpa implements BulletinRepository {

    private final JpaBulletinRepository jpaBulletinRepository;

    public BulletinRepositoryJpa(JpaBulletinRepository jpaBulletinRepository) {
        this.jpaBulletinRepository = jpaBulletinRepository;
    }


    @Override
    public Optional<Bulletin> findByStudentStudentIdAndPeriodeId(Long studentId, Long periodeId) {
        return jpaBulletinRepository.findByStudentStudentIdAndPeriodeId(studentId, periodeId);
    }

    @Override
    public List<Bulletin> findByStudentStudentIdAndPeriodeIdin(Long studentId, List<Long> periodeId) {
        return List.of();
    }


    @Override
    public List<Bulletin> findByStudentStudentIdOrderByDateEditionDesc(Long studentId) {
        return jpaBulletinRepository.findByStudentStudentIdOrderByDateEditionDesc(studentId);
    }

    @Override
    public Bulletin save(Bulletin bulletin) {
        return null;
    }

    @Override
    public Optional<Bulletin> findById(Long id) {
        return Optional.empty();
    }
}
