package com.awt.studybuddy.controller;

import com.awt.studybuddy.dto.course.CourseResponse;
import com.awt.studybuddy.entity.CourseEntity;
import com.awt.studybuddy.entity.UserEntity;
import com.awt.studybuddy.mapper.CourseMapper;
import com.awt.studybuddy.service.CourseService;
import com.awt.studybuddy.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
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
}