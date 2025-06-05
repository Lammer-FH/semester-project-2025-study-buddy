package com.awt.studybuddy.service;

import com.awt.studybuddy.entity.CourseEntity;
import com.awt.studybuddy.entity.UserEntity;
import com.awt.studybuddy.repository.CourseRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final UserService userService;

    public CourseService(CourseRepository courseRepository, UserService userService) {
        this.courseRepository = courseRepository;
        this.userService = userService;
    }

    public List<CourseEntity> getAllForUser(UserEntity user) {
        return courseRepository.findAllByUser(user);
    }


    public CourseEntity findById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));
    }

    public CourseEntity createCourse(CourseEntity course) {
        UserEntity user = userService.getHardcodedUser();
        course.setUser(user);
        return courseRepository.save(course);
    }

    public CourseEntity updateCourse(Long id, CourseEntity updated) {
        CourseEntity existing = courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));
        existing.setTitle(updated.getTitle());
        existing.setDescription(updated.getDescription());
        return courseRepository.save(existing);
    }

    public boolean deleteCourse(Long id) {
        if (!courseRepository.existsById(id)) {
            return false;
        }
        courseRepository.deleteById(id);
        return true;
    }
}
