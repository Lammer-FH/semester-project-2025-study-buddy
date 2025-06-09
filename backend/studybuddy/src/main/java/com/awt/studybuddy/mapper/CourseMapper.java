package com.awt.studybuddy.mapper;

import com.awt.studybuddy.dto.CourseRequestDTO;
import com.awt.studybuddy.dto.CourseResponseDTO;
import com.awt.studybuddy.entity.CourseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CourseMapper {
    CourseResponseDTO toDto(CourseEntity entity);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "assignments", ignore = true)
    CourseEntity toEntity(CourseRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "assignments", ignore = true)
    void updateEntityFromDto(CourseRequestDTO dto, @MappingTarget CourseEntity entity);
}
