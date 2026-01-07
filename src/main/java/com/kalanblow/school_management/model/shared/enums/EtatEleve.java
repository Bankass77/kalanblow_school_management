package com.kalanblow.school_management.model.shared.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum EtatEleve {
    NOUVEAU,
    PROMU,
    REDOUBLE,
    EXCLU,
    EN_ORIENTATION,
    REDOUBLANT,
    EN_ATTENTE_AFFECTATION
}
