package com.awt.studybuddy.controller;

import com.awt.studybuddy.dto.AssignmentRequestDTO;
import com.awt.studybuddy.dto.AssignmentResponseDTO;
import com.awt.studybuddy.entity.AssignmentEntity;
import com.awt.studybuddy.mapper.AssignmentMapper;
import com.awt.studybuddy.service.AssignmentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/assignments")
public class AssignmentController {
    private final AssignmentService assignmentService;
    private final AssignmentMapper assignmentMapper;

    public AssignmentController(AssignmentService assignmentService, AssignmentMapper assignmentMapper) {
        this.assignmentService = assignmentService;
        this.assignmentMapper = assignmentMapper;
    }

    @GetMapping
    public ResponseEntity<?> getAllSortedByDeadline(@RequestParam String sort) {
        try {
            if (!sort.equalsIgnoreCase("date")) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Invalid sort parameter."));
            }

            List<AssignmentEntity> assignments = assignmentService.getAllSortedByDeadline();

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

    @GetMapping("/{id}")
    public ResponseEntity<?> getAssignmentById(@PathVariable Long id) {
        try {
            AssignmentEntity assignment = assignmentService.findById(id);
            AssignmentResponseDTO response = assignmentMapper.toDto(assignment);
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Assignment not found."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "An unexpected error occurred."));
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateAssignment(@PathVariable Long id, @RequestBody AssignmentRequestDTO request) {
        try {
            AssignmentEntity entity = assignmentMapper.toEntity(request);
            AssignmentEntity updated = assignmentService.updateAssignment(id, entity);
            if (updated == null) {
                return new ResponseEntity<>(Map.of("error", "Assignment not found."), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(assignmentMapper.toDto(updated), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", "An unexpected error occurred."), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAssignment(@PathVariable Long id) {
        try {
            boolean deleted = assignmentService.deleteAssignment(id);
            if (!deleted) {
                return new ResponseEntity<>(Map.of("error", "Assignment not found."), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(Map.of("message", "Assignment deleted successfully."), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", "An unexpected error occurred."), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
