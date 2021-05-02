package com.epam.training.ticketservice.core.movie.exception;

public class MovieAlreadyExistsException extends Exception {

    public MovieAlreadyExistsException(String message) {
        super(message);
    }
}
