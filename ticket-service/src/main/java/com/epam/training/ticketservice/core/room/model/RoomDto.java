package com.epam.training.ticketservice.core.room.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Generated;
import lombok.Getter;

@Builder
@EqualsAndHashCode
@Getter
@Generated
public class RoomDto {

    private final String name;
    private final int rows;
    private final int columns;

    @Override
    public String toString() {
        return "Room "+name+" with "+rows * columns+" seats, "+rows+" rows and "+columns+" columns";
    }
}
