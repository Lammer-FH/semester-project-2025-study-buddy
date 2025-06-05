package com.awt.studybuddy.mapper;

import com.awt.studybuddy.dto.assignment.AssignmentRequest;
import com.awt.studybuddy.dto.assignment.AssignmentResponse;
import com.awt.studybuddy.entity.AssignmentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AssignmentMapper {
    AssignmentResponse toDto(AssignmentEntity entity);
    AssignmentEntity toEntity(AssignmentRequest dto);
    void updateEntityFromDto(AssignmentRequest dto, @MappingTarget AssignmentEntity entity);
}
