package com.example.joinq.serviceImpl;

import com.example.joinq.domain.entities.Queue;
import com.example.joinq.domain.entities.User;
import com.example.joinq.domain.entities.WaitingLine;
import com.example.joinq.domain.enumeration.QueueStatusConstant;
import com.example.joinq.repository.WaitingLineRepository;
import com.example.joinq.service.LineService;
import org.aspectj.weaver.ast.Not;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LineServiceImpl implements LineService {

    private final WaitingLineRepository lineRepository;

    public LineServiceImpl(WaitingLineRepository lineRepository) {
        this.lineRepository = lineRepository;
    }

    @Override
    public Page<WaitingLine> getLine(Queue queue, int page, int size) {
        Pageable paging = PageRequest.of(page, size);
        return lineRepository.findAllByQueue(queue, paging);
    }

    @Override
    public WaitingLine getWaitingUserById(String id) {
        return lineRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }


    public WaitingLine getWaitingUserByTicket(String ticket) {
        Optional<WaitingLine> user = lineRepository.findWaitingUserByTicket(ticket);
        if (user.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No user with ticket: " + ticket + " found");
        return user.get();
    }

    @Override
    public WaitingLine callNextInLine(String ticket) {
        WaitingLine user = getWaitingUserByTicket(ticket);
        user.setPosition(0L);
        user.setStatus(QueueStatusConstant.SERVING);
        lineRepository.delete(lineRepository.getWaitingLineByQueueAndPosition(user.getQueue(), 0L));
        return lineRepository.save(user);
    }

    @Override
    public List<WaitingLine> getAllLineWithUser(User user) {
        return lineRepository.findAllByWaitingUser(user);
    }

    @Override
    public WaitingLine addToLine(Queue queue, User user) {
        WaitingLine waitingUser = new WaitingLine();
        waitingUser.setTicket(queue.getLastSlot());
        waitingUser.setNextInLine(queue.getNextSlot());
        waitingUser.setPosition(queue.getCount());
        waitingUser.setStatus(QueueStatusConstant.WAITING);
        waitingUser.setQueue(queue);
        waitingUser.setWaitingUser(user);
        waitingUser.setEntryTime(Instant.now());

        return lineRepository.save(waitingUser);
    }

    @Override
    public void removeFromLine(String id) {
        WaitingLine waitingUser = lineRepository.getById(UUID.fromString(id));
        Optional<WaitingLine> nextUser = lineRepository.findWaitingUserByTicket(waitingUser.getNextInLine());

        nextUser.ifPresent(waitingLine -> resetTicket(waitingLine, waitingUser.getTicket()));

        lineRepository.delete(waitingUser);
    }

    public void deleteAllFromLine(Queue queue) {
        List<WaitingLine> line = lineRepository.findAllByQueue(queue);
        lineRepository.deleteAllInBatch(line);
    }

    @Override
    public Long lineCount(Queue queue) {
        return lineRepository.countAllByQueueAndStatus(queue, QueueStatusConstant.WAITING);
    }

    void resetTicket(WaitingLine waitingUser, String ticket) {
        waitingUser.setTicket(ticket);
        lineRepository.save(waitingUser);
    }
}
