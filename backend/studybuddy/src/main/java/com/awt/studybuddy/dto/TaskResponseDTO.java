package com.awt.studybuddy.dto;

import lombok.Data;

@Data
public class TaskResponseDTO {
    private Long id;
    private String description;
    private boolean done;
}
