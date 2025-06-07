package com.awt.studybuddy.mapper;

import com.awt.studybuddy.dto.task.TaskRequest;
import com.awt.studybuddy.dto.task.TaskResponse;
import com.awt.studybuddy.entity.TaskEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskMapper {
    TaskResponse toDto(TaskEntity entity);

    @Mapping(target = "assignment", ignore = true)
    TaskEntity toEntity(TaskRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "assignment", ignore = true)
    void updateFromDto(TaskRequest request, @MappingTarget TaskEntity entity);
}

