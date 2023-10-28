package com.iyke.Activity_Tracker.services;

import com.iyke.Activity_Tracker.dtos.dtoRequests.UserDto;
import com.iyke.Activity_Tracker.entities.User;

import java.util.Optional;

public interface UserService {
    Optional<User> getUserByEmail( String email);
    User RegisterUser(UserDto userDto);
    Boolean loginUser(String email, String password);
    UserDto updateUser(Long id, UserDto userDto);
    void deleteUser(Long id, String email);
}
