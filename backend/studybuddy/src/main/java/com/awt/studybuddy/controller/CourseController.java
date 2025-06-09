package com.awt.studybuddy.controller;

import com.awt.studybuddy.dto.CourseRequestDTO;
import com.awt.studybuddy.entity.CourseEntity;
import com.awt.studybuddy.mapper.CourseMapper;
import com.awt.studybuddy.service.CourseService;
import com.awt.studybuddy.service.UserService;
import jakarta.persistence.EntityNotFoundException;
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

    @PostMapping
    public ResponseEntity<?> createCourse(@RequestBody CourseRequestDTO request) {
        try {
            if (request.getTitle() == null || request.getTitle().isEmpty()) {
                return new ResponseEntity<>(Map.of("error", "Course title is required."), HttpStatus.BAD_REQUEST);
            }
            CourseEntity course = courseMapper.toEntity(request);
            CourseEntity created = courseService.createCourse(course);
            return new ResponseEntity<>(courseMapper.toDto(created), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", "An unexpected error occurred."), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCourse(@PathVariable Long id, @RequestBody CourseRequestDTO request) {
        try {
            CourseEntity course = courseMapper.toEntity(request);
            CourseEntity updated = courseService.updateCourse(id, course);
            return new ResponseEntity<>(courseMapper.toDto(updated), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", "An unexpected error occurred."), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long id) {
        try {
            boolean deleted = courseService.deleteCourse(id);
            if (!deleted) {
                return new ResponseEntity<>(Map.of("error", "Course not found."), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(Map.of("message", "Course deleted successfully."), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", "An unexpected error occurred."), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
