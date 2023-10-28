package com.iyke.Activity_Tracker.exceptions;

public class UserAlreadyExist extends RuntimeException{
    public UserAlreadyExist(String message){
        super(message);
    }
}
