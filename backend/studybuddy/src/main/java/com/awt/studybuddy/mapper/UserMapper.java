package com.awt.studybuddy.mapper;

import com.awt.studybuddy.dto.user.UserResponse;
import com.awt.studybuddy.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toDto(UserEntity entity);

}
