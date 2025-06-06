package com.awt.studybuddy.controller;

import com.awt.studybuddy.dto.task.TaskRequest;
import com.awt.studybuddy.dto.task.TaskResponse;
import com.awt.studybuddy.entity.TaskEntity;
import com.awt.studybuddy.mapper.TaskMapper;
import com.awt.studybuddy.service.TaskService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    public TaskController(TaskService taskService, TaskMapper taskMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }

    @GetMapping("/assignments/{id}/tasks")
    public ResponseEntity<?> getTasks(@PathVariable Long id) {
        try {
            List<TaskEntity> tasks = taskService.getTasksByAssignmentId(id);
            if (tasks.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Map.of("message", "Tasks not found."));
            }
            List<TaskResponse> response = tasks.stream().map(taskMapper::toDto).collect(Collectors.toList());
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Assignment not found."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "An unexpected error occurred."));
        }
    }

    @PostMapping("/assignments/{id}/tasks")
    public ResponseEntity<?> addTask(@PathVariable Long id, @RequestBody TaskRequest request) {
        try {
            TaskEntity entity = taskMapper.toEntity(request);
            TaskEntity saved = taskService.addTaskToAssignment(id, entity);
            return ResponseEntity.status(HttpStatus.CREATED).body(taskMapper.toDto(saved));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Assignment not found."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "An unexpected error occurred."));
        }
    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id, @RequestBody TaskRequest request) {
        try {
            TaskEntity entity = taskMapper.toEntity(request);
            TaskEntity updated = taskService.updateTask(id, entity);
            return ResponseEntity.ok(taskMapper.toDto(updated));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Task not found."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "An unexpected error occurred."));
        }
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        try {
            boolean deleted = taskService.deleteTask(id);
            if (!deleted) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Task not found."));
            }
            return ResponseEntity.ok(Map.of("message", "Task deleted successfully."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "An unexpected error occurred."));
        }
    }
}
