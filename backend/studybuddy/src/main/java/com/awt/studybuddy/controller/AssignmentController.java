package com.awt.studybuddy.controller;

import com.awt.studybuddy.dto.assignment.AssignmentResponse;
import com.awt.studybuddy.entity.AssignmentEntity;
import com.awt.studybuddy.mapper.AssignmentMapper;
import com.awt.studybuddy.service.AssignmentService;
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

            List<AssignmentResponse> responseList = assignments.stream()
                    .map(assignmentMapper::toDto)
                    .toList();

            return ResponseEntity.ok(responseList);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "An unexpected error occurred."));
        }
    }
}
