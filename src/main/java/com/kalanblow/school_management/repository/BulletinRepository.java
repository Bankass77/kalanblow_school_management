package com.kalanblow.school_management.repository;

import com.kalanblow.school_management.model.anneescolaire.Bulletin;

import java.util.List;
import java.util.Optional;

public interface BulletinRepository {
    Optional<Bulletin> findByStudentStudentIdAndPeriodeId(Long studentId, Long periodeId);
    List<Bulletin> findByStudentStudentIdAndPeriodeIdin(Long studentId, List<Long> periodeId);

    List<Bulletin> findByStudentStudentIdOrderByDateEditionDesc(Long studentId);

    Bulletin save(Bulletin bulletin);

    Optional<Bulletin> findById(Long id);
}
