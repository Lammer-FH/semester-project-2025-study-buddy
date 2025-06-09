package com.awt.studybuddy.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AssignmentResponseDTO {
    private Long id;
    private String title;
    private String description;
    private LocalDate deadline;
}

