package com.epam.training.ticketservice.core.availability;

import com.epam.training.ticketservice.core.user.LoginService;
import com.epam.training.ticketservice.core.user.exception.UserNotFoundException;
import com.epam.training.ticketservice.core.user.model.UserDto;
import org.springframework.shell.Availability;
import org.springframework.stereotype.Service;

@Service
public class AvailabilityProviderImpl implements AvailabilityProvider {

    private final LoginService loginService;

    public AvailabilityProviderImpl(LoginService loginService, LoginService loginService1) {

        this.loginService = loginService1;
    }

    @Override
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
