package com.example.joinq.domain.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "organizations")
@Getter @Setter
public class Organization {

    @Id
    private UUID id = UUID.randomUUID();

    private String name;
    private String email;
    private String password;

    private String serviceType;

    @OneToMany
    private Set<ServiceCenter> serviceCenters;
}
