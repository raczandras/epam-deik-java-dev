package com.epam.training.ticketservice.core.movie.impl;

import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.movie.exception.MovieAlreadyExistsException;
import com.epam.training.ticketservice.core.movie.exception.MovieNotFoundException;
import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.movie.persistence.entity.MovieEntity;
import com.epam.training.ticketservice.core.movie.persistence.repository.MovieRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public List<MovieDto> getMovieList() {
        List<MovieDto> movies = movieRepository.findAll()
                .stream().map(this::convertEntityToDto).collect(Collectors.toList());
        if (movies.isEmpty()) {
            System.out.println("There are no movies at the moment");
        }
        return movies;
    }

    @Override
    public void deleteMovie(String title) throws MovieNotFoundException {
        try {
            movieRepository.deleteById(title);
        } catch (EmptyResultDataAccessException e) {
            throw new MovieNotFoundException("Movie: " + title + " doesn't exist.");
        }
    }

    @Override
    public void createMovie(MovieDto movie) throws MovieAlreadyExistsException {
        Optional<MovieEntity> movieToCreate = checkMovie(movie);

        if (movieToCreate.isPresent()) {
            throw new MovieAlreadyExistsException("A movie with title: " + movie.getTitle() + " already exists");
        }
        movieRepository.save(new MovieEntity(movie.getTitle(), movie.getGenre(), movie.getScreeningTime()));
    }

    @Override
    public void updateMovie(MovieDto movie) throws MovieNotFoundException {
        Optional<MovieEntity> movieToUpdate = checkMovie(movie);

        if (movieToUpdate.isEmpty()) {
            throw new MovieNotFoundException("Movie: " + movie.getTitle() + " doesn't exist.");
        }
        movieRepository.save(new MovieEntity(movie.getTitle(), movie.getGenre(), movie.getScreeningTime()));
    }

    public Optional<MovieEntity> checkMovie(MovieDto movie) {
        Objects.requireNonNull(movie, "Movie cannot be null");
        Objects.requireNonNull(movie.getTitle(), "Title cannot be null");
        Objects.requireNonNull(movie.getGenre(), "Genre cannot be null");
        return movieRepository.findById(movie.getTitle());
    }

    private MovieDto convertEntityToDto(MovieEntity movie) {
        return MovieDto.builder()
                .title(movie.getTitle())
                .genre(movie.getGenre())
                .screeningTime(movie.getScreeningTime())
                .build();
    }
}
