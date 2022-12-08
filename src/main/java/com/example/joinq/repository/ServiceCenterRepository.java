package com.example.joinq.repository;

import com.example.joinq.domain.entities.ServiceCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ServiceCenterRepository extends JpaRepository<ServiceCenter, UUID> {
}
