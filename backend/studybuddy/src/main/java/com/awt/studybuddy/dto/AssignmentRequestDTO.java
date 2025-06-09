package com.awt.studybuddy.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AssignmentRequestDTO {
    private String title;
    private String description;
    private LocalDate deadline;
}

