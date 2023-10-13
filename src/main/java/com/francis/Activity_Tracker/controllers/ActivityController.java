package com.francis.Activity_Tracker.controllers;

import com.francis.Activity_Tracker.dtos.dtoRequests.ActivityDto;
import com.francis.Activity_Tracker.entities.Activity;
import com.francis.Activity_Tracker.exceptions.ActivityNotFoundException;
import com.francis.Activity_Tracker.services.ActivityService;
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

    @Autowired
    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @GetMapping("/user_page")
    public String showUserPage(Model model) {
        ActivityDto activityDto = new ActivityDto(); // Create an instance of ActivityDto
        model.addAttribute("activityDto", activityDto); // Add it to the model
        model.addAttribute("message", ""); // Initialize an empty message
        return "user_page";
    }

    @PostMapping("/create_activity")
    public String createActivity(@ModelAttribute @Valid ActivityDto activityDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            // Handle validation errors here
            model.addAttribute("message", "Activity creation failed. Please check your input.");
            return "user_page";
        }

        try {
            activityService.createActivity(activityDto);
            model.addAttribute("message", "Activity created successfully.");
        } catch (Exception e) {
            model.addAttribute("message", "Activity creation failed. An error occurred.");
        }

        return "user_page";
    }
    @GetMapping("/view_all_tasks")
    public String viewAllTasks(Model model) {
        List<ActivityDto> tasks = activityService.getAllActivities(); // Assuming you have a method getAllTasks() in your TaskService
        model.addAttribute("tasks", tasks);
        return "activitiesView_page";
    }
    @GetMapping("/view_activity_by_name")
    public String viewActivityByName(@RequestParam("task_name") String name, Model model) {
        System.out.println("Name before trimming: " + name);
        String cleanedName = name.trim();
        ActivityDto activityDto = activityService.getActivityByName(cleanedName);
        System.out.println(activityDto);
        System.out.println("Name after trimming: " + cleanedName);
        model.addAttribute("activity", activityDto);
        return "activityView_page";
    }

}
