package com.example.joinq.repository;

import com.example.joinq.domain.entities.Organization;
import com.example.joinq.domain.entities.Queue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface QueueRepository extends JpaRepository<Queue, UUID> {
    List<Queue> findAllByOrganization(Organization organization);
    Queue getByQueueCode(String queueCode);
}
