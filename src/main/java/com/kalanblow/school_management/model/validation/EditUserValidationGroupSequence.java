package com.kalanblow.school_management.model.validation;

import jakarta.validation.GroupSequence;
import jakarta.validation.groups.Default;

@GroupSequence({Default.class, ValidationGroupOne.class})
public interface EditUserValidationGroupSequence {
}
