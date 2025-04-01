package com.task.TaskManagement.service;

import com.task.TaskManagement.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private EmailService emailService;

    public void sendNotification(User user, String message) {
        String subject = "Task Reminder";
        emailService.sendEmail(user.getEmail(),subject,message);

    }
}
