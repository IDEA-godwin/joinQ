package com.example.joinq.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateQueueDTO {

    private String name;
    private String description;
    private Long estimatedServiceTime;
    private String organizationId;
}
