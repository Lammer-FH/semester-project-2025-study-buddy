package com.awt.studybuddy.controller;

import com.awt.studybuddy.dto.assignment.AssignmentResponse;
import com.awt.studybuddy.entity.AssignmentEntity;
import com.awt.studybuddy.mapper.AssignmentMapper;
import com.awt.studybuddy.service.AssignmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

public class AssignmentControllerTests {

    @Mock
    private AssignmentService assignmentService;

    @Mock
    private AssignmentMapper assignmentMapper;

    @InjectMocks
    private AssignmentController assignmentController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllSortedByDeadline_returnsOkWithAssignments() {
        AssignmentEntity entity = new AssignmentEntity();
        entity.setTitle("Test Assignment");
        entity.setDeadline(LocalDate.of(2025, 5, 1));

        AssignmentResponse dto = new AssignmentResponse();
        dto.setTitle("Test Assignment");
        dto.setDeadline(LocalDate.of(2025, 5, 1));

        when(assignmentService.getAllSortedByDeadline()).thenReturn(List.of(entity));
        when(assignmentMapper.toDto(entity)).thenReturn(dto);

        ResponseEntity<?> response = assignmentController.getAllSortedByDeadline("date");

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
        @SuppressWarnings("unchecked")
        List<AssignmentResponse> body = (List<AssignmentResponse>) response.getBody();

        assertThat(body.get(0).getTitle()).isEqualTo("Test Assignment");    }

    @Test
    void getAllSortedByDeadline_returnsNoContentWhenEmpty() {
        when(assignmentService.getAllSortedByDeadline()).thenReturn(List.of());

        ResponseEntity<?> response = assignmentController.getAllSortedByDeadline("date");
        assertThat(response.getStatusCodeValue()).isEqualTo(204);
    }

    @Test
    void getAllSortedByDeadline_returnsBadRequestWhenSortNotDate() {
        ResponseEntity<?> response = assignmentController.getAllSortedByDeadline("bla");

        assertThat(response.getStatusCodeValue()).isEqualTo(400);
    }
}