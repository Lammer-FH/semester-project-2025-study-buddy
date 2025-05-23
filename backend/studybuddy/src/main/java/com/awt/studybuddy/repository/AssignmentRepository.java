package com.awt.studybuddy.repository;

import com.awt.studybuddy.entity.AssignmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignmentRepository extends JpaRepository<AssignmentEntity, Long> {
    List<AssignmentEntity> findAllByOrderByDeadlineAsc();
    List<AssignmentEntity> findByCourseIdOrderByDeadlineAsc(Long courseId);
}
