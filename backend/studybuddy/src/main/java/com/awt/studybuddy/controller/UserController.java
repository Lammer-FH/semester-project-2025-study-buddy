package com.awt.studybuddy.controller;

import com.awt.studybuddy.dto.UserResponseDTO;
import com.awt.studybuddy.entity.UserEntity;
import com.awt.studybuddy.mapper.UserMapper;
import com.awt.studybuddy.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        UserEntity user = userService.getById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        UserResponseDTO response = userMapper.toDto(user);
        return ResponseEntity.ok(response);
    }
}