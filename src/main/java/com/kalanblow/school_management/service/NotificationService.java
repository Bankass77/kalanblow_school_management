package com.kalanblow.school_management.service;

import com.kalanblow.school_management.model.inscription.DecisionConseil;
import com.kalanblow.school_management.model.notification.NotificationMessage;
import com.kalanblow.school_management.model.student.Student;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    public void notifierExclusion(Student student, DecisionConseil decision) {

    }

    public void envoyerNotificationAuxParents(long studentId, NotificationMessage message) {
    }
}
