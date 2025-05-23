package com.awt.studybuddy.repository;

import com.awt.studybuddy.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {}

