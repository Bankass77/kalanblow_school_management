package com.kalanblow.school_management.infrastructure.web.controller.sanction;

import com.kalanblow.school_management.application.dto.inscription.Sanction;
import com.kalanblow.school_management.application.dto.sanction.DecisionConseilResponse;
import com.kalanblow.school_management.application.dto.sanction.SanctionRequest;
import com.kalanblow.school_management.application.dto.sanction.SanctionResponse;
import com.kalanblow.school_management.application.mapper.SanctionMapper;

import com.kalanblow.school_management.service.DecisionConseilService;
import com.kalanblow.school_management.service.SanctionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sanctions")
@RequiredArgsConstructor
public class SanctionController {

    private final SanctionService sanctionService;
    private final SanctionMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SanctionResponse createSanction(@RequestBody @Valid SanctionRequest request) {
        var command = mapper.toEntity(request);
        //TODO: null à modifier
        Sanction sanction = sanctionService.creerSanction(null);
        return mapper.toResponse(sanction);
    }

    @GetMapping("/student/{studentId}")
    public List<SanctionResponse> getSanctionsByStudent(@PathVariable Long studentId) {
        return sanctionService.getSanctionsByStudent(studentId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @PostMapping("/{sanctionId}/appliquer")
    public SanctionResponse appliquerSanction(@PathVariable Long sanctionId) {
        var sanction = sanctionService.appliquerSanction(sanctionId);
        return mapper.toResponse(sanction);
    }

    @GetMapping("/student/{studentId}/decision-conseil")
    public DecisionConseilResponse getDecisionConseil(@PathVariable Long studentId) {
        return sanctionService.evaluerSanctionsPourDecision(studentId);
    }
}