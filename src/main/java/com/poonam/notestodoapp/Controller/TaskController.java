package com.poonam.notestodoapp.Controller;

import com.poonam.notestodoapp.DTO.TaskReqDTO;
import com.poonam.notestodoapp.DTO.TaskResDTO;
import com.poonam.notestodoapp.Entity.Priority;
import com.poonam.notestodoapp.Service.TaskService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }
//================================
// CREATE NEW TASK
//================================
    @PostMapping("/create")
    public ResponseEntity<TaskResDTO> createTask(@Valid @RequestBody TaskReqDTO taskReqDTO){
        // 1. service call karo
        TaskResDTO resDTO = taskService.createTask(taskReqDTO);

        // 2. ResponseEntity.status(HttpStatus.CREATED).body(...) return karo
        return ResponseEntity.status(HttpStatus.CREATED).body(resDTO);
    }

    //================================
    // GET A SPECIFIC TASK
    //================================
    @GetMapping("/{taskId}")
    public ResponseEntity<TaskResDTO> readSpecificTask(@PathVariable Long taskId){

        TaskResDTO taskResDTO = taskService.getOneTask(taskId);

        return ResponseEntity.ok(taskResDTO);
    }

    //================================
    // GET ALL TASKs
   //================================

    @GetMapping("/getAll")
    public ResponseEntity<List<TaskResDTO>> readAllTasks(){
        //1. Service ko call karo, result ek variable me store karo
        List<TaskResDTO> allTasks = taskService.getAllTasks();

        //2. Us variable ko ResponseEntity.ok() ke andar wrap karke return karo
        return ResponseEntity.ok(allTasks);
    }

    //================================
   // GET PENDING TASKS
   //================================
    @GetMapping("/pending")
    public ResponseEntity<List<TaskResDTO>> getPendingTasks(){
        List<TaskResDTO> pendingTasks = taskService.getPendingTasks();
      return ResponseEntity.ok(pendingTasks);
    }


    //================================
    // MARK TASK COMPLETE (Partial Update — koi body nahi chahiye!)
   //================================
    @PutMapping("/markComplete/{taskId}")
    public ResponseEntity<?> markCompleted(@PathVariable Long taskId){
        TaskResDTO taskResDTO = taskService.markTaskComplete(taskId);

        return ResponseEntity.ok(taskResDTO);
    }

    //================================
    // UPDATE TASK (Full update)
   //================================
    @PutMapping("/update/{taskId}")
    public ResponseEntity<?> updateTask(@PathVariable Long taskId, @Valid @RequestBody TaskReqDTO taskReqDTO) {
       TaskResDTO updateTask= taskService.updateTask(taskId,taskReqDTO);

       return ResponseEntity.ok(updateTask);
    }

    //================================
    // DELETE ONE TASKS
    //================================
    @DeleteMapping("/deleteOne/{taskId}")
    public ResponseEntity<?> deleteOneTasks(@PathVariable Long taskId) {
         taskService.deleteOneTask(taskId);

        return ResponseEntity.ok("Task deleted Successfully ");
    }

    //================================
    // DELETE ALL TASKS
   //================================
    @DeleteMapping("/deleteAll")
    public ResponseEntity<?> deleteAllTasks() {
        taskService.deleteAllTasks();
        return ResponseEntity.noContent().build();
    }

    //================================
    // SEARCH TASKS BY PRIORITY
    //================================

    @GetMapping("/filterByPriority")
    public ResponseEntity <List<TaskResDTO>> filterByPriority(@RequestParam Priority priority){

        List<TaskResDTO> taskList = taskService.filterByPriority(priority);

        return ResponseEntity.ok(taskList);
    }

    //================================
    // PAGINATION
    //================================

    @GetMapping("/getAllPaginated")
    public ResponseEntity<Page<TaskResDTO>> getAllTasksPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size) {

        Page<TaskResDTO> result = taskService.getAllTasksPaginated(page, size);

        return ResponseEntity.ok(result);
    }
}
