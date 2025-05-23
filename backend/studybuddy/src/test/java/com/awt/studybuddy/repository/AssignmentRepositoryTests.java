package com.awt.studybuddy.repository;

import com.awt.studybuddy.entity.AssignmentEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;

@DataJpaTest
public class AssignmentRepositoryTests {

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Test
    void testFindAssignmentsSortedByDeadline() {
        AssignmentEntity a1 = new AssignmentEntity();
        a1.setTitle("Final Paper");
        a1.setDeadline(LocalDate.of(2025, 6, 1));

        AssignmentEntity a2 = new AssignmentEntity();
        a2.setTitle("Draft Paper");
        a2.setDeadline(LocalDate.of(2025, 5, 1));

        assignmentRepository.save(a1);
        assignmentRepository.save(a2);

        var sorted = assignmentRepository.findAllByOrderByDeadlineAsc();
        assertThat(sorted.get(0).getDeadline()).isBefore(sorted.get(1).getDeadline());
    }
}
