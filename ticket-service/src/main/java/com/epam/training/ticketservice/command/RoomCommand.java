package com.epam.training.ticketservice.command;

import com.epam.training.ticketservice.core.room.RoomService;
import com.epam.training.ticketservice.core.room.exception.RoomAlreadyExistsException;
import com.epam.training.ticketservice.core.room.exception.RoomNotFoundException;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.util.List;

@ShellComponent
public class RoomCommand {
    private final RoomService roomService;

    public RoomCommand(RoomService roomService) {
        this.roomService = roomService;
    }

    @ShellMethod(value = "List All Rooms", key = "list rooms")
    public List<RoomDto> listRooms() {
        return roomService.getRoomList();
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(value = "Create a new Room", key = "create room")
    public void createRoom(String name, int rows, int columns) {
        RoomDto roomDto = RoomDto.builder()
                .name(name)
                .rows(rows)
                .columns(columns)
                .build();
        try {
            roomService.createRoom(roomDto);
            System.out.println("Room Created: " + roomDto);
        } catch (RoomAlreadyExistsException e) {
            System.out.println(e.getMessage());
        }
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(value = "Update a room", key = "update room")
    public void updateRoom(String name, int rows, int columns) {
        RoomDto roomDto = RoomDto.builder()
                .name(name)
                .rows(rows)
                .columns(columns)
                .build();
        try {
            roomService.updateRoom(roomDto);
            System.out.println("Room Updated: " + roomDto);
        } catch (RoomNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(value = "Delete room by name", key = "delete room")
    public void deleteRoom(String name) {
        try {
            roomService.deleteRoom(name);
            System.out.printf("Room %s deleted%n", name);
        } catch (RoomNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private Availability isAvailable() {
        return Availability.available();
    }
}
