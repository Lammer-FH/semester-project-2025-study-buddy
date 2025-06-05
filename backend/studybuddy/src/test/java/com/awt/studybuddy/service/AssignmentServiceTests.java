package com.awt.studybuddy.service;

import com.awt.studybuddy.entity.AssignmentEntity;
import com.awt.studybuddy.entity.CourseEntity;
import com.awt.studybuddy.repository.AssignmentRepository;
import com.awt.studybuddy.repository.CourseRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class AssignmentServiceTests {
    @Mock
    private AssignmentRepository assignmentRepository;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private AssignmentService assignmentService;

    @Mock
    private CourseService courseService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnSortedAssignments() {
        var a1 = new AssignmentEntity(1L, "Title A", "description a", LocalDate.of(2025, 6, 1), null, null);
        var a2 = new AssignmentEntity(2L, "Title B", "description b", LocalDate.of(2025, 6, 2), null, null);

        when(assignmentRepository.findAllByOrderByDeadlineAsc()).thenReturn(List.of(a1, a2));

        var result = assignmentService.getAllSortedByDeadline();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getDeadline()).isBefore(result.get(1).getDeadline());
    }

    @Test
    void shouldReturnAssignmentsByCourseSorted() {
        Long courseId = 1L;
        var a1 = new AssignmentEntity(1L, "A", "desc", LocalDate.of(2025, 6, 1), null, null);
        var a2 = new AssignmentEntity(2L, "B", "desc", LocalDate.of(2025, 6, 5), null, null);

        when(assignmentRepository.findByCourseIdOrderByDeadlineAsc(courseId)).thenReturn(List.of(a2, a1));

        var result = assignmentService.getAssignmentsByCourseId(courseId, "date");

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getDeadline()).isBefore(result.get(1).getDeadline());
    }

    @Test
    void shouldReturnAssignmentById() {
        Long id = 10L;
        var assignment = new AssignmentEntity();
        assignment.setId(id);

        when(assignmentRepository.findById(id)).thenReturn(Optional.of(assignment));

        var result = assignmentService.findById(id);
        assertThat(result.getId()).isEqualTo(id);
    }

    @Test
    void shouldThrowIfAssignmentNotFound() {
        when(assignmentRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> assignmentService.findById(99L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Assignment not found");
    }

    @Test
    void shouldCreateAssignmentWithCourse() {
        Long courseId = 1L;
        var course = new CourseEntity();
        course.setId(courseId);

        var newAssignment = new AssignmentEntity();
        var savedAssignment = new AssignmentEntity();
        savedAssignment.setId(20L);

        when(courseService.findById(courseId)).thenReturn(course);
        when(assignmentRepository.save(any())).thenReturn(savedAssignment);

        var result = assignmentService.createAssignment(courseId, newAssignment);

        assertThat(result.getId()).isEqualTo(20L);
        assertThat(newAssignment.getCourse()).isEqualTo(course);
    }

    @Test
    void shouldUpdateAssignment() {
        Long id = 3L;
        var existing = new AssignmentEntity(id, "Old", "Old", LocalDate.of(2025, 1, 1), null, null);
        var update = new AssignmentEntity(null, "New", "New", LocalDate.of(2025, 12, 12), null, null);

        when(assignmentRepository.findById(id)).thenReturn(Optional.of(existing));
        when(assignmentRepository.save(existing)).thenReturn(existing);

        var result = assignmentService.updateAssignment(id, update);

        assertThat(result.getTitle()).isEqualTo("New");
        assertThat(result.getDeadline()).isEqualTo(LocalDate.of(2025, 12, 12));
    }

    @Test
    void shouldReturnTrueWhenDeleted() {
        Long id = 5L;
        when(assignmentRepository.existsById(id)).thenReturn(true);

        boolean result = assignmentService.deleteAssignment(id);

        assertThat(result).isTrue();
        verify(assignmentRepository).deleteById(id);
    }

    @Test
    void shouldReturnFalseWhenDeleteFails() {
        Long id = 6L;
        when(assignmentRepository.existsById(id)).thenReturn(false);

        boolean result = assignmentService.deleteAssignment(id);

        assertThat(result).isFalse();
        verify(assignmentRepository, never()).deleteById(id);
    }
}
