package com.epam.training.ticketservice.command;

import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

@ShellComponent
public class ScreeningCommand {
    //TODO make commands actually work

    public ScreeningCommand() {
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(value = "Create a new screening", key = "create screening")
    public void createScreening(String movieTitle, String roomName, String startDate) {
    }

    @ShellMethod(value = "List all screenings", key = "list screenings")
    public String[] listScreenings() {
        return "Elso filme;Masodik film;Harmadik film".split(";");
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(value = "Delete a screening", key = "delete screening")
    public void deleteScreening(String movieTitle, String roomName, String startDate) {
        System.out.println("Screening deleted: ");

    }

    private Availability isAvailable() {
        return Availability.available();
    }
}
