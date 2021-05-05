package com.epam.training.ticketservice.core.screening.model;

import com.epam.training.ticketservice.core.movie.persistence.entity.MovieEntity;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Generated;
import lombok.Getter;

@Builder
@EqualsAndHashCode
@Getter
@Generated
public class ScreeningOutDto {

    private final MovieEntity movie;
    private final String room;
    private final String startDate;

    @Override
    public String toString() {
        return movie.getTitle() + " (" + movie.getGenre() + ", " + movie.getScreeningTime()
                + " minutes), screened in room " + room + ", at " + startDate;
    }
}