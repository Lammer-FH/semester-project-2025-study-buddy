package com.awt.studybuddy.controller;

import com.awt.studybuddy.dto.course.CourseRequest;
import com.awt.studybuddy.dto.course.CourseResponse;
import com.awt.studybuddy.entity.CourseEntity;
import com.awt.studybuddy.entity.UserEntity;
import com.awt.studybuddy.mapper.CourseMapper;
import com.awt.studybuddy.service.CourseService;
import com.awt.studybuddy.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;

public class CourseControllerTests {

    @Mock
    private CourseService courseService;

    @Mock
    private CourseMapper courseMapper;

    @Mock
    private UserService userService;

    @InjectMocks
    private CourseController controller;

    private final UserEntity user = new UserEntity();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user.setId(1L);
    }

    @Test
    void getAllCourses_returnsCourses_whenAvailable() {
        CourseEntity course = new CourseEntity();
        course.setTitle("Math");

        CourseResponse response = new CourseResponse();
        response.setTitle("Math");

        when(userService.getHardcodedUser()).thenReturn(user);
        when(courseService.getAllForUser(user)).thenReturn(List.of(course));
        when(courseMapper.toDto(course)).thenReturn(response);

        ResponseEntity<?> result = controller.getAllCourses();

        assertThat(result.getStatusCodeValue()).isEqualTo(200);

        List<CourseResponse> body = (List<CourseResponse>) result.getBody();
        assertThat(body).hasSize(1);
        assertThat(body.get(0).getTitle()).isEqualTo("Math");
    }

    @Test
    void getAllCourses_returnsNoContentWhenEmpty() {
        when(userService.getHardcodedUser()).thenReturn(user);
        when(courseService.getAllForUser(user)).thenReturn(List.of());

        ResponseEntity<?> result = controller.getAllCourses();

        assertThat(result.getStatusCodeValue()).isEqualTo(204);

        Map<String, String> body = (Map<String, String>) result.getBody();
        assertThat(body).containsEntry("message", "No courses found.");
    }

    @Test
    void createCourse_returnsCreated() {
        CourseRequest request = new CourseRequest();
        request.setTitle("New Course");

        CourseEntity entity = new CourseEntity();
        CourseEntity saved = new CourseEntity();
        saved.setId(10L);

        CourseResponse response = new CourseResponse();
        response.setId(10L);
        response.setTitle("New Course");

        when(courseMapper.toEntity(request)).thenReturn(entity);
        when(courseService.createCourse(entity)).thenReturn(saved);
        when(courseMapper.toDto(saved)).thenReturn(response);

        ResponseEntity<?> result = controller.createCourse(request);

        assertThat(result.getStatusCodeValue()).isEqualTo(201);
        CourseResponse body = (CourseResponse) result.getBody();
        assertThat(body.getId()).isEqualTo(10L);
    }

    @Test
    void createCourse_returnsBadRequest_whenMissingTitle() {
        CourseRequest request = new CourseRequest();
        request.setTitle(""); // invalid

        ResponseEntity<?> result = controller.createCourse(request);
        assertThat(result.getStatusCodeValue()).isEqualTo(400);

        Map<String, String> body = (Map<String, String>) result.getBody();
        assertThat(body).containsEntry("error", "Course title is required.");
    }

    @Test
    void updateCourse_returnsOk_whenSuccessful() {
        CourseRequest request = new CourseRequest();
        request.setTitle("Updated Course");

        CourseEntity updated = new CourseEntity();
        updated.setId(1L);
        updated.setTitle("Updated Course");

        CourseResponse response = new CourseResponse();
        response.setId(1L);
        response.setTitle("Updated Course");

        when(courseMapper.toEntity(request)).thenReturn(new CourseEntity());
        when(courseService.updateCourse(eq(1L), any())).thenReturn(updated);
        when(courseMapper.toDto(updated)).thenReturn(response);

        ResponseEntity<?> result = controller.updateCourse(1L, request);
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(((CourseResponse) result.getBody()).getTitle()).isEqualTo("Updated Course");
    }

    @Test
    void updateCourse_returnsNotFound_whenMissing() {
        CourseRequest request = new CourseRequest();
        request.setTitle("Nonexistent");

        when(courseMapper.toEntity(request)).thenReturn(new CourseEntity());
        when(courseService.updateCourse(eq(99L), any())).thenThrow(new EntityNotFoundException("Course not found."));

        ResponseEntity<?> result = controller.updateCourse(99L, request);
        assertThat(result.getStatusCodeValue()).isEqualTo(404);
        assertThat(((Map<?, ?>) result.getBody()).get("error")).isEqualTo("Course not found.");
    }

    @Test
    void deleteCourse_returnsOk_whenDeleted() {
        when(courseService.deleteCourse(1L)).thenReturn(true);

        ResponseEntity<?> result = controller.deleteCourse(1L);
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(((Map<?, ?>) result.getBody()).get("message")).isEqualTo("Course deleted successfully.");
    }

    @Test
    void deleteCourse_returnsNotFound_whenCourseDoesNotExist() {
        when(courseService.deleteCourse(999L)).thenReturn(false);

        ResponseEntity<?> result = controller.deleteCourse(999L);
        assertThat(result.getStatusCodeValue()).isEqualTo(404);
        assertThat(((Map<?, ?>) result.getBody()).get("error")).isEqualTo("Course not found.");
    }

}