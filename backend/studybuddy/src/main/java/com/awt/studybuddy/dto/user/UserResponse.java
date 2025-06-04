package com.awt.studybuddy.dto.user;

import lombok.Data;

@Data
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private String program;
    private Integer semester;
    private String studentNumber;
}
