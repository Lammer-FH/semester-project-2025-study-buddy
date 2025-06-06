package com.awt.studybuddy.mapper;

import com.awt.studybuddy.dto.course.CourseRequest;
import com.awt.studybuddy.dto.course.CourseResponse;
import com.awt.studybuddy.entity.CourseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CourseMapper {
    CourseResponse toDto(CourseEntity entity);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "assignments", ignore = true)
    CourseEntity toEntity(CourseRequest dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "assignments", ignore = true)
    void updateEntityFromDto(CourseRequest dto, @MappingTarget CourseEntity entity);
}
