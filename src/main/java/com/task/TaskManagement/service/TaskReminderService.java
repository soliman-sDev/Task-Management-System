package com.task.TaskManagement.service;


import com.task.TaskManagement.model.Task;
import com.task.TaskManagement.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskReminderService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private NotificationService notificationService;

    @Scheduled(cron = "0 0 9 * * ?")
    public void sentTaskRemainders() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextDay = now.plusDays(1);

        List<Task> upcomingTasks = taskRepository.findByDueDateBetween(now,nextDay);
        List<Task> overdueTasks = taskRepository.findByDueDateBefore(now);
        for (Task task: upcomingTasks) {
            notificationService.sendNotification(task.getAssignedUser(),
                    "Remainder: Task '" + task.getTitle() + "' is due soon!");
        }
        for (Task task: overdueTasks) {
            notificationService.sendNotification(task.getAssignedUser(),
                    "Alert: Task '" + task.getTitle() + "' is overdue!");
        }

    }
}
