package com.awt.studybuddy.dto.assignment;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AssignmentResponse {
    private Long id;
    private String title;
    private String description;
    private LocalDate deadline;
}

