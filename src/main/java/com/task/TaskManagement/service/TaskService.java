
package com.task.TaskManagement.service;

import com.task.TaskManagement.dto.TasksDTO;
import com.task.TaskManagement.model.Task;
import com.task.TaskManagement.model.User;
import com.task.TaskManagement.repository.TaskRepository;
import com.task.TaskManagement.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {


    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public List<TasksDTO> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(this::convertToDTO).collect(Collectors.toList());
    }

    public TasksDTO getTaskById(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
        return convertToDTO(task);
    }

    @Transactional
    public TasksDTO createTask(TasksDTO taskDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            User creator = userRepository.findByUsername(auth.getName())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            User assignedUser = userRepository.findById(taskDTO.getAssignedUser())
                    .orElseThrow(() -> new UsernameNotFoundException("Assigned user not found"));
            Task task = new Task();
            task.setTitle(taskDTO.getTitle());
            task.setDescription(taskDTO.getDescription());
            task.setDueDate(taskDTO.getDueDate());
            task.setPriority(Task.Priority.valueOf(taskDTO.getPriority().toUpperCase()));
            task.setStatus(Task.Status.PENDING);
            task.setCreatedBy(creator);
            task.setAssignedUser(assignedUser);
            Task ourTask = taskRepository.save(task);
            if(ourTask != null && ourTask.getId() > 0) {
                taskDTO.setStatusCode(200);
                taskDTO.setMsg("Task Created Successfully");}
        return taskDTO;
    }
    @Transactional
    public TasksDTO updateTask(Long taskId, TasksDTO taskDTO) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (taskDTO.getTitle() != null) {
            task.setTitle(taskDTO.getTitle());
        }
        if (taskDTO.getDescription() != null) {
            task.setDescription(taskDTO.getDescription());
        }
        if (taskDTO.getDueDate() != null) {
            task.setDueDate(taskDTO.getDueDate());
        }
        if (taskDTO.getPriority() != null) {
            task.setPriority(Task.Priority.valueOf(taskDTO.getPriority().toUpperCase()));
        }
        if (taskDTO.getStatus() != null) {
            task.setStatus(Task.Status.valueOf(taskDTO.getStatus().toUpperCase()));
        }
        if (taskDTO.getAssignedUser() != null) {
            User assignedUser = userRepository.findById(taskDTO.getAssignedUser())
                    .orElseThrow(() -> new RuntimeException("Assigned user not found"));
            task.setAssignedUser(assignedUser);
        }

        task.setUpdatedAt(LocalDateTime.now());

        Task updatedTask = taskRepository.save(task);

        if (updatedTask != null && updatedTask.getId() > 0) {
            taskDTO.setStatusCode(200);
            taskDTO.setMsg("Task Updated Successfully");
        }

        return taskDTO;
    }


    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public TasksDTO convertToDTO(Task task){
        return new TasksDTO(task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                task.getPriority().name(),
                task.getStatus().name(),
                task.getAssignedUser().getId(),
                "Request Worked Successfully",
                200
        );
    }




}


