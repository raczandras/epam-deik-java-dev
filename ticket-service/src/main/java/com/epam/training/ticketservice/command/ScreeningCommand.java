package com.epam.training.ticketservice.command;

import com.epam.training.ticketservice.core.availability.AvailabilityProvider;
import com.epam.training.ticketservice.core.movie.exception.MovieNotFoundException;
import com.epam.training.ticketservice.core.room.exception.RoomNotFoundException;
import com.epam.training.ticketservice.core.screening.ScreeningService;
import com.epam.training.ticketservice.core.screening.exception.ScreeningNotFoundException;
import com.epam.training.ticketservice.core.screening.exception.ScreeningsOverlapException;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import com.epam.training.ticketservice.core.screening.model.ScreeningOutDto;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.text.ParseException;
import java.util.List;

@ShellComponent
public class ScreeningCommand {
    private final ScreeningService screeningService;
    private final AvailabilityProvider availabilityProvider;

    public ScreeningCommand(ScreeningService screeningService, AvailabilityProvider availabilityProvider) {
        this.screeningService = screeningService;
        this.availabilityProvider = availabilityProvider;
    }

    @ShellMethod(value = "List all screenings", key = "list screenings")
    public List<ScreeningOutDto> listScreenings() {
        return screeningService.listScreenings();
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(value = "Create a new screening", key = "create screening")
    public void createScreening(String movie, String room, String startDate) {
        ScreeningDto screeningDto = makeDto(movie, room, startDate);

        try {
            screeningService.createScreening(screeningDto);
            System.out.println("Screening created: " + screeningDto);
        } catch (RoomNotFoundException | MovieNotFoundException
                | ParseException | ScreeningsOverlapException e) {
            System.out.println(e.getMessage());
        }
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(value = "Delete a screening", key = "delete screening")
    public void deleteScreening(String movie, String room, String startDate) {
        ScreeningDto screeningDto = makeDto(movie, room, startDate);

        try {
            screeningService.deleteScreening(screeningDto);
            System.out.println("Screening deleted: " + screeningDto);
        } catch (RoomNotFoundException | MovieNotFoundException
                | ScreeningNotFoundException | ParseException e) {
            System.out.println(e.getMessage());
        }
    }

    private ScreeningDto makeDto(String movie, String room, String startDate) {
        return ScreeningDto.builder()
                .movie(movie)
                .startDate(startDate)
                .room(room)
                .build();
    }

    private Availability isAvailable() {
        return availabilityProvider.isAvailable();
    }
}
