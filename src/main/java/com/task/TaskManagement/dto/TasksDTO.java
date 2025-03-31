package com.task.TaskManagement.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
public class TasksDTO {

    private Long id;

    @NotBlank(message = "Title is required")
    private String title;
    private String description;

    @NotNull(message = "Due date is required")
    private LocalDateTime dueDate;
    private String priority;
    private String status;
    private Long assignedUser;
    private String Msg;
    private int StatusCode;
}
