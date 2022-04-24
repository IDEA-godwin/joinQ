package com.example.joinq.queue.repositories;

import com.example.joinq.queue.Queue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface QueueRepository extends JpaRepository<UUID, Queue> {
}
