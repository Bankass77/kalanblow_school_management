package com.kalanblow.school_management.application.dashboard;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class StudentDeletedEvent {
    private final Long studentId;
    private final String studentName;
    private final Long classId;
    private final String className;
}