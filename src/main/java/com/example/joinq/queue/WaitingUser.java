package com.example.joinq.queue;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "waiting_line")
@Getter @Setter
@NoArgsConstructor
public class WaitingUser {

    @Id
    private String id = UUID.randomUUID().toString();

    private String username;
    private String email;

    private String position;

    @ManyToOne(fetch = FetchType.LAZY)
    private Queue queue;
}
