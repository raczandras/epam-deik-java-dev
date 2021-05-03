package com.epam.training.ticketservice.core.user.persistence.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Generated
@Entity
public class UserEntity {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Integer id;
    @Column(unique = true)
    private String username;
    private String password;
    private boolean admin;

    public UserEntity(String username, String password, boolean admin) {
        this.username = username;
        this.password = password;
        this.admin = admin;
    }
}