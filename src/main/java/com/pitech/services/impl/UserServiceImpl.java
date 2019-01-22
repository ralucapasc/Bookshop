package com.pitech.services.impl;

import com.pitech.dtos.UserRegisterDto;
import com.pitech.enums.Username;
import com.pitech.mappers.UserMapper;
import com.pitech.models.User;
import com.pitech.repositories.UserRepository;
import com.pitech.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * Created by Pasc Raluca on 31.07.2017.
 */
@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private final UserMapper userMapper = UserMapper.INSTANCE;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void register(UserRegisterDto userDto) {
        User user = userMapper.toEntityWithPassword(userDto);

        userRepository.save(user);
    }

    @Override
    public String getUsernameAuthenticated() {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
           return  ((UserDetails) principal).getUsername();
        }

        return Username.GUEST.getKey();
    }

    @Override
    public User getUserByUsername(String username) {

        return userRepository.findByUsername(username).get();
    }
}
