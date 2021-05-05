package com.epam.training.ticketservice.core.user;

import com.epam.training.ticketservice.core.user.exception.UserNotFoundException;
import com.epam.training.ticketservice.core.user.impl.UserServiceImpl;
import com.epam.training.ticketservice.core.user.model.UserDto;
import com.epam.training.ticketservice.core.user.persistence.entity.UserEntity;
import com.epam.training.ticketservice.core.user.persistence.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

public class UserServiceImplTest {

    private static final UserDto userDto = new UserDto("admin", true);
    private static final UserEntity user = new UserEntity("admin", "admin", true);

    private UserServiceImpl userService;

    private UserRepository userRepository;

    @BeforeEach
    public void init() {
        userRepository = Mockito.mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    public void testGettingUserShouldReturnUserSuccessfully() throws UserNotFoundException {
        // Given
        Mockito.when(userRepository.findByUsernameAndPassword("admin", "admin")).thenReturn(Optional.of(user));

        // When
        UserDto actual = userService.getUserByUsernameAndPassword("admin", "admin");

        // Then
        Assertions.assertEquals(actual, userDto);
        Mockito.verify(userRepository).findByUsernameAndPassword("admin", "admin");
        Mockito.verifyNoMoreInteractions(userRepository);

    }

    @Test
    public void testGettingUserByNullUsernameShouldThrowNullPointerException() {
        // When
        Assertions.assertThrows(NullPointerException.class, () -> userService.getUserByUsernameAndPassword(null, "admin"));

        // Then
        Mockito.verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void testGettingUserByNullPasswordShouldThrowNullPointerException() {
        // When
        Assertions.assertThrows(NullPointerException.class, () -> userService.getUserByUsernameAndPassword("admin", null));

        // Then
        Mockito.verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void testGettingUserByWrongPasswordShouldThrowUserNotFoundException() {
        // Given
        Mockito.when(userRepository.findByUsernameAndPassword("user", "pw")).thenReturn(Optional.empty());

        // When
        Assertions.assertThrows(UserNotFoundException.class, () -> userService.getUserByUsernameAndPassword("user", "pw"));

        // Then
        Mockito.verify(userRepository).findByUsernameAndPassword("user", "pw");
        Mockito.verifyNoMoreInteractions(userRepository);
    }

}
