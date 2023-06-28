package com.example.joinq.controller;

import com.example.joinq.domain.dto.CreateQueueDTO;
import com.example.joinq.domain.entities.Queue;
import com.example.joinq.domain.entities.User;
import com.example.joinq.domain.entities.WaitingLine;
import com.example.joinq.service.LineService;
import com.example.joinq.service.QueueService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/queues")
@AllArgsConstructor
public class QueueController {

    private final QueueService queueService;
    private final LineService lineService;

    @PostMapping
    public ResponseEntity<Queue> createQueue(@RequestBody CreateQueueDTO queueDTO) {
        return ResponseEntity.ok(queueService.createQueue(queueDTO));
    }

    @GetMapping("/{queueId}")
    public ResponseEntity<Queue> getQueueById(@PathVariable String queueId) {
        return ResponseEntity.ok(queueService.getQueueById(queueId));
    }

    @GetMapping("/{queueId}/get-line")
    public ResponseEntity<Page<WaitingLine>> getQueueLine(@PathVariable String queueId,
                                                          @RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(queueService.getWaitingUsersOnQueue(queueId, page, size));
    }

    @GetMapping("/users/{username}/queues-lines")
    public ResponseEntity<List<WaitingLine>> getAllUserLines(@PathVariable String username) {
        return ResponseEntity.ok().body(queueService.getAllUserLine(username));
    }

    @GetMapping("/waiting-users/{id}")
    public ResponseEntity<WaitingLine> getWaitingUserById(@PathVariable String id) {
        return ResponseEntity.ok().body(lineService.getWaitingUserById(id));
    }

    @GetMapping("/organizations/{organizationId}")
    public ResponseEntity<List<Queue>> getAllOrganizationQueues(@PathVariable String organizationId) {
        return ResponseEntity.ok().body(queueService.getAllOrganizationQueues(organizationId));
    }

    @GetMapping("/{queueId}/number-on-queue")
    public ResponseEntity<Long> getNumberOnQueue(@PathVariable String queueId) {
        return ResponseEntity.ok().body(queueService.getNumberOnQueue(queueId));
    }

    @PostMapping("/{queueCode}/join-queue")
    public ResponseEntity<WaitingLine> joinQueue(@PathVariable String queueCode, @RequestBody String userId) {
        return ResponseEntity.ok(queueService.joinQueue(queueCode, userId));
    }

    @PostMapping("/{queueId}/call-next")
    public ResponseEntity<WaitingLine> callNext(@PathVariable String queueId) {
        return ResponseEntity.ok(queueService.callNext(queueId));
    }

    @PostMapping("/{queueId}/open")
    public ResponseEntity<Queue> openQueue(@PathVariable String queueId) {
        return ResponseEntity.ok().body(queueService.openQueue(queueId));
    }

    @PostMapping("/{queueId}/close")
    public ResponseEntity<Queue> closeQueue(@PathVariable String queueId) {
        return ResponseEntity.ok().body(queueService.closeQueue(queueId));
    }

    @DeleteMapping("/leave-queue/{slotId}")
    public ResponseEntity<?> leaveQueue(@PathVariable String slotId) {
        queueService.leaveQueue(slotId);
        return ResponseEntity.ok("{\"message\": \"You have left the queue\"}");
    }
}
