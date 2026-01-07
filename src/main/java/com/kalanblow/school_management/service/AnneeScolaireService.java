package com.kalanblow.school_management.service;

import com.kalanblow.school_management.model.anneescolaire.AnneeScolaire;
import com.kalanblow.school_management.repository.AnneeScolaireRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AnneeScolaireService {

    private final AnneeScolaireRepository anneeScolaireRepository;

    public AnneeScolaireService(AnneeScolaireRepository anneeScolaireRepository) {
        this.anneeScolaireRepository = anneeScolaireRepository;
    }

    public AnneeScolaire save(AnneeScolaire annee) {

        return anneeScolaireRepository.save(annee);
    }


    public AnneeScolaire findById(Long id) {

        return anneeScolaireRepository.findById(id).orElseThrow( ()-> new EntityNotFoundException("L'année scolaire avec cet id "+ id + " n'a été trouvé."));
    }

    public AnneeScolaire findByAnneeScolaireDebut(Integer debut) {

        return anneeScolaireRepository.findByAnneeScolaireDebut(debut).orElseThrow( ()-> new EntityNotFoundException("L'année scolaire avec cet id "+ debut + " n'a été trouvé."));
    }

    public Page<AnneeScolaire> search(Specification<AnneeScolaire> spec, Pageable pageable) {


        Specification<AnneeScolaire> specification = spec==null? Specification.where((root, query, cb)-> null):spec;
        return anneeScolaireRepository.search(specification,pageable);
    }

    public boolean existsByAnneeScolaireDebut(Integer debut) {
        return anneeScolaireRepository.existsByAnneeScolaireDebut(debut);
    }


    public AnneeScolaire findByAnneeFin(Integer fin) {

        return anneeScolaireRepository.findByAnneeFin(fin).orElseThrow( ()-> new EntityNotFoundException("L'année scolaire avec cet id "+ fin + " n'a été trouvé."));
    }

    public AnneeScolaire update(Long id, AnneeScolaire anneeScolaire) {

        AnneeScolaire annee = anneeScolaireRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("L'annéee scolaire avec l'id " + id + " n'a pas été trouvé."));

        if (annee != null) {

            annee.setAnneeDebut(annee.getAnneeDebut());
            annee.setAnneeFin(anneeScolaire.getAnneeFin());
            annee.setEstActive(anneeScolaire.isEstActive());
            annee.setClasses(anneeScolaire.getClasses());
            annee.setEleves(anneeScolaire.getEleves());
            annee.setDateDebutInscription(anneeScolaire.getDateDebutInscription());
            annee.setDateFinInscription(anneeScolaire.getDateFinInscription());

        }
        return annee;
    }

}
