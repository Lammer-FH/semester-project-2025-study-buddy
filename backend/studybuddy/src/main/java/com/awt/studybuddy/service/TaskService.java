package com.awt.studybuddy.service;

import com.awt.studybuddy.entity.AssignmentEntity;
import com.awt.studybuddy.entity.TaskEntity;
import com.awt.studybuddy.repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final AssignmentService assignmentService;

    public TaskService(TaskRepository taskRepository, AssignmentService assignmentService) {
        this.taskRepository = taskRepository;
        this.assignmentService = assignmentService;
    }

    public List<TaskEntity> getTasksByAssignmentId(Long assignmentId) {
        assignmentService.findById(assignmentId);
        return taskRepository.findByAssignmentId(assignmentId);
    }

    public TaskEntity addTaskToAssignment(Long assignmentId, TaskEntity task) {
        AssignmentEntity assignment = assignmentService.findById(assignmentId);
        task.setAssignment(assignment);
        return taskRepository.save(task);
    }

    public TaskEntity updateTask(Long id, TaskEntity updated) {
        TaskEntity existing = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));
        existing.setDescription(updated.getDescription());
        existing.setDone(updated.isDone());
        return taskRepository.save(existing);
    }

    public boolean deleteTask(Long id) {
        if (!taskRepository.existsById(id)) return false;
        taskRepository.deleteById(id);
        return true;
    }
}
