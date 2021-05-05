package com.epam.training.ticketservice.core.room.impl;

import com.epam.training.ticketservice.core.room.RoomService;
import com.epam.training.ticketservice.core.room.exception.RoomAlreadyExistsException;
import com.epam.training.ticketservice.core.room.exception.RoomNotFoundException;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.core.room.persistence.entity.RoomEntity;
import com.epam.training.ticketservice.core.room.persistence.repository.RoomRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public List<RoomDto> getRoomList() {
        List<RoomDto> rooms = roomRepository.findAll().stream().map(room ->
                RoomDto.builder()
                        .name(room.getName())
                        .rows(room.getRows())
                        .columns(room.getColumns())
                        .build()
        ).collect(Collectors.toList());
        if (rooms.isEmpty()) {
            System.out.println("There are no rooms at the moment");
        }
        return rooms;
    }

    @Override
    public void createRoom(RoomDto roomDto) throws RoomAlreadyExistsException {
        Objects.requireNonNull(roomDto, "Room cannot be null");
        Objects.requireNonNull(roomDto.getName(), "Name cannot be null");
        Optional<RoomEntity> roomToCreate  = roomRepository.findById(roomDto.getName());
        if (roomToCreate.isPresent()) {
            throw new RoomAlreadyExistsException("Room: " + roomDto.getName() + " already exists");
        }
        roomRepository.save(new RoomEntity(
                roomDto.getName(),
                roomDto.getRows(),
                roomDto.getColumns()
        ));
    }

    @Override
    public void updateRoom(RoomDto roomDto) throws RoomNotFoundException {
        Objects.requireNonNull(roomDto, "Room cannot be null");
        Objects.requireNonNull(roomDto.getName(), "Name cannot be null");
        Optional<RoomEntity> roomToUpdate  = roomRepository.findById(roomDto.getName());
        if (roomToUpdate.isEmpty()) {
            throw new RoomNotFoundException("Room: " + roomDto.getName() + " doesn't exist");
        }
        roomRepository.save(new RoomEntity(
                roomDto.getName(),
                roomDto.getRows(),
                roomDto.getColumns()
        ));
    }

    @Override
    public void deleteRoom(String name) throws RoomNotFoundException {
        Objects.requireNonNull(name, "Room name cannot be null");
        try {
            roomRepository.deleteById(name);
        } catch (EmptyResultDataAccessException e) {
            throw new RoomNotFoundException("Room: " + name + " doesn't exist");
        }
    }
}
