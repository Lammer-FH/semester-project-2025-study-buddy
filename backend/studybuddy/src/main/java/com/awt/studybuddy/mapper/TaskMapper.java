package com.awt.studybuddy.mapper;

import com.awt.studybuddy.dto.task.TaskRequest;
import com.awt.studybuddy.dto.task.TaskResponse;
import com.awt.studybuddy.entity.TaskEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskResponse toDto(TaskEntity entity);
    TaskEntity toEntity(TaskRequest request);
    void updateFromDto(TaskRequest request, @MappingTarget TaskEntity entity);
}

