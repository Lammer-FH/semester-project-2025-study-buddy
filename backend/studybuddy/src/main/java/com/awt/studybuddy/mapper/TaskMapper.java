package com.awt.studybuddy.mapper;

import com.awt.studybuddy.dto.TaskRequestDTO;
import com.awt.studybuddy.dto.TaskResponseDTO;
import com.awt.studybuddy.entity.TaskEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskMapper {
    TaskResponseDTO toDto(TaskEntity entity);

    @Mapping(target = "assignment", ignore = true)
    TaskEntity toEntity(TaskRequestDTO request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "assignment", ignore = true)
    void updateFromDto(TaskRequestDTO request, @MappingTarget TaskEntity entity);
}

