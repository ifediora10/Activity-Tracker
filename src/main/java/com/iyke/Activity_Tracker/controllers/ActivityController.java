package com.iyke.Activity_Tracker.controllers;

import com.iyke.Activity_Tracker.dtos.dtoRequests.ActivityDto;
import com.iyke.Activity_Tracker.dtos.dtoResponses.ActivityDtoResponse;
import com.iyke.Activity_Tracker.enums.Status;
import com.iyke.Activity_Tracker.exceptions.ActivityNotFoundException;
import com.iyke.Activity_Tracker.repositories.ActivityRepository;
import com.iyke.Activity_Tracker.services.ActivityService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class ActivityController {
    private final ActivityService activityService;
    private final ActivityRepository activityRepository;

    @Autowired
    public ActivityController(ActivityService activityService, ActivityRepository activityRepository) {
        this.activityService = activityService;
        this.activityRepository = activityRepository;
    }

    @GetMapping("/user_page")
    public String showUserPage(Model model) {
        ActivityDto activityDto = new ActivityDto(); // Create an instance of ActivityDto
        model.addAttribute("activityDto", activityDto); // Add it to the model
        model.addAttribute("message", ""); // Initialize an empty message
        return "user_page";
    }

    @PostMapping("/create_activity")
    public String createActivity(@ModelAttribute @Valid ActivityDto activityDto, HttpSession session, BindingResult result, Model model) {
        if (result.hasErrors()) {
            // Handle validation errors here
            model.addAttribute("message", "Activity creation failed. Please check your input.");
            return "user_page";
        }

        try {
            activityService.createActivity(activityDto, Long.parseLong(String.valueOf(session.getAttribute("userId"))));
            model.addAttribute("message", "Activity created successfully.");
        } catch (Exception e) {
            model.addAttribute("message", "Activity creation failed. An error occurred.");
        }

        return "user_page";
    }
    @GetMapping("/view_all_tasks")
    public String viewAllTasks(Model model, HttpSession session) {
        // Retrieve the userId from the session
        Long userId = (Long) session.getAttribute("userId");

        // Fetch tasks associated with the current user
        List<ActivityDtoResponse> tasks = activityService.getAllActivities(userId);

        model.addAttribute("tasks", tasks);
        return "activitiesView_page";
    }

    @GetMapping("/view_activity_by_Id_And_name")
    public String viewActivityByName(@RequestParam("task_name") String name, @RequestParam("id") Long id, Model model) {
        System.out.println("Name before trimming: " + name);
        String cleanedName = name.trim();
        ActivityDtoResponse activityDtoResponse = activityService.getActivityByIdAndName(id,cleanedName);
        System.out.println(activityDtoResponse);
        System.out.println("Name after trimming: " + cleanedName);
        model.addAttribute("activity", activityDtoResponse);
        return "activityView_page";
    }

    @PostMapping("/activityUpdate")
    public String updateActivity(@RequestParam("id") Long id,
                                 @RequestParam("newName") String newName,
                                 @RequestParam("newDescription") String newDescription,
                                 Model model,
                                 HttpSession session) {

            Long userId = Long.parseLong(String.valueOf(session.getAttribute("userId")));

            Boolean isUpdated = activityService.updateActivityQuery(newName, newDescription, Status.IN_PROGRESS, id, userId);

            if (!isUpdated) {
                System.out.println("Update Failed");
            }

        System.out.println("Update Successful");

        return "redirect:/user_page";
    }

    @GetMapping("/activityUpdate_page")
    public String showUpdateActivityPage(@RequestParam("id") Long id, @RequestParam("task_name") String name, Model model) {
        System.out.println("i was here");
        try {
            // Add logic to fetch the activity based on the provided ID and name
            ActivityDtoResponse activity = activityService.getActivityByIdAndName(id, name);
            model.addAttribute("activity", activity);
            System.out.println(activity.getName() + activity.getDescription() + activity.getId());
        } catch (ActivityNotFoundException e) {
            model.addAttribute("message", e.getMessage());
        }

        return "activityUpdate_page";
    }

    @PostMapping("/deleteActivity")
    public String deleteActivity(@RequestParam("id") Long id,
                                 @RequestParam("name1") String name,
                                 Model model) {
        try {
            activityService.deleteActivity(id, name);
            model.addAttribute("message", "Activity with id " + id + " and name " + name + " has been deleted successfully.");
        } catch (ActivityNotFoundException e) {
            model.addAttribute("message", e.getMessage());
        }
        return "redirect:/user_page";
    }
}
