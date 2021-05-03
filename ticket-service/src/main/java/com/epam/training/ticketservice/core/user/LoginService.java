package com.epam.training.ticketservice.core.user;

import com.epam.training.ticketservice.core.user.exception.UserNotFoundException;
import com.epam.training.ticketservice.core.user.model.UserDto;

public interface LoginService {

    UserDto login(String username, String password) throws UserNotFoundException;

    UserDto getLoggedUser() throws UserNotFoundException;

    String logout();
}
