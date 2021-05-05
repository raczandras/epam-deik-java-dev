package com.epam.training.ticketservice.core.user;

import com.epam.training.ticketservice.core.user.exception.UserNotFoundException;
import com.epam.training.ticketservice.core.user.impl.LoginServiceImpl;
import com.epam.training.ticketservice.core.user.model.UserDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class LoginServiceImplTest {

    private static final UserDto userDto = new UserDto("admin", false);

    private LoginServiceImpl loginServiceLoggedIn;

    private LoginServiceImpl loginServiceLoggedOut;

    private UserService userService;

    @BeforeEach
    public void init() {
        userService = Mockito.mock(UserService.class);
        loginServiceLoggedIn = new LoginServiceImpl(userService, userDto);
        loginServiceLoggedOut = new LoginServiceImpl(userService);
    }

    @Test
    public void testLoggingInThenGettingTheLoggedInUserShouldReturnLoggedInUser() throws UserNotFoundException {
        // Given
        Mockito.when(userService.getUserByUsernameAndPassword("admin", "admin")).thenReturn(userDto);

        // When
        UserDto expected = loginServiceLoggedOut.login("admin", "admin");

        // Then
        Assertions.assertEquals(expected, userDto);
        Mockito.verify(userService).getUserByUsernameAndPassword("admin", "admin");
        Mockito.verifyNoMoreInteractions(userService);
    }

    @Test
    public void testLoggingInWithNullUsernameShouldThrowNullPointerException() {
        // When
        Assertions.assertThrows(NullPointerException.class, () -> loginServiceLoggedOut.login(null, "admin"));

        // Then
        Mockito.verifyNoMoreInteractions(userService);
    }

    @Test
    public void testLoggingInWithNullPasswordShouldThrowNullPointerException() {
        // When
        Assertions.assertThrows(NullPointerException.class, () -> loginServiceLoggedOut.login("admin", null));

        // Then
        Mockito.verifyNoMoreInteractions(userService);
    }

    @Test
    public void testLoggingInWithUnknownUserShouldThrowUserNotFoundException() throws UserNotFoundException {
        // Given
        Mockito.doThrow(UserNotFoundException.class).when(userService).getUserByUsernameAndPassword("admin", "admin");

        // When
        Assertions.assertThrows(UserNotFoundException.class, () -> loginServiceLoggedOut.login("admin", "admin"));

        // Then
        Mockito.verify(userService).getUserByUsernameAndPassword("admin", "admin");
        Mockito.verifyNoMoreInteractions(userService);
    }

    @Test
    public void testGettingTheLoggedInUserShouldReturnLoggedInUserSuccessfully() throws UserNotFoundException {
        // When
        UserDto actual = loginServiceLoggedIn.getLoggedUser();

        // Then
        Assertions.assertEquals(userDto, actual);
        Mockito.verifyNoMoreInteractions(userService);
    }

    @Test
    public void testGettingLoggedInUserWhenLoggedOutShouldThrowUserNotFoundException() {
        // When
        Assertions.assertThrows(UserNotFoundException.class, () -> loginServiceLoggedOut.getLoggedUser());

        // Then
        Mockito.verifyNoMoreInteractions(userService);
    }

    @Test
    public void testLoggingOutWhenLoggedInShouldLogOutSuccessfully() throws UserNotFoundException {
        // Given
        String expected = "Logout successful";

        // When
        String actual = loginServiceLoggedIn.logout();

        // Then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testLoggingOutWhenLoggedOutShouldReturnNotSignedInMessage() {
        // Given
        String expected = "You are not signed in";

        // When
        String actual = loginServiceLoggedOut.logout();

        // Then
        Assertions.assertEquals(expected,actual);
    }
}
