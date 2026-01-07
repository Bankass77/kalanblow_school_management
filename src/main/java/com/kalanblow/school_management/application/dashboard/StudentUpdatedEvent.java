package com.kalanblow.school_management.application.dashboard;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class StudentUpdatedEvent {
    private final Long studentId;
    private final Long oldClassId;
    private final Long newClassId;
    private final String studentName;
    private  final  boolean hasClassChanged;
}
