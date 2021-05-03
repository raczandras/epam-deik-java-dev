package com.epam.training.ticketservice.core.user;

import com.epam.training.ticketservice.core.user.exception.UserNotFoundException;
import com.epam.training.ticketservice.core.user.model.UserDto;

public interface UserService {
    UserDto getUserByUsernameAndPassword(String username, String password) throws UserNotFoundException;
}
