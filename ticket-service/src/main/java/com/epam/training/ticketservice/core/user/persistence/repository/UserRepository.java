package com.epam.training.ticketservice.core.user.persistence.repository;

import com.epam.training.ticketservice.core.user.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByUsernameAndPassword(String username, String password);
}