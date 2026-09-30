package com.poonam.notestodoapp.DTO;

import com.poonam.notestodoapp.Entity.Priority;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TaskResDTO {
    private Long taskId;
    private String title;
    private String description;
    private boolean isCompleted;
    private Priority priority;
}
