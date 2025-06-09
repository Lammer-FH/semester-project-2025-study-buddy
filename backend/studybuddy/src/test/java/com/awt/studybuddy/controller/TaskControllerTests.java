package com.awt.studybuddy.controller;

import com.awt.studybuddy.dto.TaskRequestDTO;
import com.awt.studybuddy.dto.TaskResponseDTO;
import com.awt.studybuddy.entity.TaskEntity;
import com.awt.studybuddy.mapper.TaskMapper;
import com.awt.studybuddy.service.TaskService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class TaskControllerTests {

    @Mock
    private TaskService taskService;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskController taskController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getTasks_success() {
        TaskEntity task = new TaskEntity();
        task.setDescription("Read papers");

        TaskResponseDTO dto = new TaskResponseDTO();
        dto.setDescription("Read papers");

        when(taskService.getTasksByAssignmentId(1L)).thenReturn(List.of(task));
        when(taskMapper.toDto(task)).thenReturn(dto);

        ResponseEntity<?> response = taskController.getTasks(1L);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        List<TaskResponseDTO> body = (List<TaskResponseDTO>) response.getBody();
        assertThat(body).hasSize(1);
        assertThat(body.get(0).getDescription()).isEqualTo("Read papers");
    }

    @Test
    void getTasks_assignmentNotFound() {
        when(taskService.getTasksByAssignmentId(1L)).thenThrow(new EntityNotFoundException());

        ResponseEntity<?> response = taskController.getTasks(1L);
        assertThat(response.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    void addTask_success() {
        TaskRequestDTO request = new TaskRequestDTO();
        request.setDescription("New task");

        TaskEntity entity = new TaskEntity();
        TaskEntity saved = new TaskEntity();
        TaskResponseDTO dto = new TaskResponseDTO();
        dto.setDescription("New task");

        when(taskMapper.toEntity(request)).thenReturn(entity);
        when(taskService.addTaskToAssignment(1L, entity)).thenReturn(saved);
        when(taskMapper.toDto(saved)).thenReturn(dto);

        ResponseEntity<?> response = taskController.addTask(1L, request);

        assertThat(response.getStatusCodeValue()).isEqualTo(201);
        assertThat(((TaskResponseDTO) response.getBody()).getDescription()).isEqualTo("New task");
    }

    @Test
    void updateTask_success() {
        TaskRequestDTO request = new TaskRequestDTO();
        request.setDescription("Updated");

        TaskEntity entity = new TaskEntity();
        TaskEntity updated = new TaskEntity();
        TaskResponseDTO dto = new TaskResponseDTO();
        dto.setDescription("Updated");

        when(taskMapper.toEntity(request)).thenReturn(entity);
        when(taskService.updateTask(1L, entity)).thenReturn(updated);
        when(taskMapper.toDto(updated)).thenReturn(dto);

        ResponseEntity<?> response = taskController.updateTask(1L, request);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(((TaskResponseDTO) response.getBody()).getDescription()).isEqualTo("Updated");
    }

    @Test
    void deleteTask_success() {
        when(taskService.deleteTask(1L)).thenReturn(true);

        ResponseEntity<?> response = taskController.deleteTask(1L);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        Map<String, String> body = (Map<String, String>) response.getBody();
        assertThat(body).containsEntry("message", "Task deleted successfully.");
    }

    @Test
    void deleteTask_notFound() {
        when(taskService.deleteTask(2L)).thenReturn(false);

        ResponseEntity<?> response = taskController.deleteTask(2L);

        assertThat(response.getStatusCodeValue()).isEqualTo(404);
    }
}
