package com.iyke.Activity_Tracker.services.ServicesImpls;

import com.iyke.Activity_Tracker.dtos.dtoRequests.UserDto;
import com.iyke.Activity_Tracker.entities.User;
import com.iyke.Activity_Tracker.exceptions.UserAlreadyExist;
import com.iyke.Activity_Tracker.exceptions.UserNotFoundException;
import com.iyke.Activity_Tracker.repositories.UserRepository;
import com.iyke.Activity_Tracker.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final HttpSession session;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, HttpSession session) {
        this.userRepository = userRepository;
        this.session = session;
    }

    @Override
    public Optional<User> getUserByEmail( String email) {
        return userRepository.findByEmail( email);
    }


    @Override
    public User RegisterUser(UserDto userDto) {
        User existingUser = userRepository.findByEmail(userDto.getEmail()).orElse(null);
        if (existingUser == null) {
            User newUser = new User();
            newUser.setFirst_name(userDto.getFirst_name());
            newUser.setLast_name(userDto.getLast_name());
            newUser.setEmail(userDto.getEmail());
            newUser.setPhone_number(userDto.getPhone_number());
            newUser.setPassword(userDto.getPassword());
            userRepository.save(newUser);
            return newUser;
        } else {
            throw new UserAlreadyExist("User already exists");
        }
    }
    public Boolean loginUser(String email, String password){
        User existingUser = userRepository.findByEmail(email).orElse(null);
        if (existingUser == null){
            return  false;
        }else {
            session.setAttribute("userId",existingUser.getEmail());
            return true;
        }
    }


    @Override
    public UserDto updateUser(Long id, UserDto userDto) {
        User existingUser = userRepository.findById(id).orElse(null);
        if(existingUser != null){
            existingUser.setFirst_name(userDto.getFirst_name());
            existingUser.setLast_name(userDto.getLast_name());
            existingUser.setEmail(userDto.getEmail());
            existingUser.setPhone_number(userDto.getPhone_number());
            existingUser.setPassword(userDto.getPassword());
            userRepository.saveAndFlush(existingUser);
            BeanUtils.copyProperties(existingUser,userDto);
            return userDto;
        }else {
            throw new UserNotFoundException("User not found");
        }

    }

    @Override
    public void deleteUser(Long id, String email) {
        userRepository.deleteByIdAndEmail(id,email);

    }
}
