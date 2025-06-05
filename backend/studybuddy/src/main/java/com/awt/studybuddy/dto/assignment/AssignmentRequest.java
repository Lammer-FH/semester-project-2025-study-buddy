package com.awt.studybuddy.dto.assignment;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AssignmentRequest {
    private String title;
    private String description;
    private LocalDate deadline;
}

