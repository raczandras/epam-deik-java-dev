package com.epam.training.ticketservice.core.screening.persistence.entity;

import com.epam.training.ticketservice.core.movie.persistence.entity.MovieEntity;
import com.epam.training.ticketservice.core.room.persistence.entity.RoomEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Generated;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.Column;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Generated
public class ScreeningEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int screeningId;

    @ManyToOne
    @JoinColumn(name = "name")
    private RoomEntity room;

    @ManyToOne
    @JoinColumn(name = "title")
    private MovieEntity movie;

    @Column(unique = true, name = "date")
    private Date startDate;

}
