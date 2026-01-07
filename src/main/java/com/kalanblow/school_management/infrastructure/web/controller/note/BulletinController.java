package com.kalanblow.school_management.infrastructure.web.controller.note;

import com.kalanblow.school_management.application.dto.note.BulletinCommand;
import com.kalanblow.school_management.application.usecase.note.ConsulterBulletinUseCase;
import com.kalanblow.school_management.application.usecase.note.CreerBulletinUseCase;
import com.kalanblow.school_management.application.usecase.note.GenererBulletinCompletUseCase;
import com.kalanblow.school_management.model.anneescolaire.Bulletin;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bulletins")
@RequiredArgsConstructor
public class BulletinController {

    private final CreerBulletinUseCase creerBulletinUseCase;
    private final ConsulterBulletinUseCase consulterBulletinUseCase;
    private final GenererBulletinCompletUseCase genererBulletinCompletUseCase;

    @PostMapping
    public ResponseEntity<Bulletin> creerBulletin(@RequestBody BulletinCommand command) {
        Bulletin bulletin = creerBulletinUseCase.execute(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(bulletin);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bulletin> getBulletin(@PathVariable Long id) {
        return ResponseEntity.ok(consulterBulletinUseCase.getById(id));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Bulletin>> getBulletinsByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(consulterBulletinUseCase.getByStudent(studentId));
    }

    @PostMapping("/generer/{studentId}/{periodeId}")
    public ResponseEntity<Bulletin> genererBulletin(
            @PathVariable Long studentId,
            @PathVariable Long periodeId
    ) {
        return ResponseEntity.ok(genererBulletinCompletUseCase.execute(studentId, periodeId));
    }
}

