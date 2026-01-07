package com.kalanblow.school_management.model.matiere;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MatiereResultat {
    private String matiereCode;
    private String matiere;
    private double moyenne;
    private Integer coefficient;
}
