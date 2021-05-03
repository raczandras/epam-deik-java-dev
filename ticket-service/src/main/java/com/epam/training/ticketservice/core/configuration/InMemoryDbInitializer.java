package com.epam.training.ticketservice.core.configuration;

import com.epam.training.ticketservice.core.user.persistence.entity.UserEntity;
import com.epam.training.ticketservice.core.user.persistence.repository.UserRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class InMemoryDbInitializer {

    private final UserRepository userRepository;

    public InMemoryDbInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void init() {
        UserEntity admin = new UserEntity("admin", "admin", true);
        userRepository.save(admin);
    }
}
