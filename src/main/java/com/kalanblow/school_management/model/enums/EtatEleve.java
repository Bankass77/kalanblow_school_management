package com.kalanblow.school_management.model.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum EtatEleve {
    NOUVEAU,
    PROMU,
    REDOUBLE
}
