package com.kalanblow.school_management.infrastructure.persistence.etablissement;

import com.kalanblow.school_management.model.etablissement.Etablissement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface JpaEtablissementRepository  extends JpaRepository<Etablissement,Long>, JpaSpecificationExecutor<Etablissement> {
}
