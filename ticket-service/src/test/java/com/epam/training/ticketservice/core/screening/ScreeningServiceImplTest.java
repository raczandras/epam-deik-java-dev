package com.epam.training.ticketservice.core.screening;

import com.epam.training.ticketservice.core.dateconverter.DateConverterService;
import com.epam.training.ticketservice.core.movie.exception.MovieNotFoundException;
import com.epam.training.ticketservice.core.movie.persistence.entity.MovieEntity;
import com.epam.training.ticketservice.core.movie.persistence.repository.MovieRepository;
import com.epam.training.ticketservice.core.room.exception.RoomNotFoundException;
import com.epam.training.ticketservice.core.room.persistence.entity.RoomEntity;
import com.epam.training.ticketservice.core.room.persistence.repository.RoomRepository;
import com.epam.training.ticketservice.core.screening.exception.ScreeningsOverlapException;
import com.epam.training.ticketservice.core.screening.impl.ScreeningServiceImpl;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import com.epam.training.ticketservice.core.screening.model.ScreeningOutDto;
import com.epam.training.ticketservice.core.screening.persistence.entity.ScreeningEntity;
import com.epam.training.ticketservice.core.screening.persistence.repository.ScreeningRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.text.ParseException;
import java.util.Date;
import java.util.Optional;


public class ScreeningServiceImplTest {

    @Deprecated
    private final static Date date = new Date(2021, 5, 5);

    private static final ScreeningEntity screening = ScreeningEntity.builder()
            .room(new RoomEntity("Room", 5,5))
            .movie(new MovieEntity("Star Wars", "sci-fi", 180))
            .startDate(date)
            .build();

    private static final MovieEntity movie = new MovieEntity("Star Wars", "sci-fi", 180);

    private static final ScreeningOutDto screeningListDto = ScreeningOutDto.builder()
            .movie(new MovieEntity("Star Wars", "sci-fi", 180))
            .room("Room")
            .startDate("2021-05-05 12:00")
            .build();

    private ScreeningServiceImpl underTest;

    private ScreeningRepository screeningRepository;

    private MovieRepository movieRepository;

    private RoomRepository roomRepository;

    private DateConverterService dateConverterService;

    @BeforeEach
    public void init() {
        screeningRepository = Mockito.mock(ScreeningRepository.class);
        movieRepository = Mockito.mock(MovieRepository.class);
        roomRepository = Mockito.mock(RoomRepository.class);
        underTest = new ScreeningServiceImpl(screeningRepository, movieRepository, roomRepository, dateConverterService);
    }

    @Test
    public void testCreatingScreeningWithNullDtoShouldThrowNullPointerException() {
        // When
        Assertions.assertThrows(NullPointerException.class, () -> underTest.createScreening(null));

        // Then
        Mockito.verifyNoMoreInteractions(screeningRepository);
    }

    @Test
    public void testCreatingScreeningWithNullMovieTitleShouldThrowNullPointerException() {
        // Given
        ScreeningDto screeningDto = ScreeningDto.builder()
                .movie(null)
                .room("Room")
                .startDate("2021-05-05 12:00")
                .build();

        // When
        Assertions.assertThrows(NullPointerException.class, () -> underTest.createScreening(screeningDto));

        // Then
        Mockito.verifyNoMoreInteractions(screeningRepository);
    }

    @Test
    public void testCreatingScreeningWithNullRoomNameShouldThrowNullPointerException() {
        // Given
        ScreeningDto screeningDto = ScreeningDto.builder()
                .movie("Star Wars")
                .room(null)
                .startDate("2021-05-05 12:00")
                .build();

        // When
        Assertions.assertThrows(NullPointerException.class, () -> underTest.createScreening(screeningDto));

        // Then
        Mockito.verifyNoMoreInteractions(screeningRepository);
    }

    @Test
    public void testCreatingScreeningWithNullStartDateShouldThrowNullPointerException() {
        // Given
        ScreeningDto screeningDto = ScreeningDto.builder()
                .movie("Star Wars")
                .room("Room")
                .startDate(null)
                .build();

        // When
        Assertions.assertThrows(NullPointerException.class, () -> underTest.createScreening(screeningDto));

        // Then
        Mockito.verifyNoMoreInteractions(screeningRepository);
    }

    @Test
    public void testCreatingScreeningWithUnknownMovieShouldThrowMovieNotFoundException() throws ScreeningsOverlapException, RoomNotFoundException, MovieNotFoundException, ParseException {
        // Given
        ScreeningDto screeningDto = ScreeningDto.builder()
                .movie("Star Wars")
                .room("Room")
                .startDate("2021-05-05 12:00")
                .build();
        Mockito.when(movieRepository.findById("Star Wars")).thenReturn(Optional.empty());

        // When
        Assertions.assertThrows(MovieNotFoundException.class, () -> underTest.createScreening(screeningDto));

        // Then
        Mockito.verifyNoMoreInteractions(screeningRepository);
        Mockito.verify(movieRepository).findById("Star Wars");
        Mockito.verifyNoMoreInteractions(movieRepository);
    }

    @Test
    public void testCreatingScreeningWithUnknownRoomShouldThrowRoomNotFoundException() {
        // Given
        ScreeningDto screeningDto = ScreeningDto.builder()
                .movie("Star Wars")
                .room("Room")
                .startDate("2021-05-05 12:00")
                .build();
        Mockito.when(roomRepository.findById("Room")).thenReturn(Optional.empty());
        Mockito.when(movieRepository.findById("Star Wars")).thenReturn(Optional.of(movie));

        // When
        Assertions.assertThrows(RoomNotFoundException.class, () -> underTest.createScreening(screeningDto));

        // Then
        Mockito.verifyNoMoreInteractions(screeningRepository);
        Mockito.verify(roomRepository).findById("Room");
        Mockito.verifyNoMoreInteractions(roomRepository);
        Mockito.verify(movieRepository).findById("Star Wars");
        Mockito.verifyNoMoreInteractions(movieRepository);
    }



}
