package com.kalanblow.school_management.application.dto.inscription;

import com.kalanblow.school_management.model.anneescolaire.AnneeScolaire;
import com.kalanblow.school_management.model.inscription.DecisionConseil;
import com.kalanblow.school_management.model.inscription.Inscription;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Setter
@Getter
public class ProcesVerbalConseil {
    private AnneeScolaire anneeScolaire;
    private LocalDate dateConseil;
    private Map<DecisionConseil, List<Inscription>> decisions;
    private Map<String, Object> statistiques;
    private byte[] documentPdf;
}
