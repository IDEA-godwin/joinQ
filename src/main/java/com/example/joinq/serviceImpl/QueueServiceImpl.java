package com.example.joinq.serviceImpl;

import com.example.joinq.domain.dto.CreateQueueDTO;
import com.example.joinq.domain.entities.Organization;
import com.example.joinq.domain.entities.Queue;
import com.example.joinq.domain.entities.User;
import com.example.joinq.domain.entities.WaitingLine;
import com.example.joinq.domain.enumeration.QueueStatusConstant;
import com.example.joinq.repository.OrganizationRepository;
import com.example.joinq.repository.QueueRepository;
import com.example.joinq.repository.UserRepository;
import com.example.joinq.service.LineService;
import com.example.joinq.service.QueueService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class QueueServiceImpl implements QueueService {

    private final QueueRepository queueRepository;
    private final UserRepository userRepository;
    private final OrganizationRepository organizationRepository;
    private final LineService lineService;

    @Override
    public Queue createQueue(CreateQueueDTO queueDTO) {
        Queue queue = new Queue();
        queue.setName(queueDTO.getName());
        queue.setDescription(queueDTO.getDescription());
        queue.setEstimatedServiceTime(queueDTO.getEstimatedServiceTime());
        queue.setQueueCode(RandomStringUtils.random(6, false, true));
        queue.setLastSlot(generateTicket());
        queue.setNextSlot(generateTicket());
        queue.setCount(0L);
        queue.setStatus(QueueStatusConstant.CLOSED);
        queue.setCallNext(queue.getLastSlot());
        queue.setCreatedAt(new Date());
        queue.setOrganization(getOrganizationInfo(queueDTO.getOrganizationId()));
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
    public WaitingLine joinQueue(String queueCode, String username) {
        Queue queue = queueRepository.getByQueueCode(queueCode);
        User user = userRepository.findUserByUsername(username).get();
        queue.setCount(queue.getCount() + 1);

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
        queue.setStatus(QueueStatusConstant.SERVING);
        WaitingLine next = lineService.callNextInLine(queue.getCallNext());

        queue.setCallNext(next.getNextInLine());
        queueRepository.save(queue);
        return next;

    }

    @Override
    public List<Queue> getAllOrganizationQueues(String organizationId) {
        Organization organization = getOrganizationInfo(organizationId);
        return queueRepository.findAllByOrganization(organization);
    }

    @Override
    public List<WaitingLine> getAllUserLine(String username) {
        User user = userRepository.findUserByUsername(username).get();
        return lineService.getAllLineWithUser(user);
    }

    @Override
    public Long getNumberOnQueue(String queueId) {
        return lineService.lineCount(getQueueById(queueId));
    }

    @Override
    public Queue closeQueue(String queueId) {
        Queue queue = getQueueById(queueId);
        queue.setStatus(QueueStatusConstant.CLOSED);
        lineService.deleteAllFromLine(queue);
        return queueRepository.save(queue);
    }

    @Override
    public Queue openQueue(String queueId) {
        Queue queue = getQueueById(queueId);
        queue.setStatus(QueueStatusConstant.OPEN);
        return queueRepository.save(queue);
    }

    Organization getOrganizationInfo(String organizationId) {
        return organizationRepository.findById(UUID.fromString(organizationId))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "queue not found"));
    }

    void saveUserInfo(User user) {
        user.setId(UUID.randomUUID());
        userRepository.save(user);
    }

    String generateTicket() {
        return RandomStringUtils.random(9, true, true);
    }
}
