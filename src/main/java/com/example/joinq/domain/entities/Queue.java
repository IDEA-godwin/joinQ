package com.example.joinq.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "queues")
@Getter @Setter
@NoArgsConstructor
public class Queue {

    @Id
    private UUID id = UUID.randomUUID();

    private String lastSlot;
    private String nextSlot;

    private String callNext;

}
