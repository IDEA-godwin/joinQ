package com.example.joinq.domain.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Getter @Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {


    @Id
    private UUID id = UUID.randomUUID();

    private String username;
    private String email;
    private String contactNumber;
}

