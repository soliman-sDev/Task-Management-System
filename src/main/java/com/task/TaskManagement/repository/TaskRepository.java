package com.task.TaskManagement.repository;


import com.task.TaskManagement.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByAssignedUserId(Long userId);
    List<Task> findByDueDateBetween(LocalDateTime start, LocalDateTime end);
    List<Task> findByDueDateBefore(LocalDateTime date);
}
