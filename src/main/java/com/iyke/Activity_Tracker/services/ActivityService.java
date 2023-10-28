package com.iyke.Activity_Tracker.services;

import com.iyke.Activity_Tracker.dtos.dtoRequests.ActivityDto;
import com.iyke.Activity_Tracker.dtos.dtoResponses.ActivityDtoResponse;
import com.iyke.Activity_Tracker.entities.Activity;
import com.iyke.Activity_Tracker.enums.Status;

import java.util.List;

public interface ActivityService {
    List<ActivityDtoResponse> getAllActivities(Long userId);
    ActivityDtoResponse getActivityByIdAndName(Long id,String name);
    void createActivity(ActivityDto activityDto, Long id);
    void updateActivity(Long id, String name, Activity activity, Long userId);
    void deleteActivity(Long id, String name);
    List<ActivityDtoResponse> getAllPending(Long id,String name);
    List<ActivityDtoResponse> getAllInProgress(Long id,String name);
    List<ActivityDtoResponse> getAllDone(Long id,String name);
    Boolean updateActivityQuery(
            String newName,
            String newDesc,
            Status newStatus,
            Long taskId,
            Long userId
    );
}
