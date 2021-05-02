package com.epam.training.ticketservice.core.movie.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Generated;
import lombok.Getter;

@Builder
@EqualsAndHashCode
@Getter
@Generated
public class MovieDto {

    private final String title;
    private final String genre;
    private final int screeningTime;

    @Override
    public String toString() {
        return String.format(title + " ("+genre+", "+screeningTime+" minutes)");
    }
}
