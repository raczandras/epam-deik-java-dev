package com.epam.training.ticketservice.availability;

import com.epam.training.ticketservice.core.user.LoginService;
import com.epam.training.ticketservice.core.user.exception.UserNotFoundException;
import com.epam.training.ticketservice.core.user.model.UserDto;
import org.springframework.shell.Availability;
import org.springframework.stereotype.Component;

@Component
public class AvailabilityProvider {

    private final LoginService loginService;

    public AvailabilityProvider(LoginService loginService, LoginService loginService1) {

        this.loginService = loginService1;
    }

    public Availability isAvailable() {
        try {
            UserDto userDto = loginService.getLoggedUser();
            if (userDto.isAdmin()) {
                return Availability.available();
            } else {
                return Availability.unavailable("You are not an admin user");
            }
        } catch (UserNotFoundException e) {
            return Availability.unavailable(e.getMessage());
        }
    }
}
