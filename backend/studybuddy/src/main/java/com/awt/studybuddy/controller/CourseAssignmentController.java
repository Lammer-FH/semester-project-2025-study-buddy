package com.awt.studybuddy.controller;

import com.awt.studybuddy.dto.AssignmentRequestDTO;
import com.awt.studybuddy.dto.AssignmentResponseDTO;
import com.awt.studybuddy.entity.AssignmentEntity;
import com.awt.studybuddy.mapper.AssignmentMapper;
import com.awt.studybuddy.service.AssignmentService;
import com.awt.studybuddy.service.CourseService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/courses/{id}/assignments")
public class CourseAssignmentController {

    private final AssignmentService assignmentService;
    private final CourseService courseService;
    private final AssignmentMapper assignmentMapper;

    public CourseAssignmentController(AssignmentService assignmentService, AssignmentMapper assignmentMapper, CourseService courseService) {
        this.assignmentService = assignmentService;
        this.assignmentMapper = assignmentMapper;
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<?> getAssignmentsByCourse(@PathVariable Long id, @RequestParam String sort) {

        try {
            if (!sort.equalsIgnoreCase("date")) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Invalid sort parameter."));
            }

            try {
                courseService.findById(id); // will throw if not found
            } catch (EntityNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Course not found."));
            }

            List<AssignmentEntity> assignments = assignmentService.findByCourseId(id);

            if (assignments.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                        .body(Map.of("message", "No assignments found."));
            }

            List<AssignmentResponseDTO> responseList = assignments.stream()
                    .map(assignmentMapper::toDto)
                    .toList();

            return ResponseEntity.ok(responseList);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "An unexpected error occurred."));
        }
    }

    @PostMapping
    public ResponseEntity<?> createAssignmentForCourse(@PathVariable Long id,
                                                       @RequestBody AssignmentRequestDTO request) {
        try {
            if (request.getDeadline() != null && request.getDeadline().isBefore(LocalDate.now())) {
                return new ResponseEntity<>(Map.of("error", "Deadline must be in the future."), HttpStatus.BAD_REQUEST);
            }
            AssignmentEntity entity = assignmentMapper.toEntity(request);
            AssignmentEntity created = assignmentService.createAssignment(id, entity);
            if (created == null) {
                return new ResponseEntity<>(Map.of("error", "Course not found."), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(assignmentMapper.toDto(created), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", "An unexpected error occurred."), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
