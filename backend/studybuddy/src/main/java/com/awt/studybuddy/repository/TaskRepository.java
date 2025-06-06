package com.awt.studybuddy.repository;

import com.awt.studybuddy.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    List<TaskEntity> findByAssignmentId(Long assignmentId);
}
