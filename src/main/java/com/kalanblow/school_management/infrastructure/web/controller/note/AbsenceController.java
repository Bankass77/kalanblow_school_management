package com.kalanblow.school_management.infrastructure.web.controller.note;

import com.kalanblow.school_management.application.dto.note.AbscenceRequest;
import com.kalanblow.school_management.application.dto.note.AbscenceResponse;
import com.kalanblow.school_management.application.dto.note.StatistiquesAbsences;
import com.kalanblow.school_management.application.dto.page.PageResponse;
import com.kalanblow.school_management.application.dto.student.StudentResponse;
import com.kalanblow.school_management.application.mapper.AbscenceMapper;
import com.kalanblow.school_management.application.mapper.PageMapper;
import com.kalanblow.school_management.application.usecase.abscence.*;
import com.kalanblow.school_management.application.usecase.student.StudentPageCacheKey;
import com.kalanblow.school_management.infrastructure.persistence.student.StudentSpecifications;
import com.kalanblow.school_management.model.anneescolaire.Abscence;
import com.kalanblow.school_management.model.shared.util.StringNormalizer;
import com.kalanblow.school_management.model.student.Student;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/absences")
@RequiredArgsConstructor
public class AbsenceController {

    private final CreateAbsenceUseCase createUseCase;
    private final GetAbsenceUseCase getAbsceneceUseCase;
    private final UpdateAbscenceUseCase updateAbscenceUseCase;
    private final AbscenceMapper abscenceMapper;
    private final DeleteAbscenceUseCase deleteAbscenceUseCase;
    private final PageMapper pageMapper;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Abscence create(@RequestBody @Valid AbscenceRequest abscenceRequest) {
        return createUseCase.create(abscenceRequest);
    }

    @GetMapping("/{id}")
    public Abscence getAbscenceById(@PathVariable Long id) {

        return getAbsceneceUseCase.getAbscenceById(id);
    }

    @PutMapping("/{id}")
    public Abscence updateAbscence(@PathVariable Long id, @RequestBody @Valid AbscenceRequest abscenceRequest){
        Abscence abscence = abscenceMapper.toEntity(abscenceRequest);
        return  updateAbscenceUseCase.updateAbscence(id, abscence);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAbsence(@PathVariable Long id) {
        deleteAbscenceUseCase.deleteAbscence(id);
    }

    @GetMapping("/student/{studentId}")
    public List<Abscence> getAbsencesByStudent(@PathVariable Long studentId) {
        return getAbsceneceUseCase.getAbsencesByStudent(studentId);
    }

    @GetMapping("/student/{studentId}/periode/{periodeId}")
    public List<Abscence> getAbsencesByStudentAndPeriode(@PathVariable Long studentId,
                                                        @PathVariable Long periodeId) {
        return getAbsceneceUseCase.getAbsencesByStudentAndPeriode(studentId, periodeId);
    }

    @GetMapping("/student/{studentId}/statistiques")
    public StatistiquesAbsences getStatistiques(@PathVariable Long studentId) {
        return getAbsceneceUseCase.getStatistiques(studentId);
    }

    @PostMapping("/{id}/justifier")
    public Abscence justifierAbsence(@PathVariable Long id,
                                    @RequestParam(required = false) String motif) {
        return getAbsceneceUseCase.justifierAbsence(id, motif);
    }

    @GetMapping
    public ResponseEntity<PageResponse<AbscenceResponse>> searchAbscences(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Long studentId,
            @RequestParam(required = false) Long periodeId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) Boolean justifie,
            @RequestParam(required = false) String motif) {

        page = Math.max(page, 0);
        size = Math.max(Math.max(size, 1), 20);
        Pageable pageable = PageRequest.of(page, size);

        Specification<Abscence> spec;
        if (startDate != null || endDate != null || periodeId != null || studentId !=null || motif !=null) {
            spec = Specification
                    .where(AbscenceSpecifications.dateAbscenceAfter(endDate)
                    .and(AbscenceSpecifications.dateAbscenceBetween(startDate,endDate))
                    .and(AbscenceSpecifications.studentIdEquals(studentId))
                    .and(AbscenceSpecifications.periodeIdEquals(periodeId))
                            .and(AbscenceSpecifications.dateAbscenceBefore(endDate)));
        } else {
            spec = Specification.where((root, query, cb) -> null);
        }
        AbscencePageCacheKey cacheKey =
                new AbscencePageCacheKey(page,size,studentId, startDate, endDate, periodeId, motif
                );

        Page<Abscence> abscences = getAbsceneceUseCase.execute(cacheKey, pageable, spec);
        return ResponseEntity.ok(pageMapper.toPageResponse(abscences, abscenceMapper::toResponse));
    }

}
