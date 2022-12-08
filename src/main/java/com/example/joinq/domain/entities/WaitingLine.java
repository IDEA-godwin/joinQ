package com.example.joinq.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "queues_waiting_line")
@Getter @Setter
@NoArgsConstructor
public class WaitingLine {

    @Id
    private UUID id;
    private String ticket;
    private String nextInLine;
    private Long position;

    // autogenerated random string
    // used to track user position on queue
    private String lineCodeName;

    @ManyToOne
    private User waitingUser;

    @ManyToOne
    private Queue queue;

}
