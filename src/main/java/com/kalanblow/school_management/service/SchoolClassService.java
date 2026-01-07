package com.kalanblow.school_management.service;

import com.kalanblow.school_management.model.anneescolaire.AnneeScolaire;
import com.kalanblow.school_management.model.classe.SchoolClass;
import com.kalanblow.school_management.model.etablissement.Etablissement;
import com.kalanblow.school_management.repository.AnneeScolaireRepository;
import com.kalanblow.school_management.repository.EtablissementRepository;
import com.kalanblow.school_management.repository.SchoolClassRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SchoolClassService {
    private final SchoolClassRepository schoolClassRepository;
    private final EtablissementRepository etablissementRepository;
    private final AnneeScolaireRepository anneeScolaireRepository;

    public SchoolClassService(SchoolClassRepository schoolClassRepository, EtablissementRepository etablissementRepository, AnneeScolaireRepository anneeScolaireRepository) {
        this.schoolClassRepository = schoolClassRepository;
        this.etablissementRepository = etablissementRepository;
        this.anneeScolaireRepository = anneeScolaireRepository;
    }

    @Transactional(readOnly = true)
    public SchoolClass findSchoolClassById(Long id) {
        return schoolClassRepository.findSchoolClassById(id).orElseThrow(() -> new EntityNotFoundException(" School class not found with id " + id));
    }

    @Transactional(readOnly = true)
    public SchoolClass findSchoolClassByName(String name) {
        return schoolClassRepository.findSchoolClassByName(name).orElseThrow(() -> new EntityNotFoundException(" School class not found with name " + name));
    }

    public SchoolClass create(SchoolClass schoolClass) {
        Etablissement etablissement = etablissementRepository.findById(schoolClass.getEtablissement().getEtablisementScolaireId()).orElseThrow(() -> new EntityNotFoundException("Établissement non trouvé"));
        AnneeScolaire anneeScolaire = anneeScolaireRepository.findById(schoolClass.getAnneeScolaire().getAnneeScolaireId()).orElseThrow(() -> new EntityNotFoundException("Année scolaire non trouvée"));
        schoolClass.setEtablissement(etablissement);
        schoolClass.setAnneeScolaire(anneeScolaire);
        return schoolClassRepository.create(schoolClass);
    }

    public boolean existSchoolClass(Long id) {
        return schoolClassRepository.existSchoolClass(id);
    }

    public SchoolClass updatedSchoolClass(Long id, SchoolClass schoolClass) {

        SchoolClass updated = schoolClassRepository.findSchoolClassById(id).orElseThrow(() -> new EntityNotFoundException(" SchoolClass not found with " + id));

        if (updated != null) {

            updated.setCapacity(schoolClass.getCapacity());
            updated.setName(schoolClass.getName());
        }
        return schoolClassRepository.updatedSchoolClass(id, updated);
    }

    public void deleteSchoolClassById(Long id) {

        if (!existSchoolClass(id)) {

            throw new EntityNotFoundException("School classe not found with " + id);
        }
        schoolClassRepository.deleteSchoolClassById(id);
    }

    public Page<SchoolClass> search(Specification<SchoolClass> specification, Pageable pageable) {
        return schoolClassRepository.search(specification, pageable);
    }

}
