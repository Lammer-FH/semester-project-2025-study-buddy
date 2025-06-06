package com.awt.studybuddy.service;

import com.awt.studybuddy.entity.AssignmentEntity;
import com.awt.studybuddy.entity.TaskEntity;
import com.awt.studybuddy.repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TaskServiceTests {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private AssignmentService assignmentService;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getTasksByAssignmentId_returnsTasks() {
        TaskEntity task = new TaskEntity();
        task.setDescription("Do research");

        when(assignmentService.findById(1L)).thenReturn(new AssignmentEntity());
        when(taskRepository.findByAssignmentId(1L)).thenReturn(List.of(task));

        List<TaskEntity> result = taskService.getTasksByAssignmentId(1L);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getDescription()).isEqualTo("Do research");
    }

    @Test
    void addTaskToAssignment_success() {
        TaskEntity task = new TaskEntity();
        AssignmentEntity assignment = new AssignmentEntity();
        assignment.setId(1L);

        when(assignmentService.findById(1L)).thenReturn(assignment);
        when(taskRepository.save(task)).thenReturn(task);

        TaskEntity result = taskService.addTaskToAssignment(1L, task);

        assertThat(result).isNotNull();
        assertThat(result.getAssignment()).isEqualTo(assignment);
    }

    @Test
    void updateTask_success() {
        TaskEntity existing = new TaskEntity();
        existing.setId(10L);
        existing.setDescription("Old");
        existing.setDone(false);

        TaskEntity updated = new TaskEntity();
        updated.setDescription("New");
        updated.setDone(true);

        when(taskRepository.findById(10L)).thenReturn(Optional.of(existing));
        when(taskRepository.save(existing)).thenReturn(existing);

        TaskEntity result = taskService.updateTask(10L, updated);

        assertThat(result.getDescription()).isEqualTo("New");
        assertThat(result.isDone()).isTrue();
    }

    @Test
    void updateTask_throwsWhenNotFound() {
        when(taskRepository.findById(404L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> taskService.updateTask(404L, new TaskEntity()))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Task not found");
    }

    @Test
    void deleteTask_success() {
        when(taskRepository.existsById(1L)).thenReturn(true);

        boolean deleted = taskService.deleteTask(1L);

        assertThat(deleted).isTrue();
        verify(taskRepository).deleteById(1L);
    }

    @Test
    void deleteTask_notFound() {
        when(taskRepository.existsById(2L)).thenReturn(false);

        boolean deleted = taskService.deleteTask(2L);

        assertThat(deleted).isFalse();
    }
}
