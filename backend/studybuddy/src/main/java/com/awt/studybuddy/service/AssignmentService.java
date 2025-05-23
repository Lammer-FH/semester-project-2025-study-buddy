package com.awt.studybuddy.service;

import com.awt.studybuddy.entity.AssignmentEntity;
import com.awt.studybuddy.repository.AssignmentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssignmentService {
    private final AssignmentRepository assignmentRepository;

    public AssignmentService(AssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }

    public List<AssignmentEntity> getAllSortedByDeadline() {
        return assignmentRepository.findAllByOrderByDeadlineAsc();
    }

    public List<AssignmentEntity> findByCourseId(Long id) {
        return assignmentRepository.findByCourseIdOrderByDeadlineAsc(id);
    }

    public AssignmentEntity findById(Long id) {
        return assignmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Assignment not found"));
    }
}
