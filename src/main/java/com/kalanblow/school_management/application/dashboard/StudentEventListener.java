package com.kalanblow.school_management.application.dashboard;

import com.kalanblow.school_management.service.SchoolStatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;



@Component
@Slf4j
public class StudentEventListener {

    private final SchoolStatisticsService statisticsService;
    private final CacheManager cacheManager;

    public StudentEventListener(SchoolStatisticsService statisticsService, CacheManager cacheManager) {
        this.statisticsService = statisticsService;
        this.cacheManager = cacheManager;
    }

    @EventListener
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleStudentCreated(StudentCreatedEvent event) {
        try {
            // Invalider le cache quand un nouvel élève est ajouté
            clearDashboardCaches();

            log.info("Nouvel élève ajouté - ID: {}, Nom: {}, Classe: {}. Effectif total: {}",
                    event.getStudentId(),
                    event.getStudentName(),
                    event.getClassName(),
                    statisticsService.getCurrentStudentsCount());
        } catch (Exception e) {
            log.error("Erreur lors du traitement de l'événement StudentCreatedEvent", e);
        }
    }

    @EventListener
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleStudentDeleted(StudentDeletedEvent event) {
        try {
            // Invalider le cache quand un élève est supprimé
            clearDashboardCaches();

            log.info("Élève supprimé - ID: {}, Nom: {}, Classe: {}. Effectif total: {}",
                    event.getStudentId(),
                    event.getStudentName(),
                    event.getClassName(),
                    statisticsService.getCurrentStudentsCount());
        } catch (Exception e) {
            log.error("Erreur lors du traitement de l'événement StudentDeletedEvent", e);
        }
    }

    // Méthode utilitaire pour nettoyer les caches
    private void clearDashboardCaches() {
        try {
            Cache dashboardCache = cacheManager.getCache("schoolDashboard");
            if (dashboardCache != null) {
                dashboardCache.clear();
            }

            Cache capacityCache = cacheManager.getCache("schoolCapacity");
            if (capacityCache != null) {
                capacityCache.clear();
            }

            Cache classCache = cacheManager.getCache("classDetails");
            if (classCache != null) {
                classCache.clear();
            }

            log.debug("Caches du dashboard nettoyés avec succès");
        } catch (Exception e) {
            log.warn("Impossible de nettoyer les caches", e);
        }
    }

    // Optionnel : Pour gérer les mises à jour d'élève aussi
    @EventListener
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleStudentUpdated(StudentUpdatedEvent event) {
        try {
            // Vider le cache seulement si la classe a changé
            if (event.isHasClassChanged()) {
                clearDashboardCaches();
                log.info("Élève déplacé de classe - ID: {}. Caches nettoyés", event.getStudentId());
            }
        } catch (Exception e) {
            log.error("Erreur lors du traitement de l'événement StudentUpdatedEvent", e);
        }
    }
}