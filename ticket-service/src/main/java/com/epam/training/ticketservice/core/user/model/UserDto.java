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
            return "privileged account '" + username + "'";
        } else  {
            return "account " + username;
        }
    }
}