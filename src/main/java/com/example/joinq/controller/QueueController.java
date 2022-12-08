package com.example.joinq.controller;

import com.example.joinq.domain.entities.Queue;
import com.example.joinq.domain.entities.User;
import com.example.joinq.domain.entities.WaitingLine;
import com.example.joinq.service.QueueService;
import com.example.joinq.serviceImpl.QueueServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/queues")
public class QueueController {

    private final QueueService queueService;

    public QueueController(QueueService queueService) {
        this.queueService = queueService;
    }

    @PostMapping
    public ResponseEntity<Queue> createQueue() {
        return ResponseEntity.ok(queueService.createQueue());
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

    @PatchMapping("/{queueId}/join-queue")
    public ResponseEntity<WaitingLine> joinQueue(@PathVariable String queueId, @RequestBody User user) {
        return ResponseEntity.ok(queueService.joinQueue(queueId, user));
    }

    @PatchMapping("/{queueId}/call-next")
    public ResponseEntity<WaitingLine> callNext(@PathVariable String queueId) {
        return ResponseEntity.ok(queueService.callNext(queueId));
    }

    @DeleteMapping("/leave-queue/{slotId}")
    public ResponseEntity<?> leaveQueue(@PathVariable String slotId) {
        queueService.leaveQueue(slotId);
        return ResponseEntity.ok("{\"message\": \"You have left the queue\"}");
    }
}
