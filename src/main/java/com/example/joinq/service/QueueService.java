package com.example.joinq.service;

import com.example.joinq.domain.dto.CreateQueueDTO;
import com.example.joinq.domain.entities.Organization;
import com.example.joinq.domain.entities.Queue;
import com.example.joinq.domain.entities.User;
import com.example.joinq.domain.entities.WaitingLine;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface QueueService {

    Queue createQueue(CreateQueueDTO queueDTO);
    Queue getQueueById(String queueId);
    Page<WaitingLine> getWaitingUsersOnQueue(String queueId, int page, int size);
    WaitingLine joinQueue(String queueCode, String userId);
    void leaveQueue(String slotId);
    WaitingLine callNext(String queueId);
    List<Queue> getAllOrganizationQueues(String organizationId);
    List<WaitingLine> getAllUserLine(String username);
    Long getNumberOnQueue(String queueId);
    Queue closeQueue(String queueId);
    Queue openQueue(String queueId);
}
