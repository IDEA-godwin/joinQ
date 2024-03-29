package com.example.joinq.repository;

import com.example.joinq.domain.entities.Queue;
import com.example.joinq.domain.entities.User;
import com.example.joinq.domain.entities.WaitingLine;
import com.example.joinq.domain.enumeration.QueueStatusConstant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface WaitingLineRepository extends JpaRepository<WaitingLine, UUID> {

    Optional<WaitingLine> findWaitingUserByTicket(String ticket);
    WaitingLine getWaitingLineByQueueAndPosition(Queue queue, Long position);
    List<WaitingLine> findAllByWaitingUser(User user);
    Page<WaitingLine> findAllByQueue(Queue queue, Pageable pageable);
    Long countAllByQueueAndStatus(Queue queue, QueueStatusConstant status);
    List<WaitingLine> findAllByQueue(Queue queue);
}
