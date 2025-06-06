package com.awt.studybuddy.dto.task;

import lombok.Data;

@Data
public class TaskResponse {
    private Long id;
    private String description;
    private boolean done;
}
