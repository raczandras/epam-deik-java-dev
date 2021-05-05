package com.epam.training.ticketservice.core.movie;

import com.epam.training.ticketservice.core.movie.exception.MovieAlreadyExistsException;
import com.epam.training.ticketservice.core.movie.exception.MovieNotFoundException;
import com.epam.training.ticketservice.core.movie.model.MovieDto;

import java.util.List;

public interface MovieService {

    List<MovieDto> getMovieList();

    void createMovie(MovieDto movie) throws MovieAlreadyExistsException;

    void deleteMovie(String title) throws MovieNotFoundException;

    void updateMovie(MovieDto movieDto) throws MovieNotFoundException;
}
