package com.awt.studybuddy.controller;

import com.awt.studybuddy.mapper.CourseMapper;
import com.awt.studybuddy.service.CourseService;
import com.awt.studybuddy.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/courses")
public class CourseController {
    private final CourseService courseService;
    private final CourseMapper courseMapper;
    private final UserService userService;

    public CourseController(CourseService courseService, CourseMapper courseMapper, UserService userService) {
        this.courseService = courseService;
        this.courseMapper = courseMapper;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> getAllCourses() {
        try {
            var user = userService.getHardcodedUser();
            var courses = courseService.getAllForUser(user);

            if (courses.isEmpty()) {
                Map<String, String> message = Map.of("message", "No courses found.");
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(message);
            }

            var courseResponses = courses.stream()
                    .map(courseMapper::toDto)
                    .toList();

            return ResponseEntity.ok(courseResponses);
        } catch (Exception e) {
            Map<String, String> error = Map.of("error", "An unexpected error occurred.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}
