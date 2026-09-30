package com.poonam.notestodoapp.Exception;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(String message) {
        super(message);
    }

    public TaskNotFoundException(){
        super("Task not found"); //default message
    }
}
