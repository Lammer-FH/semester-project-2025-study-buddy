package com.awt.studybuddy.mapper;

import com.awt.studybuddy.dto.UserResponseDTO;
import com.awt.studybuddy.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponseDTO toDto(UserEntity entity);
}



