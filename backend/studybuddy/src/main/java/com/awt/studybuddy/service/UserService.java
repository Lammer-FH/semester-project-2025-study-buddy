package com.awt.studybuddy.service;

import com.awt.studybuddy.entity.UserEntity;
import com.awt.studybuddy.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    private final Long HARDCODED_USER_ID = 1L;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity getHardcodedUser() {
        return userRepository.findById(HARDCODED_USER_ID)
                .orElseThrow(() -> new RuntimeException("Hardcoded user not found"));
    }

    public UserEntity getById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
