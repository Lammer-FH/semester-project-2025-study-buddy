 package com.awt.studybuddy.service;

import com.awt.studybuddy.entity.AssignmentEntity;
import com.awt.studybuddy.entity.CourseEntity;
import com.awt.studybuddy.repository.AssignmentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class AssignmentService {
    private final AssignmentRepository assignmentRepository;
    private final CourseService courseService;

    public AssignmentService(AssignmentRepository assignmentRepository, CourseService courseService) {
        this.assignmentRepository = assignmentRepository;
        this.courseService = courseService;
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

    public List<AssignmentEntity> getAllAssignments(String sort) {
        List<AssignmentEntity> all = assignmentRepository.findAll();
        if ("date".equalsIgnoreCase(sort)) {
            all.sort(Comparator.comparing(AssignmentEntity::getDeadline));
        }
        return all;
    }

    public List<AssignmentEntity> getAssignmentsByCourseId(Long courseId, String sort) {
        List<AssignmentEntity> list = assignmentRepository.findByCourseIdOrderByDeadlineAsc(courseId);
        if ("date".equalsIgnoreCase(sort)) {
            list.sort(Comparator.comparing(AssignmentEntity::getDeadline));
        }
        return list;
    }

    public AssignmentEntity createAssignment(Long courseId, AssignmentEntity assignment) {
        CourseEntity course = courseService.findById(courseId);
        assignment.setCourse(course);
        return assignmentRepository.save(assignment);
    }

    public AssignmentEntity updateAssignment(Long id, AssignmentEntity updated) {
        AssignmentEntity existing = assignmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Assignment not found"));

        existing.setTitle(updated.getTitle());
        existing.setDescription(updated.getDescription());
        existing.setDeadline(updated.getDeadline());

        return assignmentRepository.save(existing);
    }

    public boolean deleteAssignment(Long id) {
        if (!assignmentRepository.existsById(id)) {
            return false;
        }
        assignmentRepository.deleteById(id);
        return true;
    }

}