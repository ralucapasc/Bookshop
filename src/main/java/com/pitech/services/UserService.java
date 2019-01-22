package com.pitech.services;

import com.pitech.dtos.UserRegisterDto;
import com.pitech.models.User;

/**
 * Created by Pasc Raluca on 31.07.2017.
 */
public interface UserService {
    void register(UserRegisterDto userDto);

    String getUsernameAuthenticated();

    User getUserByUsername(String username);
}
