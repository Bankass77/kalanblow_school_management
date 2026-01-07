package com.kalanblow.school_management.application.dto.etablissement;

import com.kalanblow.school_management.model.shared.Email;
import com.kalanblow.school_management.model.shared.PhoneNumber;
import com.kalanblow.school_management.model.shared.UserName;

public record ChefEtablissementResponse(
        Long id,
        UserName userName,
        Email email,
        PhoneNumber phone
) {}
