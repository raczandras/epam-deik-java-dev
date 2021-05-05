package com.epam.training.ticketservice.core.movie;


import com.epam.training.ticketservice.core.movie.exception.MovieAlreadyExistsException;
import com.epam.training.ticketservice.core.movie.exception.MovieNotFoundException;
import com.epam.training.ticketservice.core.movie.impl.MovieServiceImpl;
import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.movie.persistence.entity.MovieEntity;
import com.epam.training.ticketservice.core.movie.persistence.repository.MovieRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;
import java.util.Optional;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;

public class MovieServiceImplTest {

    private static final MovieEntity movieEntity = new MovieEntity("Star Wars", "sci-fi", 180);
    private static final MovieEntity movieEntity1 = new MovieEntity("Random Movie", "action", 99);
    private static final MovieDto movieDto = MovieDto.builder().title("Star Wars").genre("sci-fi").screeningTime(180).build();
    private static final MovieDto movieDto1 = MovieDto.builder().title("Random Movie").genre("action").screeningTime(99).build();

    private MovieServiceImpl movieService;
    private MovieRepository movieRepository;

    @BeforeEach
    public void init() {
        movieRepository = Mockito.mock(MovieRepository.class);
        movieService = new MovieServiceImpl(movieRepository);
    }

    @Test
    public void testGetAllMoviesShouldReturnMoviesSuccessfully() {
        // Given
        Mockito.when(movieRepository.findAll()).thenReturn(List.of(movieEntity, movieEntity1));
        List<MovieDto> expected = List.of(movieDto, movieDto1);

        // When
        List<MovieDto> actual = movieService.getMovieList();

        // Then
        Assertions.assertEquals(expected, actual);
        Mockito.verify(movieRepository).findAll();
        Mockito.verifyNoMoreInteractions(movieRepository);
    }

    @Test
    public void testCreatingMovieShouldCreateTheMovieSuccessfully() throws MovieAlreadyExistsException {
        // Given
        Mockito.when(movieRepository.save(movieEntity)).thenReturn(movieEntity);
        Mockito.when(movieRepository.findById("Star Wars")).thenReturn(Optional.empty());

        // When
        movieService.createMovie(movieDto);

        // Then
        Mockito.verify(movieRepository, times(1)).save(movieEntity);
    }

    @Test
    public void testCreatingMovieShouldThrowNullPointerException() {
        // Given

        // When
        Assertions.assertThrows(NullPointerException.class, () -> movieService.createMovie(null));

        //Then
        Mockito.verifyNoMoreInteractions(movieRepository);
    }

    @Test
    public void testCreatingMovieWithNullTitleShouldThrowNullPointerException() {
        // Given
        MovieDto movieDto = MovieDto.builder()
                .title(null)
                .genre("horror")
                .screeningTime(110)
                .build();

        // When
        Assertions.assertThrows(NullPointerException.class, () -> movieService.createMovie(movieDto));

        //Then
        Mockito.verifyNoMoreInteractions(movieRepository);
    }

    @Test
    public void testCreatingMovieWithNullGenreShouldThrowNullPointerException() {
        // Given
        MovieDto movieDto = MovieDto.builder()
                .title("Nonexistent Movie")
                .genre(null)
                .screeningTime(110)
                .build();

        // When
        Assertions.assertThrows(NullPointerException.class, () -> movieService.createMovie(movieDto));

        //Then
        Mockito.verifyNoMoreInteractions(movieRepository);
    }

    @Test
    public void testCreatingMovieThatAlreadyExistsShouldThrowMovieAlreadyExistsException() {
        // Given
        Mockito.when(movieRepository.findById(anyString())).thenReturn(java.util.Optional.of(movieEntity));

        // When
        Assertions.assertThrows(MovieAlreadyExistsException.class, () -> movieService.createMovie(movieDto));

        // Then
        Mockito.verify(movieRepository, times(0)).save(any());
    }

    @Test
    public void testUpdatingUnknownMovieShouldThrowMovieNotFoundException() {
        // Given
        Mockito.when(movieRepository.findById(movieDto.getTitle())).thenReturn(Optional.empty());

        // When
        Assertions.assertThrows(MovieNotFoundException.class, ()-> movieService.updateMovie(movieDto));

        // Then
        Mockito.verify(movieRepository, times(0)).save(any());

    }

    @Test
    public void testUpdateMovieShouldSaveUpdatedMovieWithValidMovieDto() throws MovieNotFoundException {
        // Given
        MovieDto movieDto = MovieDto.builder()
                .title("Star Wars")
                .genre("comedy")
                .screeningTime(150)
                .build();
        MovieEntity movie = new MovieEntity("Star Wars", "comedy", 150);
        Mockito.when(movieRepository.findById(movieDto.getTitle())).thenReturn(Optional.of(movie));

        // When
        movieService.updateMovie(movieDto);

        // Then
        Mockito.verify(movieRepository).save(movie);
    }

    @Test
    public void testDeletingMovieShouldDeleteMovie () throws MovieNotFoundException {

        // When
        movieService.deleteMovie("Star Wars");

        // Then
        Mockito.verify(movieRepository).deleteById("Star Wars");
        Mockito.verifyNoMoreInteractions(movieRepository);
    }

    @Test()
    public void testDeletingUnknownMovieShouldThrowUnknownMovieException() {
        // Given
        doThrow(EmptyResultDataAccessException.class).when(movieRepository).deleteById(anyString());

        // When
        Assertions.assertThrows(MovieNotFoundException.class, () -> {
            movieService.deleteMovie("Nonexistent Movie");
        });

        // Then
        Mockito.verify(movieRepository).deleteById("Nonexistent Movie");
        Mockito.verifyNoMoreInteractions(movieRepository);
    }


}
