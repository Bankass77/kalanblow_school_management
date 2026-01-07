package com.kalanblow.school_management.infrastructure.persistence.inscription;

import com.kalanblow.school_management.model.inscription.Inscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface JpaInscriptionRepository  extends JpaRepository<Inscription,Long>, JpaSpecificationExecutor<Inscription> {
}
