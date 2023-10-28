package com.iyke.Activity_Tracker.exceptions;

public class ActivityNotFoundException extends RuntimeException {
    public ActivityNotFoundException(String message){
        super(message);
    }
}
