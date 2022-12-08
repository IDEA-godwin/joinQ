package com.example.joinq.service;

import com.example.joinq.domain.entities.Queue;
import com.example.joinq.domain.entities.User;
import com.example.joinq.domain.entities.WaitingLine;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface QueueService {

    Queue createQueue();
    Queue getQueueById(String queueId);
    Page<WaitingLine> getWaitingUsersOnQueue(String queueId, int page, int size);
    WaitingLine joinQueue(String queueId, User user);
    void leaveQueue(String slotId);
    WaitingLine callNext(String queueId);
}
