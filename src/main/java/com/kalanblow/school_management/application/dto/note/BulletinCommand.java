package com.kalanblow.school_management.application.dto.note;

public record BulletinCommand(
        Long studentId,
        Long periodeId,
        Double moyenneGenerale,
        Integer classement,
        String appreciations
) {}
