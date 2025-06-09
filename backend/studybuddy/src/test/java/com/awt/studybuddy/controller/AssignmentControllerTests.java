package com.awt.studybuddy.controller;

import com.awt.studybuddy.dto.AssignmentRequestDTO;
import com.awt.studybuddy.dto.AssignmentResponseDTO;
import com.awt.studybuddy.entity.AssignmentEntity;
import com.awt.studybuddy.mapper.AssignmentMapper;
import com.awt.studybuddy.service.AssignmentService;
import com.awt.studybuddy.service.CourseService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

public class AssignmentControllerTests {

    @Mock
    private AssignmentService assignmentService;

    @Mock
    private CourseService courseService;

    @Mock
    private AssignmentMapper assignmentMapper;

    @InjectMocks
    private AssignmentController assignmentController;

    @InjectMocks
    private CourseAssignmentController courseAssignmentController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllSortedByDeadline_returnsOkWithAssignments() {
        AssignmentEntity entity = new AssignmentEntity();
        entity.setTitle("Test Assignment");
        entity.setDeadline(LocalDate.of(2025, 5, 1));

        AssignmentResponseDTO dto = new AssignmentResponseDTO();
        dto.setTitle("Test Assignment");
        dto.setDeadline(LocalDate.of(2025, 5, 1));

        when(assignmentService.getAllSortedByDeadline()).thenReturn(List.of(entity));
        when(assignmentMapper.toDto(entity)).thenReturn(dto);

        ResponseEntity<?> response = assignmentController.getAllSortedByDeadline("date");

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
        @SuppressWarnings("unchecked")
        List<AssignmentResponseDTO> body = (List<AssignmentResponseDTO>) response.getBody();

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

    @Test
    void getAssignmentsByCourse_returnsNotFoundIfCourseMissing() {
        Long courseId = 999L;
        when(courseService.findById(courseId)).thenThrow(new EntityNotFoundException("Course not found"));

        ResponseEntity<?> response = courseAssignmentController.getAssignmentsByCourse(courseId, "date");
        assertThat(response.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    void createAssignmentForCourse_returnsCreated() {
        Long courseId = 1L;
        AssignmentRequestDTO request = new AssignmentRequestDTO();
        request.setTitle("Write report");
        request.setDeadline(LocalDate.of(2025, 10, 5));

        AssignmentEntity entity = new AssignmentEntity();
        AssignmentEntity saved = new AssignmentEntity();
        AssignmentResponseDTO dto = new AssignmentResponseDTO();
        dto.setTitle("Write report");

        when(assignmentMapper.toEntity(request)).thenReturn(entity);
        when(assignmentService.createAssignment(courseId, entity)).thenReturn(saved);
        when(assignmentMapper.toDto(saved)).thenReturn(dto);

        ResponseEntity<?> response = courseAssignmentController.createAssignmentForCourse(courseId, request);
        assertThat(response.getStatusCodeValue()).isEqualTo(201);
    }

    @Test
    void createAssignmentForCourse_returnsBadRequestForPastDeadline() {
        AssignmentRequestDTO request = new AssignmentRequestDTO();
        request.setDeadline(LocalDate.now().minusDays(1));

        ResponseEntity<?> response = courseAssignmentController.createAssignmentForCourse(1L, request);
        assertThat(response.getStatusCodeValue()).isEqualTo(400);
    }

    @Test
    void createAssignmentForCourse_returnsNotFoundIfCourseMissing() {
        AssignmentRequestDTO request = new AssignmentRequestDTO();
        request.setTitle("Task X");
        request.setDeadline(LocalDate.now().plusDays(1));

        when(assignmentMapper.toEntity(request)).thenReturn(new AssignmentEntity());
        when(assignmentService.createAssignment(anyLong(), any())).thenReturn(null);

        ResponseEntity<?> response = courseAssignmentController.createAssignmentForCourse(999L, request);
        assertThat(response.getStatusCodeValue()).isEqualTo(404);
    }
}