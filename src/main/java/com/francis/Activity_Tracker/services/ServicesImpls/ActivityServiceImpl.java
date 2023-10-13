package com.francis.Activity_Tracker.services.ServicesImpls;

import com.francis.Activity_Tracker.dtos.dtoRequests.ActivityDto;
import com.francis.Activity_Tracker.exceptions.ActivityAlreadyExit;
import com.francis.Activity_Tracker.exceptions.ActivityNotFoundException;
import com.francis.Activity_Tracker.entities.Activity;
import com.francis.Activity_Tracker.repositories.ActivityRepository;
import com.francis.Activity_Tracker.services.ActivityService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hibernate.sql.ast.SqlTreeCreationLogger.LOGGER;

@Service
@Transactional
public class ActivityServiceImpl implements ActivityService {
    private final ActivityRepository activityRepository;
     @Autowired
    public ActivityServiceImpl(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @Override
    public List<ActivityDto> getAllActivities() {
        List<Activity> activities = activityRepository.findAll();
        return activities.stream()
                .map(activity -> new ActivityDto(
                        activity.getName(),
                        activity.getDescription(),
                        activity.getStatus(),
                        activity.getCreatedDate(),
                        activity.getDoneDate(),
                        activity.getUpDateDate(),
                        activity.getUser()
                ))
                .collect(Collectors.toList());
    }


    @Override
    public ActivityDto getActivityByName(String name) {
        LOGGER.info("Searching for activity with name: " + name);
        Optional<Activity> activityOptional = activityRepository.findActivityByName(name);
        if (activityOptional.isPresent()) {
            LOGGER.info("Activity found: " + name);
            Activity activity = activityOptional.get();
            return new ActivityDto(
                    activity.getName(),
                    activity.getDescription(),
                    activity.getStatus(),
                    activity.getCreatedDate(),
                    activity.getDoneDate(),
                    activity.getUpDateDate(),
                    activity.getUser()
            );
        } else {
            LOGGER.warn("Activity not found with name: " + name);
            throw new ActivityNotFoundException("Activity not found with name: " + name);
        }
    }

    @Override
    public void createActivity(ActivityDto activityDto) {
        String activityName = activityDto.getName();

        // Check if an activity with the same name already exists
        if (activityRepository.existsByName(activityName)) {
            throw new ActivityAlreadyExit("Activity with the name '" + activityName + "' already exists.");
        }

        Activity activity = new Activity();
        activity.setName(activityDto.getName());
        activity.setDescription(activityDto.getDescription());
        activity.setStatus(activityDto.getStatus());
        activity.setUser(activityDto.getUser());

         activityRepository.save(activity);

    }


    @Override
    public void updateActivity(Long id, String name, ActivityDto activityDto) {
         Optional<Activity> activityOptional = activityRepository.findActivityByName(name);
         if(activityOptional.isPresent()){
             Activity activity = activityOptional.get();
             activity.setName(activityDto.getName());
             activity.setDescription(activityDto.getDescription());
             activity.setDoneDate(activityDto.getDoneDate());
             activity.setCreatedDate(activityDto.getCreatedDate());
             activity.setUpDateDate(activityDto.getUpDateDate());
             activityRepository.save(activity);
         }else {
             throw new ActivityNotFoundException("Activity not find with " +"and "+ name);
         }

    }

    @Override
    public void deleteActivity(Long id, String name) {
         Optional<Activity> activityOptional = activityRepository.deleteByIdAndName(id, name);
         if(activityOptional.isPresent()){
             activityRepository.deleteByIdAndName(id, name);
         }else {
             throw new ActivityNotFoundException("Activity not find with "+ id +"and "+ name);
         }

    }

}
