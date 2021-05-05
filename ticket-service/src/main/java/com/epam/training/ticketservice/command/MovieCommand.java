package com.epam.training.ticketservice.command;

import com.epam.training.ticketservice.availability.AvailabilityProvider;
import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.movie.exception.MovieAlreadyExistsException;
import com.epam.training.ticketservice.core.movie.exception.MovieNotFoundException;
import com.epam.training.ticketservice.core.movie.model.MovieDto;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.util.List;

@ShellComponent
public class MovieCommand {

    private final MovieService movieService;
    private final AvailabilityProvider availabilityProvider;

    public MovieCommand(MovieService movieService, AvailabilityProvider availabilityProvider) {
        this.movieService = movieService;
        this.availabilityProvider = availabilityProvider;
    }

    @ShellMethod(value = "List all Movies", key = "list movies")
    public List<MovieDto> listMovies() {
        return movieService.getMovieList();
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(value = "Delete a movie by title", key = "delete movie")
    public void deleteMovie(String title) {
        try {
            movieService.deleteMovie(title);
        } catch (MovieNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(value = "Create a new movie", key = "create movie")
    public String createMovie(String title, String genre, int screeningTime) {
        MovieDto movieDto = MovieDto.builder()
                .title(title)
                .genre(genre)
                .screeningTime(screeningTime)
                .build();
        try {
            movieService.createMovie(movieDto);
            return "Movie created: " + movieDto;
        } catch (MovieAlreadyExistsException e) {
            return e.getMessage();
        }
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(value = "Update a movie", key = "update movie")
    public String updateMovie(String title, String genre, int screeningTime) {
        MovieDto movieDto = MovieDto.builder()
                .title(title)
                .genre(genre)
                .screeningTime(screeningTime)
                .build();
        try {
            movieService.updateMovie(movieDto);
            return "Movie updated: " + movieDto;
        } catch (MovieNotFoundException e) {
            return e.getMessage();
        }
    }

    private Availability isAvailable() {
        return availabilityProvider.isAvailable();
    }
}
