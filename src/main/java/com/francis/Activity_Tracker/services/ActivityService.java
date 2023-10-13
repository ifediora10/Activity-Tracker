package com.francis.Activity_Tracker.services;

import com.francis.Activity_Tracker.dtos.dtoRequests.ActivityDto;

import java.util.List;

public interface ActivityService {
    List<ActivityDto> getAllActivities();
    ActivityDto getActivityByName(String name);
    void createActivity(ActivityDto activityDto);
    void updateActivity(Long id,String name,ActivityDto activityDto);
    void deleteActivity(Long id, String name);
}
