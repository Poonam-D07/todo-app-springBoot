package com.poonam.notestodoapp.Exception;

public class TaskAlreadyCompletedException extends RuntimeException {
    public TaskAlreadyCompletedException(String message) {

        super(message);
    }
}
