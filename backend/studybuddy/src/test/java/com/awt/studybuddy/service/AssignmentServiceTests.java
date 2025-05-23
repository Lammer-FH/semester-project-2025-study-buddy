package com.awt.studybuddy.service;

import com.awt.studybuddy.entity.AssignmentEntity;
import com.awt.studybuddy.repository.AssignmentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

public class AssignmentServiceTests {
    @Mock
    private AssignmentRepository assignmentRepository;

    @InjectMocks
    private AssignmentService assignmentService;

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
}
