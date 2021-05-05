package com.epam.training.ticketservice.core.room;

import com.epam.training.ticketservice.core.room.exception.RoomAlreadyExistsException;
import com.epam.training.ticketservice.core.room.exception.RoomNotFoundException;
import com.epam.training.ticketservice.core.room.impl.RoomServiceImpl;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.core.room.persistence.entity.RoomEntity;
import com.epam.training.ticketservice.core.room.persistence.repository.RoomRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;

public class RoomServiceImplTest {

    private static final RoomEntity room = new RoomEntity("Room", 5, 5);
    private static final RoomEntity room2 = new RoomEntity("Room2", 10, 10);
    private static final RoomDto roomDto = RoomDto.builder()
            .name("Room")
            .rows(5)
            .columns(5)
            .build();
    private static final RoomDto roomDto2 = RoomDto.builder()
            .name("Room2")
            .rows(10)
            .columns(10)
            .build();

    private RoomServiceImpl roomService;

    private RoomRepository roomRepository;

    @BeforeEach
    public void init() {
        roomRepository = Mockito.mock(RoomRepository.class);
        roomService = new RoomServiceImpl(roomRepository);
    }

    @Test
    public void testGetAllRoomsShouldReturnRoomsSuccessfully() {
        // Given
        Mockito.when(roomRepository.findAll()).thenReturn(List.of(room, room2));
        List<RoomDto> expected = List.of(roomDto, roomDto2);

        // When
        List<RoomDto> actual  = roomService.getRoomList();

        // Then
        Assertions.assertEquals(expected, actual);
        Mockito.verify(roomRepository).findAll();
        Mockito.verifyNoMoreInteractions(roomRepository);
    }


    @Test
    public void testCreateRoomShouldCreateRoomSuccessfully() throws RoomAlreadyExistsException {
        // Given
        Mockito.when(roomRepository.findById(roomDto.getName())).thenReturn(Optional.empty());

        // When
        roomService.createRoom(roomDto);

        // Then
        Mockito.verify(roomRepository, times(1)).save(room);
    }

    @Test
    public void testCreatingNullRoomShouldThrowNullPointerException() {
        // Given

        // When
        Assertions.assertThrows(NullPointerException.class, () -> roomService.createRoom(null));

        /// Then
        Mockito.verifyNoMoreInteractions(roomRepository);
    }

    @Test
    public void testCreatingRoomWithNullTitleShouldThrowNullPointerException() {
        // Given
        RoomDto roomDto = RoomDto.builder()
                .name(null)
                .columns(10)
                .rows(10)
                .build();

        // When
        Assertions.assertThrows(NullPointerException.class, () -> roomService.createRoom(roomDto));

        /// Then
        Mockito.verifyNoMoreInteractions(roomRepository);
    }

    @Test
    public void testCreatingRoomThatAlreadyExistsShouldThrowRoomAlreadyExistsException() {
        // Given
        Mockito.when(roomRepository.findById(roomDto.getName())).thenReturn(java.util.Optional.of(room));

        // When
        Assertions.assertThrows(RoomAlreadyExistsException.class, () -> roomService.createRoom(roomDto));

        // Then
        Mockito.verify(roomRepository, times(0)).save(room);
    }

    @Test
    public void testUpdatingRoomShouldUpdateRoomSuccessfully() throws RoomNotFoundException {
        // Given
        Mockito.when(roomRepository.findById(roomDto.getName())).thenReturn(Optional.of(room));

        // When
        roomService.updateRoom(roomDto);

        // Then
        Mockito.verify(roomRepository, times(1)).save(room);
    }

    @Test
    public void testUpdatingNullRoomShouldThrowNullPointerException() {
        // When
        Assertions.assertThrows(NullPointerException.class, () -> roomService.updateRoom(null));

        /// Then
        Mockito.verifyNoMoreInteractions(roomRepository);
    }

    @Test
    public void testUpdatingRoomWithNullNameShouldThrowNullPointerException() {
        // Given
        RoomDto roomDto = RoomDto.builder()
                .name(null)
                .rows(10)
                .columns(10)
                .build();

        // When
        Assertions.assertThrows(NullPointerException.class, () -> roomService.updateRoom(roomDto));

        // Then
        Mockito.verifyNoMoreInteractions(roomRepository);
    }

    @Test
    public void testUpdatingUnknownRoomShouldThrowRoomNotFoundException() {
        // Given
        Mockito.when(roomRepository.findById(roomDto.getName())).thenReturn(Optional.empty());

        // When
        Assertions.assertThrows(RoomNotFoundException.class, () -> roomService.updateRoom(roomDto));

        // Then
        Mockito.verify(roomRepository, times(0)).save(room);
    }

    @Test
    public void testDeletingRoomShouldDeleteRoomSuccessfully() throws RoomNotFoundException {
        // Given

        // When
        roomService.deleteRoom(roomDto.getName());

        // Then
        Mockito.verify(roomRepository).deleteById(roomDto.getName());
        Mockito.verifyNoMoreInteractions(roomRepository);
    }

    @Test
    public void testDeletingRoomWithNullNameShouldThrowNullPointerException() {
        // When
        Assertions.assertThrows(NullPointerException.class, () -> roomService.deleteRoom(null));

        // Then
        Mockito.verifyNoMoreInteractions(roomRepository);
    }

    @Test
    public void testDeletingUnknownRoomShouldThrowRoomNotFoundException() {
        // Given
        doThrow(EmptyResultDataAccessException.class).when(roomRepository).deleteById(roomDto.getName());

        // When
        Assertions.assertThrows(RoomNotFoundException.class, () -> roomService.deleteRoom(roomDto.getName()));

        // Then
        Mockito.verify(roomRepository, times(1)).deleteById(roomDto.getName());
        Mockito.verifyNoMoreInteractions(roomRepository);
    }

}
