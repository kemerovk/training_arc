package me.project.training_arc.exceptions.custom_exception;

public class UsernameAlreadyExistsException extends RuntimeException{
    public UsernameAlreadyExistsException(String message){
        super(message);
    }


    public UsernameAlreadyExistsException(String message, Throwable cause){
        super(message, cause);
    }

}
