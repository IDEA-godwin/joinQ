package com.example.joinq.queue.services;

import com.example.joinq.queue.repositories.WaitingUserRepository;
import org.springframework.stereotype.Service;

@Service
public class WaitingUserService {

    private final WaitingUserRepository userRepository;

    public WaitingUserService(WaitingUserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
