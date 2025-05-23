package com.awt.studybuddy.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.*;

import com.awt.studybuddy.entity.CourseEntity;
import com.awt.studybuddy.entity.UserEntity;
import com.awt.studybuddy.repository.CourseRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

public class CourseServiceTests {
    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseService courseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnCoursesForUser() {
        UserEntity user = new UserEntity();
        user.setId(1L);

        CourseEntity course = new CourseEntity();
        course.setTitle("Math");

        when(courseRepository.findAllByUser(user)).thenReturn(List.of(course));

        List<CourseEntity> result = courseService.getAllForUser(user);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("Math");
    }

    @Test
    void shouldReturnCourseById() {
        CourseEntity course = new CourseEntity();
        course.setId(10L);

        when(courseRepository.findById(10L)).thenReturn(Optional.of(course));

        CourseEntity result = courseService.findById(10L);

        assertThat(result.getId()).isEqualTo(10L);
    }

    @Test
    void shouldThrowWhenCourseNotFound() {
        when(courseRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> courseService.findById(99L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Course not found");
    }
}
