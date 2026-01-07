package com.kalanblow.school_management.model.notification;

import com.kalanblow.school_management.model.shared.enums.TypeNotification;
import com.kalanblow.school_management.model.student.Student;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationMessage {
    private Student student;
    private String titre;
    private String contenu;
    private TypeNotification type;
    private boolean important;
}
