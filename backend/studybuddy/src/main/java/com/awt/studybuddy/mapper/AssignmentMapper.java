package com.awt.studybuddy.mapper;

import com.awt.studybuddy.dto.assignment.AssignmentResponse;
import com.awt.studybuddy.entity.AssignmentEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AssignmentMapper {
    AssignmentResponse toDto(AssignmentEntity entity);
}
