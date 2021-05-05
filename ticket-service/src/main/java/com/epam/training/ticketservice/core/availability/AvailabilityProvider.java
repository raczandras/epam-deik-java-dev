package com.epam.training.ticketservice.core.availability;

import org.springframework.shell.Availability;

public interface AvailabilityProvider {

    Availability isAvailable();
}
