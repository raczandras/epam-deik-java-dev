package com.epam.training.ticketservice.core.room.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Generated
@Entity
@Table(name = "rooms")
public class RoomEntity {

    @Id
    @Column(unique = true, name = "name")
    private String name;

    @Column(name = "rows")
    private int rows;

    @Column(name = "columns")
    private int columns;
}
