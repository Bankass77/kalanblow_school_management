package com.kalanblow.school_management.application.usecase.abscence;

import com.kalanblow.school_management.model.anneescolaire.Abscence;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class AbscenceSpecifications {
    // Recherche par date d'Abscence (égalité exacte)
    public static Specification<Abscence> dateAbscenceEquals(LocalDate dateAbscence) {
        return (root, query, criteriaBuilder) ->
                dateAbscence == null ?
                        criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get("dateAbscence"), dateAbscence);
    }

    // Recherche par période (entre deux dates)
    public static Specification<Abscence> dateAbscenceBetween(LocalDate startDate, LocalDate endDate) {
        return (root, query, criteriaBuilder) -> {
            if (startDate == null && endDate == null) {
                return criteriaBuilder.conjunction();
            }
            if (startDate == null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("dateAbscence"), endDate);
            }
            if (endDate == null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("dateAbscence"), startDate);
            }
            return criteriaBuilder.between(root.get("dateAbscence"), startDate, endDate);
        };
    }

    // Recherche par date de début
    public static Specification<Abscence> dateAbscenceAfter(LocalDate startDate) {
        return (root, query, criteriaBuilder) ->
                startDate == null ?
                        criteriaBuilder.conjunction() :
                        criteriaBuilder.greaterThanOrEqualTo(root.get("dateAbscence"), startDate);
    }

    // Recherche par date de fin
    public static Specification<Abscence> dateAbscenceBefore(LocalDate endDate) {
        return (root, query, criteriaBuilder) ->
                endDate == null ?
                        criteriaBuilder.conjunction() :
                        criteriaBuilder.lessThanOrEqualTo(root.get("dateAbscence"), endDate);
    }

    // Recherche par étudiant
    public static Specification<Abscence> studentIdEquals(Long studentId) {
        return (root, query, criteriaBuilder) ->
                studentId == null ?
                        criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get("student").get("studentId"), studentId);
    }

    // Recherche par période scolaire
    public static Specification<Abscence> periodeIdEquals(Long periodeId) {
        return (root, query, criteriaBuilder) ->
                periodeId == null ?
                        criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get("periode").get("id"), periodeId);
    }

    // Recherche par statut de justification
    public static Specification<Abscence> justifieEquals(Boolean justifie) {
        return (root, query, criteriaBuilder) ->
                justifie == null ?
                        criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get("justifie"), justifie);
    }

    // Recherche par motif (recherche texte, insensible à la casse)
    public static Specification<Abscence> motifContains(String motif) {
        return (root, query, criteriaBuilder) ->
                motif == null || motif.trim().isEmpty() ?
                        criteriaBuilder.conjunction() :
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("motif")),
                                "%" + motif.toLowerCase() + "%"
                        );
    }
    // Recherche combinée - exemple d'utilisation multiple
    public static Specification<Abscence> searchAbsences(
            Long studentId,
            Long periodeId,
            LocalDate startDate,
            LocalDate endDate,
            Boolean justifie,
            String motif) {

        return Specification.where(studentIdEquals(studentId))
                .and(periodeIdEquals(periodeId))
                .and(dateAbscenceBetween(startDate, endDate))
                .and(justifieEquals(justifie))
                .and(motifContains(motif));
    }
}
