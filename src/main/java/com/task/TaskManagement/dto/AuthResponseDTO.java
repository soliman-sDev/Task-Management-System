package com.task.TaskManagement.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.task.TaskManagement.model.Task;
import com.task.TaskManagement.model.User;

import lombok.*;

import java.util.List;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthResponseDTO {
    private int statusCode;
    private String error;
    private String message;
    private String token;
    private String refreshToken;
    private String expirationTime;
    private String name;
    private String title;
    private String username;
    private String email;
    private String role;
    private String password;
    private List<Task> tasks;
    private User user;

    /*
    private Long id;

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @NotNull(message = "Due date is required")
    private LocalDateTime dueDate;

    private String priority;
    private String status;
    private Long assignedUserId;

     */
}
