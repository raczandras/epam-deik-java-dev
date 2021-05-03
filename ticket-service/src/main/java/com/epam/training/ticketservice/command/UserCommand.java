package com.epam.training.ticketservice.command;

import com.epam.training.ticketservice.core.user.LoginService;
import com.epam.training.ticketservice.core.user.exception.UserNotFoundException;
import com.epam.training.ticketservice.core.user.model.UserDto;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class UserCommand {

    private final LoginService loginService;

    public UserCommand(LoginService loginService) {
        this.loginService = loginService;
    }

    @ShellMethod(value = "Login as admin", key = "sign in privileged")
    public String loginUser(String username, String password) {
        try {
            loginService.login(username, password);
            UserDto user = loginService.getLoggedUser();
            return "Signed in with " + user;
        } catch (UserNotFoundException e) {
            return e.getMessage();
        }
    }

    @ShellMethod(value = "Describe current account", key = "describe account")
    public String describeUser() {
        try {
            UserDto userDto = loginService.getLoggedUser();
            return "Signed in with " + userDto;
        } catch (UserNotFoundException e) {
            return e.getMessage();
        }
    }

    @ShellMethod(value = "Log out from current account", key = "sign out")
    public String logout() {
        return loginService.logout();
    }
}
