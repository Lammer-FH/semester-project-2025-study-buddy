package com.awt.studybuddy.mapper;

import com.awt.studybuddy.dto.course.CourseRequest;
import com.awt.studybuddy.dto.course.CourseResponse;
import com.awt.studybuddy.entity.CourseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    CourseResponse toDto(CourseEntity entity);
    CourseEntity toEntity(CourseRequest dto);
    void updateEntityFromDto(CourseRequest dto, @MappingTarget CourseEntity entity);
}
