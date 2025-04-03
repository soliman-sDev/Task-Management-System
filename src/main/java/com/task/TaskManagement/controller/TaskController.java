package com.task.TaskManagement.controller;


import com.task.TaskManagement.dto.TasksDTO;
import com.task.TaskManagement.model.Task;
import com.task.TaskManagement.service.TaskReminderService;
import com.task.TaskManagement.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Tasks", description = "Manage tasks in the system")
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;
    @Autowired
    private TaskReminderService reminderService;

    @Operation(summary = "Get all tasks")
    @GetMapping
    public ResponseEntity<List<TasksDTO>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @Operation(summary = "Get task by ID")
    @GetMapping("/{id}")
    public ResponseEntity<TasksDTO> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @Operation(summary = "send email reminder to assigned user")
    @PostMapping("/send")
    public ResponseEntity<String> triggerReminders() {
        reminderService.sentTaskRemainders();
        return ResponseEntity.ok("Reminders sent manually");
    }

    @Operation(summary = "Create a new task")
    @PostMapping
    public ResponseEntity<TasksDTO> createTask(@Valid @RequestBody TasksDTO taskDTO) {
        return ResponseEntity.ok(taskService.createTask(taskDTO));
    }

    @Operation(summary = "update existing task")
    @PutMapping("/{id}")
    public ResponseEntity<TasksDTO> updateTask(@PathVariable Long id,@Valid @RequestBody TasksDTO taskDTO) {
        return ResponseEntity.ok(taskService.updateTask(id,taskDTO));
    }

    @Operation(summary = "Delete task by ID")
    @DeleteMapping("/{id}")
    public  void deleteById(@PathVariable Long id){
         taskService.deleteTask(id);
    }

    @Operation(summary = "test Get request")
    @GetMapping("/test")
    public ResponseEntity<Object> Tasting() {
        return ResponseEntity.ok("It worked");
    }
}
