package com.epam.training.ticketservice.core.user.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Generated;
import lombok.Getter;

@Getter
@Generated
@EqualsAndHashCode
@AllArgsConstructor
public class UserDto {

    private final String username;
    private final boolean admin;

    @Override
    public String toString() {
        if (admin) {
            return String.format("privileged account '"+username+"'");
        } else  {
            return String.format("account '%s'", username);
        }
    }
}