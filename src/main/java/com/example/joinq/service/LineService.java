package com.example.joinq.service;

import com.example.joinq.domain.entities.Queue;
import com.example.joinq.domain.entities.User;
import com.example.joinq.domain.entities.WaitingLine;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LineService {

    Page<WaitingLine> getLine(Queue queue, int page, int size);
    WaitingLine getWaitingUserByTicket(String ticket);
    List<WaitingLine> getAllLineWithUser(User user);
    WaitingLine addToLine(Queue queue, User user);
    void removeFromLine(String id);
    Long lineCount(Queue queue);
}
