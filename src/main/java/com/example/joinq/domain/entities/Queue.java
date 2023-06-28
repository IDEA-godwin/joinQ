package com.example.joinq.domain.entities;

import com.example.joinq.domain.enumeration.QueueStatusConstant;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "queues")
@Getter @Setter
@NoArgsConstructor
public class Queue {

    @Id
    private UUID id = UUID.randomUUID();

    private String name;
    private String description;
    private Long estimatedServiceTime;

    private String queueCode;
    private Long count;

    private String lastSlot;
    private String nextSlot;
    private String callNext;

    private Date createdAt;

    private QueueStatusConstant status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Organization organization;

}
