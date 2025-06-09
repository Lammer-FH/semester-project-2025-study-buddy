package com.awt.studybuddy.dto;

import lombok.Data;

@Data
public class UserResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String program;
    private Integer semester;
    private String studentNumber;
}
