package com.epam.training.ticketservice.command;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class UserCommand {
    //TODO make commands actually work

    public UserCommand() {
    }

    @ShellMethod(value = "Login as admin", key = "sign in privileged")
    public String loginUser(String username, String password) {
        return "logged in (maybe)";
    }

    @ShellMethod(value = "Describe current account", key = "describe account")
    public String describeUser() {
        return "Signed in with: ";
    }

    @ShellMethod(value = "Log out from current account", key = "sign out")
    public String logout() {
        return "logged out";
    }
}
