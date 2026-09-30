package com.poonam.notestodoapp.Service;

import com.poonam.notestodoapp.DTO.TaskReqDTO;
import com.poonam.notestodoapp.DTO.TaskResDTO;
import com.poonam.notestodoapp.Entity.Priority;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TaskService {
    //Create new Task
    TaskResDTO createTask(TaskReqDTO taskReqDTO);

    //Get all task
    List<TaskResDTO> getAllTasks();

    //Get pending task(Incomplete tasks)
    List<TaskResDTO> getPendingTasks();

    //Get one specific task
    TaskResDTO getOneTask(Long taskId);

    //Mark task as complete (Partial Update — no body needed)
    TaskResDTO markTaskComplete(Long taskId);

    //Update task by ID (Partical update)
    TaskResDTO updateTask(Long taskId, TaskReqDTO taskReqDTO);

    //Delete one task by id (hard delete)
    boolean deleteOneTask(Long taskId);

    //Delete all tasks
    // kitne tasks delete hue, wo number return kare
    boolean deleteAllTasks();

    // Search By Priority
    List<TaskResDTO> filterByPriority(Priority priority);

    //pagination concept
    Page<TaskResDTO> getAllTasksPaginated(int page, int size);

}
