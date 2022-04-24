package com.example.joinq.queue;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "queues")
@Getter @Setter
@NoArgsConstructor
public class Queue {

    @Id
    private UUID id = UUID.randomUUID();

    private String eol;
    private String nil;

    private String callNext;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<WaitingUser> waitingLine;

}
