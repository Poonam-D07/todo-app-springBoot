package com.poonam.notestodoapp.Service;

import com.poonam.notestodoapp.DTO.TaskReqDTO;
import com.poonam.notestodoapp.DTO.TaskResDTO;
import com.poonam.notestodoapp.Entity.Priority;
import com.poonam.notestodoapp.Entity.TaskEntity;
import com.poonam.notestodoapp.Exception.TaskAlreadyCompletedException;
import com.poonam.notestodoapp.Exception.TaskNotFoundException;
import com.poonam.notestodoapp.Repository.TaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class TaskServiceImpl implements TaskService{

    private static final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

    private  final TaskRepository taskRepository;

    //Constructor injection TaskRepository
    public TaskServiceImpl(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }
    //======================
    // Create New Task
    //======================
    @Override
    public TaskResDTO createTask(TaskReqDTO taskReqDTO) {
        //Logger use here
        logger.info("Creating new task with title : {} ", taskReqDTO.getTitle());
        //1. TaskReqDTO se data liya
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setTitle(taskReqDTO.getTitle());
        taskEntity.setDescription(taskReqDTO.getDescription());
        taskEntity.setPriority(taskReqDTO.getPriority());

        //Save from repository
        TaskEntity saveTask = taskRepository.save(taskEntity);
        logger.info("Task created successfullly with id : {}" , saveTask.getTaskId());

        /*
        return TaskResDTO.builder()

                .taskId(saveTask.getTaskId())
                .title(saveTask.getTitle())
                .description(saveTask.getDescription())
                .priority(saveTask.getPriority())
                .isCompleted(saveTask.getIsCompleted())
                .build();
*/
        return mapToDTO(saveTask);   // 6 lines ki jagah 1 line
    }

    //===========================
    //   GET all Tasks
    //=========================
    @Override
    public List<TaskResDTO> getAllTasks() {
        logger.info("Fetching all tasks");

        List<TaskEntity> taskList = taskRepository.findAll();

        List<TaskResDTO> dtoList = new ArrayList<>();

        for(TaskEntity task : taskList){
           /* TaskResDTO taskResDTO = TaskResDTO.builder()
                    .taskId(task.getTaskId())
                    .title(task.getTitle())
                    .description(task.getDescription())
                    .isCompleted(task.getIsCompleted())
                    .priority(task.getPriority())
                    .build();
            dtoList.add(taskResDTO);
      */
            dtoList.add(mapToDTO(task));
        }
        return dtoList;
    }

    //===========================
    //   GET  pending Tasks
    //=========================
    @Override
    public List<TaskResDTO> getPendingTasks() {
        logger.info("Fetching pending tasks");
        // 1. Repository se list lo
        List<TaskEntity> pendingList = taskRepository.findByIsCompletedFalse();
// 2. Khaali dtoList
        List<TaskResDTO> dtoList = new ArrayList<>();

        // 3. Loop chalao, mapToDTO() use karo
        for(TaskEntity task : pendingList){
                dtoList.add(mapToDTO(task));
        }
        // 4. Return karo
        return dtoList;
    }

    //=====================
    // Get One Tasks
    //=====================
    @Override
    public TaskResDTO getOneTask(Long taskId) {
        logger.info("Fetching task with id : {}", taskId);
        //1. findById() se dhoondo
        Optional<TaskEntity>  boxOptional = taskRepository.findById(taskId);

        //2. isEmpty() check karo → true ho to null return
        if (!boxOptional.isPresent()) {
            throw new TaskNotFoundException("Task not found with id "+ taskId +" !");
        }
//3. Mile to .get() se entity nikalo
        TaskEntity taskEntity = boxOptional.get();

        //4. mapToDTO() se DTO banao
        return mapToDTO(taskEntity);
    }


    //===============================================
    // Partical Updation Tasks  (Mark Complete)
    //===============================================
    @Override
    public TaskResDTO markTaskComplete(Long taskId) {
        logger.info("Making task {} as complete ", taskId);
          //1. taskId se task dhoondo (findById)
        Optional<TaskEntity> boxOptional = taskRepository.findById(taskId);

        //2. Agar na mile → null return
        if (!boxOptional.isPresent()) {
            throw new TaskNotFoundException("Task Not found with id "+taskId);
        }
        //3. Existing entity nikalo, sirf isCompleted field change karo
        TaskEntity taskEntity = boxOptional.get();

        // NAYA CHECK — yaha socho kya likhna hai
        // Agar taskEntity.getIsCompleted() already true hai, to exception throw karo


        if(taskEntity.getIsCompleted()){
throw new TaskAlreadyCompletedException("Task already completed with id " + taskId);
        }
        taskEntity.setIsCompleted(true);

        //4. save() karo (Hibernate ye UPDATE query samjhega, kyunki taskId already set hai)
        TaskEntity saveTask= taskRepository.save(taskEntity) ;

        //5. mapToDTO() se DTO banao, return karo
        return mapToDTO(saveTask);
    }


    //=====================
    // Update Tasks
    //=====================
    @Override
    public TaskResDTO updateTask(Long taskId, TaskReqDTO taskReqDTO) {

        logger.info("Updating task with id : {} ", taskId);
//1. taskId se task dhoondo (findById)
        Optional<TaskEntity> boxOptional = taskRepository.findById(taskId);
//2. Agar na mile → null return
        if (!boxOptional.isPresent()) {
         throw new TaskNotFoundException("Task Not found with id "+taskId);
        }
        //3. Existing entity nikalo, naya data set karo
        TaskEntity taskEntity = boxOptional.get();
        taskEntity.setTitle(taskReqDTO.getTitle());
        taskEntity.setDescription(taskReqDTO.getDescription());
        taskEntity.setPriority(taskReqDTO.getPriority());
//4. save() karo
        TaskEntity saveTask = taskRepository.save(taskEntity);

        logger.info("Task {} updated successfully ", taskId);

//5. mapToDTO() se DTO banao, return karo
        return mapToDTO(saveTask);
    }

    //=====================
    // Delete One Task
    //=====================
    @Override
    public boolean deleteOneTask(Long taskId) {
        logger.warn("Deleting task with id {} ", taskId);
        //1. findById() se task dhoondo
        Optional<TaskEntity> boxOptional = taskRepository.findById(taskId);

        //2. isEmpty() → false return
        if (!boxOptional.isPresent()) {
            throw new TaskNotFoundException("Task Not found with id "+ taskId);
        }
        //3. Mile to → bookRepository.delete(entity) call karo
        TaskEntity taskEntity = boxOptional.get();
        taskRepository.delete(taskEntity);
        //4. true return karo
        return true;
    }

    //=====================
    // Delete All  Tasks
    //=====================
    @Override
    public boolean deleteAllTasks() {
    logger.warn("Deleting All tasks - this action is irreversible");
    taskRepository.deleteAll();
        return true;
    }

    //=====================
    // Search Task By Priority
    //=====================
    @Override
    public List<TaskResDTO> filterByPriority(Priority priority) {

        logger.info("Fetching task with Priority : {}", priority);
        // 1. Repository se List lo (Optional NAHI, kyunki multiple results ho sakte hain)
        List<TaskEntity>  taskList = taskRepository.findByPriority(priority);

        // 2. Khaali DTO list banao
        List<TaskResDTO> dtoList = new ArrayList<>();

        // 3. Loop chalao, mapToDTO() use karo
        for (TaskEntity task : taskList) {
            dtoList.add(mapToDTO(task));
        }

        // 4. Return karo (koi exception nahi, khaali list bhi valid hai)
        return dtoList;

    }

    @Override
    public Page<TaskResDTO> getAllTasksPaginated(int page, int size) {

        logger.info("Fetching tasks - page: {}, size: {}", page, size);

        // 1. Pageable object banao
        Pageable pageable = PageRequest.of(page, size);

        // 2. Repository se paginated data lo
        Page<TaskEntity> taskPage = taskRepository.findAll(pageable);

        // 3. Page<TaskEntity> ko Page<TaskResDTO> me convert karo
        Page<TaskResDTO> dtoPage = taskPage.map(this::mapToDTO);

        return dtoPage;
    }


    //Solution: Ek Baar Likho, Baar-Baar Use Karo
    private TaskResDTO mapToDTO(TaskEntity task){
        return TaskResDTO.builder()
                .taskId(task.getTaskId())
                .title(task.getTitle())
                .description(task.getDescription())
                .isCompleted(task.getIsCompleted())
                .priority(task.getPriority())
                .build();
    }
}


//follow this

//1. TaskReqDTO se data lo
//2. Naya TaskEntity object banao, usme data daalo
//3. Repository se save() karo
//4. Saved entity se TaskResDTO banao, return karo