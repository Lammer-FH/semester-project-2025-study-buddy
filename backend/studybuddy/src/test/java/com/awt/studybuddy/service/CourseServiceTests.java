package com.awt.studybuddy.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

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

    @Mock
    private UserService userService;

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

    @Test
    void shouldCreateCourse() {
        UserEntity user = new UserEntity();
        user.setId(1L);

        CourseEntity course = new CourseEntity();
        course.setTitle("Physics");

        CourseEntity saved = new CourseEntity();
        saved.setId(42L);
        saved.setTitle("Physics");

        when(userService.getHardcodedUser()).thenReturn(user); // ✅ add this
        when(courseRepository.save(any())).thenReturn(saved);

        CourseEntity result = courseService.createCourse(course);

        assertThat(result.getId()).isEqualTo(42L);
        assertThat(result.getTitle()).isEqualTo("Physics");
    }


    @Test
    void shouldUpdateCourse() {
        Long courseId = 5L;

        CourseEntity existing = new CourseEntity();
        existing.setId(courseId);
        existing.setTitle("Old");

        CourseEntity update = new CourseEntity();
        update.setTitle("New Title");
        update.setDescription("New Description");

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(existing));
        when(courseRepository.save(existing)).thenReturn(existing);

        CourseEntity result = courseService.updateCourse(courseId, update);

        assertThat(result.getTitle()).isEqualTo("New Title");
        assertThat(result.getDescription()).isEqualTo("New Description");
    }

    @Test
    void updateCourse_shouldThrowWhenNotFound() {
        Long courseId = 999L;
        CourseEntity update = new CourseEntity();
        update.setTitle("Missing");

        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> courseService.updateCourse(courseId, update))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Course not found");
    }

    @Test
    void shouldDeleteCourseAndReturnTrue() {
        Long id = 7L;

        when(courseRepository.existsById(id)).thenReturn(true);

        boolean result = courseService.deleteCourse(id);

        assertThat(result).isTrue();
        verify(courseRepository).deleteById(id);
    }

    @Test
    void shouldNotDeleteWhenCourseMissing() {
        Long id = 8L;

        when(courseRepository.existsById(id)).thenReturn(false);

        boolean result = courseService.deleteCourse(id);

        assertThat(result).isFalse();
        verify(courseRepository, never()).deleteById(id);
    }

}
