package com.kalanblow.school_management.application.dto.sanction;

import java.util.List;

public record DecisionConseilResponse(Long studentId,
                                      Integer nombreSanctions,
                                      Integer graviteTotale,
                                      String decision,
                                      List<SanctionResume> sanctions) {
}
