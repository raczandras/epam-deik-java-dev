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
        List<RoomDto> rooms = roomRepository.findAll()
                .stream().map(this::convertEntityToDto).collect(Collectors.toList());
        if (rooms.isEmpty()) {
            System.out.println("There are no rooms at the moment");
        }
        return rooms;
    }

    @Override
    public void createRoom(RoomDto roomDto) throws RoomAlreadyExistsException {
        Optional<RoomEntity> roomToCreate  = checkRoom(roomDto);

        if (roomToCreate.isPresent()) {
            throw new RoomAlreadyExistsException("Room: " + roomDto.getName() + " already exists");
        }
        roomRepository.save(new RoomEntity(roomDto.getName(), roomDto.getRows(), roomDto.getColumns()));
    }

    @Override
    public void updateRoom(RoomDto roomDto) throws RoomNotFoundException {
        Optional<RoomEntity> roomToUpdate  = checkRoom(roomDto);

        if (roomToUpdate.isEmpty()) {
            throw new RoomNotFoundException("Room: " + roomDto.getName() + " doesn't exist");
        }
        roomRepository.save(new RoomEntity(roomDto.getName(), roomDto.getRows(), roomDto.getColumns()));
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

    private Optional<RoomEntity> checkRoom(RoomDto roomDto) {
        Objects.requireNonNull(roomDto, "Room cannot be null");
        Objects.requireNonNull(roomDto.getName(), "Name cannot be null");
        return roomRepository.findById(roomDto.getName());
    }

    private RoomDto convertEntityToDto(RoomEntity roomEntity) {
        return RoomDto.builder()
                .name(roomEntity.getName())
                .columns(roomEntity.getColumns())
                .rows(roomEntity.getRows())
                .build();
    }
}
