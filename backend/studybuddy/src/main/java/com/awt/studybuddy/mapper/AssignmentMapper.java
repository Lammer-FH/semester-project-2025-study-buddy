package com.awt.studybuddy.mapper;

import com.awt.studybuddy.dto.assignment.AssignmentRequest;
import com.awt.studybuddy.dto.assignment.AssignmentResponse;
import com.awt.studybuddy.entity.AssignmentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AssignmentMapper {

    @Mapping(target = "course", ignore = true) // We ignore course in DTO to entity mapping
    @Mapping(target = "tasks", ignore = true)
    AssignmentEntity toEntity(AssignmentRequest dto);

    AssignmentResponse toDto(AssignmentEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "course", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    void updateEntityFromDto(AssignmentRequest dto, @MappingTarget AssignmentEntity entity);
}
