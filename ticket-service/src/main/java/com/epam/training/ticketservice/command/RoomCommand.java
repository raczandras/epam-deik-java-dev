package com.epam.training.ticketservice.command;

import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

@ShellComponent
public class RoomCommand {
    //TODO make commands actually work

    public RoomCommand() {
    }

    @ShellMethod(value = "List All Rooms", key = "list rooms")
    public String[] listRooms() {
        return "Elso filme;Masodik film;Harmadik film".split(";");
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(value = "Create a new Room", key = "create room")
    public void createRoom(String name, int rows, int columns) {
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(value = "Update a room", key = "update room")
    public void updateRoom(String name, int rows, int columns) {
            System.out.println("Room Updated: ");
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(value = "Delete room by name", key = "delete room")
    public void deleteRoom(String name) {
    }

    private Availability isAvailable() {
        return Availability.available();
    }
}
