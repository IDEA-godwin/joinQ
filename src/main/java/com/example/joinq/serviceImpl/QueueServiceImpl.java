package com.example.joinq.serviceImpl;

import com.example.joinq.domain.entities.Queue;
import com.example.joinq.domain.entities.User;
import com.example.joinq.domain.entities.WaitingLine;
import com.example.joinq.repository.QueueRepository;
import com.example.joinq.repository.UserRepository;
import com.example.joinq.service.LineService;
import com.example.joinq.service.QueueService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class QueueServiceImpl implements QueueService {

    private final QueueRepository queueRepository;
    private final UserRepository userRepository;
    private final LineService lineService;

    @Override
    public Queue createQueue() {
        Queue queue = new Queue();
        queue.setLastSlot(generateTicket());
        queue.setNextSlot(generateTicket());
        queue.setCallNext(queue.getLastSlot());
        return queueRepository.save(queue);
    }

    @Override
    public Queue getQueueById(String queueId) {
        Optional<Queue> queue = queueRepository.findById(UUID.fromString(queueId));
        if (queue.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "queue not found");
        return queue.get();
    }

    @Override
    public Page<WaitingLine> getWaitingUsersOnQueue(String queueId, int page, int size) {
        return lineService.getLine(getQueueById(queueId), page, size);
    }

    @Override
    public WaitingLine joinQueue(String queueId, User user) {
        Queue queue = getQueueById(queueId);

        if (user.getId() == null)
            saveUserInfo(user);
        WaitingLine userSlot = lineService.addToLine(queue, user);

        queue.setLastSlot(queue.getNextSlot());
        queue.setNextSlot(generateTicket());
        queueRepository.save(queue);

        return userSlot;
    }

    @Override
    public void leaveQueue(String slotId) {
        lineService.removeFromLine(slotId);
    }

    @Override
    public WaitingLine callNext(String queueId) {
        Queue queue = getQueueById(queueId);
        WaitingLine next = lineService.getWaitingUserByTicket(queue.getCallNext());
        queue.setCallNext(next.getNextInLine());
        queueRepository.save(queue);
        return next;

    }

    void saveUserInfo(User user) {
        user.setId(UUID.randomUUID());
        userRepository.save(user);
    }

    String generateTicket() {
        return RandomStringUtils.random(9, true, true);
    }
}
