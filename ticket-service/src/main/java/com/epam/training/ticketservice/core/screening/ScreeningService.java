package com.epam.training.ticketservice.core.screening;

import com.epam.training.ticketservice.core.movie.exception.MovieNotFoundException;
import com.epam.training.ticketservice.core.room.exception.RoomNotFoundException;
import com.epam.training.ticketservice.core.screening.exception.ScreeningNotFoundException;
import com.epam.training.ticketservice.core.screening.exception.ScreeningsOverlapException;
import com.epam.training.ticketservice.core.screening.model.ScreeningOutDto;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;

import java.text.ParseException;
import java.util.List;

public interface ScreeningService {

    List<ScreeningOutDto> listScreenings();

    void createScreening(ScreeningDto screeningDto) throws MovieNotFoundException, RoomNotFoundException,
            ScreeningsOverlapException, ParseException;

    void deleteScreening(ScreeningDto screeningDto) throws MovieNotFoundException, RoomNotFoundException,
            ScreeningNotFoundException, ParseException;
}
