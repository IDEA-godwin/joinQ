package com.example.joinq.repository;

import com.example.joinq.domain.entities.Organization;
import com.example.joinq.domain.entities.ServiceCenter;
import com.example.joinq.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, UUID> {
    Optional<Organization> findOrganizationByUser(User user);
}
