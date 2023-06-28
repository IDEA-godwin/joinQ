package com.example.joinq.service;

import com.example.joinq.domain.dto.OrganizationDto;
import com.example.joinq.domain.dto.UserDto;
import com.example.joinq.domain.entities.Organization;
import com.example.joinq.domain.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserAdministrationService {

    User registerUser(User user);
    User getUserAccountByUsername(String username);
    UserDto getUserAccount();
    Organization createOrganizationAccount(Organization organization);
    OrganizationDto getOrganizationAccount(String adminUsername, String password);

}
