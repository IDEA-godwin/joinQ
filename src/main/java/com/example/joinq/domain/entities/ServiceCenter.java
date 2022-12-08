package com.example.joinq.domain.entities;

import com.example.joinq.domain.entities.Queue;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Getter @Setter
@NoArgsConstructor
@Entity
@Table(name = "service_centers")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class ServiceCenter {

    @Id
    private UUID id = UUID.randomUUID();

    private String name;
    private String averageServiceTime;

    @ManyToOne(fetch = FetchType.LAZY)
    private Queue queue;
}
