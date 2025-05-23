package com.awt.studybuddy.mapper;

import com.awt.studybuddy.dto.course.CourseResponse;
import com.awt.studybuddy.entity.CourseEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    CourseResponse toDto(CourseEntity entity);
}
