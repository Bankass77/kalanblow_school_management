package com.kalanblow.school_management.infrastructure.persistence.note;

import com.kalanblow.school_management.model.anneescolaire.Bulletin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface JpaBulletinRepository  extends JpaRepository<Bulletin,Long>, JpaSpecificationExecutor<Bulletin> {
    Optional<Bulletin> findByStudentStudentIdAndPeriodeId(Long studentId, Long periodeId);

    List<Bulletin> findByStudentStudentIdOrderByDateEditionDesc(Long studentId);

}
