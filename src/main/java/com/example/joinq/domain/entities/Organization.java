package com.example.joinq.domain.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "organizations")
@Getter @Setter
public class Organization {

    @Id
    private UUID id = UUID.randomUUID();

    private String name;
    private String serviceType;
    private Date createdAt;

    @OneToOne
    private User user;

}
