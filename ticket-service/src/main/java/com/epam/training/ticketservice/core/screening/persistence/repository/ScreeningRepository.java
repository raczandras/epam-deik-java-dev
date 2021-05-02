package com.epam.training.ticketservice.core.screening.persistence.repository;

import com.epam.training.ticketservice.core.movie.persistence.entity.MovieEntity;
import com.epam.training.ticketservice.core.room.persistence.entity.RoomEntity;
import com.epam.training.ticketservice.core.screening.persistence.entity.ScreeningEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScreeningRepository extends JpaRepository<ScreeningEntity, Integer> {
    Optional<ScreeningEntity> findFirstByMovieAndRoomAndStartDate(MovieEntity movie, RoomEntity room, Date startDate);

    List<ScreeningEntity> findAllByRoom(RoomEntity room);
}
