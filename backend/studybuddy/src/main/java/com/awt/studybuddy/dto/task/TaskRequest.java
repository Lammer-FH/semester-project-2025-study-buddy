package com.awt.studybuddy.dto.task;

import lombok.Data;

@Data
public class TaskRequest {
    private String description;
    private boolean done;
}

