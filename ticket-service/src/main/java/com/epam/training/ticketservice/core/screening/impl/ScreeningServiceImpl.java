package com.epam.training.ticketservice.core.screening.impl;

import com.epam.training.ticketservice.core.movie.exception.MovieNotFoundException;
import com.epam.training.ticketservice.core.movie.persistence.entity.MovieEntity;
import com.epam.training.ticketservice.core.movie.persistence.repository.MovieRepository;
import com.epam.training.ticketservice.core.room.exception.RoomNotFoundException;
import com.epam.training.ticketservice.core.room.persistence.entity.RoomEntity;
import com.epam.training.ticketservice.core.room.persistence.repository.RoomRepository;
import com.epam.training.ticketservice.core.screening.ScreeningService;
import com.epam.training.ticketservice.core.screening.exception.ScreeningNotFoundException;
import com.epam.training.ticketservice.core.screening.exception.ScreeningsOverlapException;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import com.epam.training.ticketservice.core.screening.persistence.entity.ScreeningEntity;
import com.epam.training.ticketservice.core.screening.persistence.repository.ScreeningRepository;
import com.epam.training.ticketservice.core.dateconverter.DateConverterService;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ScreeningServiceImpl implements ScreeningService {

    private final ScreeningRepository screeningRepository;
    private final MovieRepository movieRepository;
    private final RoomRepository roomRepository;
    private final DateConverterService dateConverterService;

    public ScreeningServiceImpl(ScreeningRepository screeningRepository, MovieRepository movieRepository, RoomRepository roomRepository, DateConverterService dateConverterService) {
        this.screeningRepository = screeningRepository;
        this.movieRepository = movieRepository;
        this.roomRepository = roomRepository;
        this.dateConverterService = dateConverterService;
    }


    @Override
    public List<ScreeningDto> listScreenings() {
        List<ScreeningDto> screenings = screeningRepository.findAll().stream().map(screening ->
                ScreeningDto.builder()
                        .room(screening.getRoom().getName())
                        .movie(screening.getMovie())
                        .startDate(dateConverterService.convertDateToString(screening.getStartDate()))
                        .build()
        ).collect(Collectors.toList());
        if (screenings.isEmpty()) {
            System.out.println("There are no screenings");
        }
        return screenings;
    }

    @Override
    public void createScreening(ScreeningDto screeningDto) throws MovieNotFoundException, RoomNotFoundException, ParseException, ScreeningsOverlapException {
        checkForNulls(screeningDto);

        ScreeningEntity screening = makeScreening(screeningDto);

        checkOverlappingScreening(screening);
        screeningRepository.save(screening);
    }

    @Override
    public void deleteScreening(ScreeningDto screeningDto) throws MovieNotFoundException, RoomNotFoundException, ScreeningNotFoundException, ParseException {
        checkForNulls(screeningDto);

        ScreeningEntity screening = makeScreening(screeningDto);

        Optional<ScreeningEntity> screeningToDelete = screeningRepository
                .findFirstByMovieAndRoomAndStartDate(screening.getMovie(), screening.getRoom(),
                        screening.getStartDate());
        if (screeningToDelete.isEmpty()) {
            throw new ScreeningNotFoundException(screeningDto + " doesn't exist");
        }
        screeningRepository.delete(screeningToDelete.get());
    }

    private void checkForNulls(ScreeningDto screeningDto) {
        Objects.requireNonNull(screeningDto, "Screening cannot be null");
        Objects.requireNonNull(screeningDto.getMovie().getTitle(), "Movie title cannot be null");
        Objects.requireNonNull(screeningDto.getRoom(), "Room name cannot be null");
        Objects.requireNonNull(screeningDto.getStartDate(), "Start date cannot be null");
    }

    private ScreeningEntity makeScreening(ScreeningDto screeningDto) throws MovieNotFoundException, RoomNotFoundException, ParseException {
        return ScreeningEntity.builder()
                .movie(queryMovie(screeningDto.getMovie().getTitle()))
                .room(queryRoom(screeningDto.getRoom()))
                .startDate(dateConverterService.convertStringToDate(screeningDto.getStartDate()))
                .build();
    }


    protected MovieEntity queryMovie(String title) throws MovieNotFoundException {
        Optional<MovieEntity> movie = movieRepository.findById(title);
        if (movie.isEmpty()) {
            throw new MovieNotFoundException("Movie: " + title + " doesn't exist.");
        }
        return movie.get();
    }

    protected RoomEntity queryRoom(String name) throws RoomNotFoundException {
        Optional<RoomEntity> room = roomRepository.findById(name);
        if (room.isEmpty()) {
            throw new RoomNotFoundException("Room: " + name + " doesn't exist");
        }
        return room.get();
    }

    protected void checkOverlappingScreening(ScreeningEntity screeningToAdd) throws ScreeningsOverlapException {
        Date screeningToAddStart = screeningToAdd.getStartDate();
        Date screeningToAddEnd = DateUtils.addMinutes(screeningToAddStart,
                screeningToAdd.getMovie().getScreeningTime());
        List<ScreeningEntity> screenings = screeningRepository.findAllByRoom(screeningToAdd.getRoom());
        if (!screenings.isEmpty()) {
            for (ScreeningEntity screening : screenings) {
                Date screeningStart = screening.getStartDate();
                Date screeningEnd = DateUtils.addMinutes(screeningStart,
                        screening.getMovie().getScreeningTime());
                if ((screeningToAddStart.before(screeningEnd))
                        && (screeningToAddEnd.after(screeningStart))) {
                    throw new ScreeningsOverlapException("There is an overlapping screening");
                }
                if ((screeningToAddStart.before(DateUtils.addMinutes(screeningEnd, 11)))
                        && (screeningToAddEnd.after(screeningStart))) {
                    throw new ScreeningsOverlapException(
                            "This would start in the break period after another screening in this room");
                }
            }
        }
    }

}
