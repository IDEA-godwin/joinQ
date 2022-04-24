package com.example.joinq.queue.services;

import com.example.joinq.queue.repositories.QueueRepository;
import org.springframework.stereotype.Service;

@Service
public class QueueService {

    private final QueueRepository queueRepository;
    private final WaitingUserService userService;

    public QueueService(QueueRepository queueRepository, WaitingUserService userService) {
        this.queueRepository = queueRepository;
        this.userService = userService;
    }
}
