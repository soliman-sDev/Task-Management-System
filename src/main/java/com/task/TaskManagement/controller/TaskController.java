package com.task.TaskManagement.controller;


import com.task.TaskManagement.dto.TasksDTO;
import com.task.TaskManagement.model.Task;
import com.task.TaskManagement.service.TaskReminderService;
import com.task.TaskManagement.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;
    @Autowired
    private TaskReminderService reminderService;

    @GetMapping
    public ResponseEntity<List<TasksDTO>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TasksDTO> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @PostMapping("/send")
    public ResponseEntity<String> triggerReminders() {
        reminderService.sentTaskRemainders();
        return ResponseEntity.ok("Reminders sent manually");
    }

    @PostMapping
    public ResponseEntity<TasksDTO> createTask(@Valid @RequestBody TasksDTO taskDTO) {
        return ResponseEntity.ok(taskService.createTask(taskDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TasksDTO> updateTask(@PathVariable Long id,@Valid @RequestBody TasksDTO taskDTO) {
        return ResponseEntity.ok(taskService.updateTask(id,taskDTO));
    }

    @DeleteMapping("/{id}")
    public  void deleteById(@PathVariable Long id){
         taskService.deleteTask(id);
    }

    @GetMapping("/test")
    public ResponseEntity<Object> Tasting() {
        return ResponseEntity.ok("It worked");
    }
}
