package com.kalanblow.school_management.service;

import com.kalanblow.school_management.model.anneescolaire.Bulletin;
import com.kalanblow.school_management.model.inscription.DecisionConseil;
import com.kalanblow.school_management.repository.BulletinRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BulletinService {

    private final BulletinRepository bulletinRepository;

    public BulletinService(BulletinRepository bulletinRepository) {
        this.bulletinRepository = bulletinRepository;
    }

    public void genererBulletinFinAnnee(long studentId, Long anneeScolaireId, DecisionConseil decision) {
    }

    public Bulletin save(Bulletin bulletin) {

        return  bulletinRepository.save(bulletin);
    }
}
