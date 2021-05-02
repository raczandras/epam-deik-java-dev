package com.epam.training.ticketservice.core.room.exception;

public class RoomAlreadyExistsException extends Exception {

    public RoomAlreadyExistsException(String message) {
        super(message);
    }
}
