package com.prince.ToolEntrySystem.exceptions;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(){
    }

    public ResourceNotFoundException(String message){
        super(message);
    }
}
