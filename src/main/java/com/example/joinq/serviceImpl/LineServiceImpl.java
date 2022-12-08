package com.example.joinq.serviceImpl;

import com.example.joinq.domain.entities.Queue;
import com.example.joinq.domain.entities.User;
import com.example.joinq.domain.entities.WaitingLine;
import com.example.joinq.repository.WaitingLineRepository;
import com.example.joinq.service.LineService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
    public WaitingLine getWaitingUserByTicket(String ticket) {
        Optional<WaitingLine> user = lineRepository.findWaitingUserByTicket(ticket);
        if (user.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No user with ticket: " + ticket + " found");
        return user.get();
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
        waitingUser.setQueue(queue);
        waitingUser.setWaitingUser(user);

        return lineRepository.save(waitingUser);
    }

    @Override
    public void removeFromLine(String id) {
        WaitingLine waitingUser = lineRepository.getById(UUID.fromString(id));
        Optional<WaitingLine> nextUser = lineRepository.findWaitingUserByTicket(waitingUser.getNextInLine());

        nextUser.ifPresent(waitingLine -> resetTicket(waitingLine, waitingUser.getTicket()));

        lineRepository.delete(waitingUser);
    }

    @Override
    public Long lineCount(Queue queue) {
        return lineRepository.countAllByQueue(queue);
    }

    void resetTicket(WaitingLine waitingUser, String ticket) {
        waitingUser.setTicket(ticket);
        lineRepository.save(waitingUser);
    }
}
