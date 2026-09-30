package com.poonam.notestodoapp.Repository;

import com.poonam.notestodoapp.Entity.Priority;
import com.poonam.notestodoapp.Entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<TaskEntity, Long > {
List<TaskEntity> findByIsCompletedFalse();

List<TaskEntity> findByPriority(Priority priority);
}
