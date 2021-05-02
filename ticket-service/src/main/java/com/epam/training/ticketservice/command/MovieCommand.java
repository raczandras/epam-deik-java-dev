package com.epam.training.ticketservice.command;

import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

@ShellComponent
public class MovieCommand {
    //TODO make commands actually work

    public MovieCommand() {
    }

    @ShellMethod(value = "List All Available Movies", key = "list movies")
    public String[] listMovies() {
        return "Elso filme;Masodik film;Harmadik film".split(";");
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(value = "Delete a movie by title", key = "delete movie")
    public void deleteMovie(String title) {

    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(value = "Create new movie", key = "create movie")
    public String createMovie(String title, String genre, int screeningTime) {
        return "Movie created: movie";
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(value = "Create new movie", key = "update movie")
    public String updateMovie(String title, String genre, int screeningTime) {
        return "Movie updated: movie";
    }

    private Availability isAvailable() {
        return Availability.available();
    }

}
