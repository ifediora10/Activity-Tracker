package com.francis.Activity_Tracker.exceptions;

public class ActivityAlreadyExit extends RuntimeException{
    public ActivityAlreadyExit(String message){
        super(message);
    }
}
