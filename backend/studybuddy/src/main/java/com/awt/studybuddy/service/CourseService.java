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

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<CourseEntity> getAllForUser(UserEntity user) {
        return courseRepository.findAllByUser(user);
    }


    public CourseEntity findById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));
    }
}
