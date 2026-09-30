package com.poonam.notestodoapp.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "tasks")
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long taskId;

    @NotBlank(message = "Require title")
    @Column(nullable = false)
    @Size(max = 100)
 private String title;

    @Size(max = 1000)
 private String description;

 private Boolean isCompleted = false;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Priority priority;


//    public boolean getIsCompleted() {
//    }
}
